package ru.etna.documentmodification2_0.service.psi;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.service.ConvertorService;
import ru.etna.documentmodification2_0.service.NumberProductionService;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PsiBatchProcessor {

    private final ConvertorService convertorService;
    private final Map<String, EquipmentHandlerForPsi> handlers;
    private final PsiDocxHelper psiHelper;

    private static final Logger logger = LoggerFactory.getLogger(PsiBatchProcessor.class);

    // Обработка обычных изделий (батчами)
    public void processStandard(
            String key,
            String pathFile,
            String outputFile,
            String pathExcel,
            String lastname,
            NumberProductionService numberProductionService
    ) throws IOException {
        List<String> productionNumbers = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        int startRow = psiHelper.getStartRow(key);
        List<List<String>> batches = splitIntoBatches(productionNumbers, PsiConstants.MAX_ROWS);

        EquipmentHandlerForPsi handler = getHandlerOrThrow(key);

        byte[] templateBytes = Files.readAllBytes(Paths.get(pathFile));

        int threadCount = Math.min(productionNumbers.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        try {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (List<String> batch : batches) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(templateBytes))) {
                        processBatch(doc, batch, key, lastname, outputFile, startRow, handler);
                    } catch (IOException e) {
                        throw new RuntimeException("Ошибка в батче", e);
                    }
                }, threadPool);
                futures.add(future);
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } finally {
            shutdownThreadPool(threadPool);
        }
    }

    // Обработка ВВЭК/ЭК (по одному документу)
    public void processVvekEk(
            String key,
            String templateDocxPath,
            String outputDirPath,
            String excelDataPath,
            String lastName,
            NumberProductionService numberProductionService
    ) throws IOException {
        List<String> productionNumbers = numberProductionService.numberProductionFromExcelInArray(excelDataPath, 0, 0);
        byte[] templateBytes = Files.readAllBytes(Paths.get(templateDocxPath));

        int threadCount = Math.min(productionNumbers.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        try {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (String number : productionNumbers) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    processSingleDocument(templateBytes, number, key, lastName, outputDirPath);
                }, threadPool);
                futures.add(future);
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } finally {
            shutdownThreadPool(threadPool);
        }
    }



    //процесс заполнения таблицы. Batch- периодический процесс
    private void processBatch(
            XWPFDocument templateDoc,
            List<String> batch,
            String key,
            String lastname,
            String outputDir,
            int startRow,
            EquipmentHandlerForPsi handler
    ) throws IOException {

        try (XWPFDocument doc = psiHelper.cloneDocument(templateDoc)) {
            XWPFTable table = doc.getTables().get(PsiConstants.FIRST_TABLE_INDEX);

            // Заполняю строки таблицы
            for (int i = 0; i < batch.size(); i++) {
                int targetRowIndex = startRow + i;

                if (targetRowIndex >= table.getNumberOfRows()) {
                    String msg = "Превышено количество строк в таблице. Невозможно добавить больше записей. " +
                            "Проверьте, возможно, вы пытаетесь загрузить номера от ТРО в обычный шаблон.";
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }

                XWPFTableRow row = table.getRow(targetRowIndex);
                XWPFTableCell cell = row.getCell(PsiConstants.DATA_COLUMN_INDEX); // лучше вынести в константу:

                if (psiHelper.isYppKey(key)) {
                    psiHelper.addTextForYpp5(cell, batch.get(i), PsiConstants.DEFAULT_FONT_SIZE);
                } else {
                    psiHelper.addTextToCell(cell, batch.get(i), PsiConstants.DEFAULT_FONT_SIZE);
                }
            }


            handler.handlerPsi(lastname, doc, PsiConstants.DEFAULT_SIZE_TEXT);

            convertAndSave(doc, outputDir, batch.get(0));
        }
    }
    //общий метод для одного документа т.е. получаем поток байтов создаём документ из потока
    //fillTable- заполнение таблицы ФИО
    //applyEquipmentHandler - обработчик  меняет  строку ***Представитель ОТК____ ____ ___***
    // convertAndSave - конвертор из docx в PDF
    private void processSingleDocument(
            byte[] templateBytes,
            String productionNumber,
            String key,
            String lastname,
            String outputDirPath

    ) {

        try (ByteArrayInputStream bis = new ByteArrayInputStream(templateBytes);
             XWPFDocument document = new XWPFDocument(bis)) {

            psiHelper.fillTableLastname(document, key, lastname,productionNumber);
            psiHelper.replaceNumbersInPsiVVEKANDEK(document, productionNumber);
            psiHelper.applyEquipmentHandler(key, lastname, document, PsiConstants.DEFAULT_FONT_SIZE);
            convertAndSave(document, outputDirPath,productionNumber);

        } catch (Exception e) {
            String errorMsg = "Ошибка при обработке документа для номера: " + productionNumber ;
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }
    // конвертация из DOCX в pdf
    private void convertAndSave(XWPFDocument document, String outputDirPath,String firstNumber) throws IOException {
        try (ByteArrayOutputStream fileInMemory = new ByteArrayOutputStream()) {
            document.write(fileInMemory);

            String uniqueFileName = "PSI_" + firstNumber + ".pdf";
            String filePath = outputDirPath + File.separator + uniqueFileName;
            logger.info(" Сохраняю файл: " + filePath);

            convertorService.convertFromStreamToPdf(fileInMemory, filePath);

            logger.info(" Конвертация завершена: " + filePath);
        }
    }

    private List<List<String>> splitIntoBatches (List<String> numbers , int batchSize) {
        List<List<String>> batches = new ArrayList<>();
        for (int startIndex = 0; startIndex < numbers.size(); startIndex+=batchSize) {
            int endIndex = Math.min(startIndex + batchSize, numbers.size());
            List<String>batch = numbers.subList(startIndex, endIndex);
            batches.add(batch);
        }
        return batches;
    }


    //получаем обработчика для ПСИ
    private EquipmentHandlerForPsi getHandlerOrThrow(String key) {
        EquipmentHandlerForPsi handler = handlers.get(key);
        if (handler == null) {
            String msg = "Отсутствует обработчик PSI для ключа: " + key;
            logger.error(msg);
            throw new IllegalStateException(msg);
        }
        return handler;
    }


    private void shutdownThreadPool(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
