package ru.etna.documentmodification2_0.service.psi;

import org.apache.poi.xwpf.usermodel.*;
import org.jodconverter.core.office.OfficeException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.*;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
public class PsiService {
    @Autowired
    NumberProductionService numberProductionService;
    @Autowired
    FilterEquipmentService filterEquipmentService;
    @Autowired
    ConvertorService convertorService;
    @Autowired
    DocxUpdateTextService docxUpdateTextService;
    @Autowired
    private Map<String, EquipmentHandlerForPsi> handlers;



    private static final Logger logger = LoggerFactory.getLogger(ConvertorService.class);
    private static final String TRO_KEY = "ТРО";
    private static final String SOKT_KEY = "СОКТ";
    private static final String OKVT_KEY = "ОКВТ";
    private static final String YPP1_KEY = "УПП1";
    private static final String YPP7_KEY = "УПП7";
    private static final String YPP_KEY = "УПП";
    private static final String NPEK_KEY = "НПЭК";
    private static final String OKVA_KEY = "ОКВА";
    private static final String  VVEK11400_KEY = "ВВЭК11400";
    private static final String  VVEK_KEY = "ВВЭК";
    private static final String  VVEK6500_KEY = "ЭК6500";

    private static final List<String> EK_VVEK = List.of("ЭК20000", "ЭК6500", "ВВЭК", "ВВЭК11400", "ВВЭК24000");

    private static final int MAX_ROWS = 10;
    private static final int FIRST_TABLE_INDEX = 0;
    private static final int DEFAULT_FONT_SIZE = 10;
    private static final int VVEK_11400_FONT_SIZE = 8;
    private static final int DEFAULT_SIZE_TEXT = 12;
    private static final int DATA_COLUMN_INDEX = 1;

    private static final String  VVEK_11400_VERSIA_01 = "14301";
    private static final String  VVEK_11400_VERSIA = "14300";

    //метод заполнения таблицы ПСИ  отдельно для ВВЭК/ЭК и для остальной продукции
    public void fillingPsi(String pathFile, String outputFile, String pathExcel, String lastname) throws IOException, OfficeException {
        String key = filterEquipmentService.filterBybnshi(pathExcel);

        if (pathFile.isEmpty()) {
            throw new IllegalArgumentException("Ошибка файл пуст");
        }
        boolean hasElement = EK_VVEK.stream().anyMatch(o -> o.equals(key));
        if (hasElement) {
            fillingTablePsiEkAndVek(key, pathFile, outputFile, pathExcel, lastname);
        } else {
            fillingFirstColumnInTable(key, pathFile, outputFile, pathExcel, lastname);
        }
    }
  //многопоточное асинхронное заполнения ПСИ в таблицу, ТОЛЬКО 1 колонка для заводских номеров
    public void fillingFirstColumnInTable(String key,
                                          String pathFile,
                                          String outputFile,
                                          String pathExcel,
                                          String lastname
    ) throws IOException{

        List<String> productionNumbers = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);

        int startRow = getStartRow(key);
        List<List<String>> batches = splitIntoBatches(productionNumbers, MAX_ROWS);
        for (int i = 0; i < batches.size(); i++) {
            logger.info("   Батч## " + batches.size());
        }

        EquipmentHandlerForPsi handler = getHandlerOrThrow(key);

         byte[] templateBytes;
        try (FileInputStream fis = new FileInputStream(Objects.requireNonNull(pathFile));
             XWPFDocument templateDoc = new XWPFDocument(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            templateDoc.write(baos);
            templateBytes = baos.toByteArray();
        }

            int threadCount = Math.min(productionNumbers.size(), Runtime.getRuntime().availableProcessors());

            ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

            try {
                List<CompletableFuture<Void>> futures = new ArrayList<>();

                for (List<String> batch : batches) {

                    CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() -> {
                       try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(templateBytes))) {
                           processBatch(doc, batch, key, lastname, outputFile, startRow, handler);
                       } catch (IOException e) {
                           throw new RuntimeException("Ошибка при обработке батча", e);
                       }
                    }, threadPool);
                    futures.add(taskFuture);
                }
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();

            } finally {
                threadPool.shutdown();
                try {
                    if (threadPool.awaitTermination(30, TimeUnit.MICROSECONDS)) {
                        threadPool.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    threadPool.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }



    }

 //заполнение ПСИ таблицу для ВВЭК и ЭК
    public void fillingTablePsiEkAndVek(
            String key,
            String templateDocxPath,
            String outputDirPath,
            String excelDataPath,
            String lastName
    ) throws IOException {

        List<String> productionNumbers = numberProductionService.numberProductionFromExcelInArray(excelDataPath, 0, 0);

        byte[] templateBytes;
        try (FileInputStream fis = new FileInputStream(templateDocxPath)) {
            templateBytes = fis.readAllBytes();
        }
        int threadCount = Math.min(productionNumbers.size(), Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        try {
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (int i = 0; i < productionNumbers.size(); i++) {
                final String numberProduction = productionNumbers.get(i);
                CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() -> {
                    processSingleDocument(
                            templateBytes,
                            numberProduction,
                            key,
                            lastName,
                            outputDirPath
                    );
                }, threadPool);
                futures.add(taskFuture);
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();

        } finally {
            threadPool.shutdown();
            try {
                if (threadPool.awaitTermination(30, TimeUnit.MICROSECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

//получаем строку заполнения таблицы
    private int getStartRow(String key) {
        Set<String> keysWithOffset = Set.of(TRO_KEY, SOKT_KEY, OKVA_KEY, OKVT_KEY, NPEK_KEY);
        return keysWithOffset.contains(key) ? 3 : 2;
    }
//получаем  обработчика для ПСИ
    private EquipmentHandlerForPsi getHandlerOrThrow(String key) {
        EquipmentHandlerForPsi handler = handlers.get(key);
        if (handler == null) {
            String msg = "Отсутствует обработчик PSI для ключа: " + key;
            logger.error(msg);
            throw new IllegalStateException(msg);
        }
        return handler;
    }
  // метод для определения является ли продукция УПП (у УПП таблица отличается от остальных продуктов)
    private boolean isYppKey(String key) {
        return YPP1_KEY.equals(key) || YPP7_KEY.equals(key) || YPP_KEY.equals(key);
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

        try (XWPFDocument doc = cloneDocument(templateDoc)) {
            XWPFTable table = doc.getTables().get(FIRST_TABLE_INDEX);

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
                XWPFTableCell cell = row.getCell(DATA_COLUMN_INDEX); // лучше вынести в константу:

                if (isYppKey(key)) {
                    addTextForYpp5(cell, batch.get(i), DEFAULT_FONT_SIZE);
                } else {
                    addTextToCell(cell, batch.get(i), DEFAULT_FONT_SIZE);
                }
            }


            handler.handlerPsi(lastname, doc, DEFAULT_SIZE_TEXT);

            convertAndSave(doc, outputDir, batch.get(0));
        }
    }
//создаю копию  документа.Сначала сериализую в массив байтов, а  возвращаю десеариализованный документ
    private XWPFDocument cloneDocument(XWPFDocument original) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        original.write(byteArrayOutputStream);
        return new XWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }



    //общий метод для одного документа т.е. получаем поток байтов создаём документ из потока
    //fillTable- заполнение таблицы ФИО
    //applyEquipmentHandler - обработчик  меняет  строку ***Представитель ОТК____ ____ ___***
    // convertAndSave - конвертор из docx в PDF
    private void processSingleDocument(
            byte[] templateBytes,
            String productionNumber,
            String key,
            String lastName,
            String outputDirPath

    ) {

        try (ByteArrayInputStream bis = new ByteArrayInputStream(templateBytes);
             XWPFDocument document = new XWPFDocument(bis)) {

            fillTableLastname(document, key, lastName,productionNumber);
            replaceNumbersInPsiVVEKANDEK(document, productionNumber);
            applyEquipmentHandler(key, lastName, document, DEFAULT_FONT_SIZE);
            convertAndSave(document, outputDirPath,productionNumber);

        } catch (Exception e) {
            String errorMsg = "Ошибка при обработке документа для номера: " + productionNumber ;
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    //fillTable- заполнение таблицы ФИО
    private void fillTableLastname(XWPFDocument document, String key, String lastName, String number) {
        XWPFTable table = document.getTables().get(0);
        int startRow = 3;
        int totalRowsToFill = 9;
        if (key.startsWith(VVEK11400_KEY)) totalRowsToFill = 10;
        logger.info("****КЛЮЧ = {},totalRowsToFill ={}", key, totalRowsToFill);

        for (int rowIndex = startRow; rowIndex < startRow + totalRowsToFill && rowIndex < table.getRows().size(); rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            if (row == null) continue;

            List<XWPFTableCell> cells = row.getTableCells();
            if (cells.isEmpty()) continue;

            XWPFTableCell targetCell = determineTargetCell(cells, key, rowIndex);
            if (number.startsWith(VVEK_11400_VERSIA_01) && rowIndex == 8) continue;
            if (number.startsWith(VVEK_11400_VERSIA) && rowIndex == 9) continue;
            if (key.startsWith(VVEK11400_KEY)) {
                addTextToCell(targetCell, lastName, VVEK_11400_FONT_SIZE);
            }else {
                addTextToCell(targetCell, lastName, DEFAULT_FONT_SIZE);
            }
        }
    }

    //определение столбца для заполнения
    private XWPFTableCell determineTargetCell(List<XWPFTableCell> cells, String key, int rowIndex) {
        if (key.startsWith(VVEK11400_KEY) && cells.size() > 5) {
            return cells.get(6);
        }
        if (key.startsWith(VVEK_KEY) && cells.size() > 5) {
            return cells.get(5);
        }
        if (key.startsWith(VVEK6500_KEY) && rowIndex ==9){
            return cells.get(6);
        }
        if (rowIndex == 9 || rowIndex == 10) {
            return cells.get(7);
        }
        return cells.get(6);
    }
//замена номеров в ПСИ ввэк и эк
    private void replaceNumbersInPsiVVEKANDEK(XWPFDocument document, String productionNumber) throws FileNotFoundException {
        String searchPattern = CodeMapping.VVEK_PSI_PATTERN_NUMBER_SEARCH.getDescription();
        String numberPattern = CodeMapping.VVEK_PSI_PATTERN_NUMBER.getDescription();
        int patternSize = CodeMapping.VVEK_PSI_PATTERN_NUMBER_SEARCH.getSize();
        int maxReplaced = 2;
        int count = 0;


        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (count >= maxReplaced) break;

            StringBuilder paragraphText = new StringBuilder();
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null) {
                    paragraphText.append(text);
                }
            }

            String fullText = paragraphText.toString();
            logger.info("Текст параграфа: {}", fullText);
            Pattern pattern = Pattern.compile(searchPattern);
            Matcher matcher = pattern.matcher(fullText);
            if (matcher.find()) {
                String newText = numberPattern + productionNumber + " ";
                boolean replaced = docxUpdateTextService.replaceInParagraph(paragraph, fullText, searchPattern, newText, patternSize);
                if (replaced) {
                    count++;
                    logger.info("ЗАМЕНИЛ номер №{}", count);
                }
            }
        }
    }
 //обработчик  меняет  строку ***Представитель ОТК____ ____ ___***
    private void applyEquipmentHandler(String key, String lastName, XWPFDocument document, int fontSize) {
        EquipmentHandlerForPsi handler = handlers.get(key);
        if (handler == null) {
            throw new IllegalStateException("Отсутствует обработчик для ключа: " + key);
        }
        try {
            handler.handlerPsi(lastName, document, fontSize);
        } catch (Exception e) {
            logger.error("Ошибка при обработке ПСИ для " + key, e);
            throw new IllegalStateException("Ошибка применения обработчика ПСИ для " + key, e);
        }
    }
  // конвертация из DOCX в pdf
    private void convertAndSave(XWPFDocument document, String outputDirPath,String firstNumber) throws IOException {
        try (ByteArrayOutputStream fileInMemory = new ByteArrayOutputStream()) {
            document.write(fileInMemory);

            String uniqueFileName = "PSI_" + firstNumber + ".pdf";
            String filePath = outputDirPath + File.separator + uniqueFileName;
            logger.info(" Сохраняю файл: " + filePath); // ← ДОБАВЬ ЭТОТ ЛОГ

            convertorService.convertFromStreamToPdf(fileInMemory, filePath);

            logger.info(" Конвертация завершена: " + filePath); // ← И ЭТОТ
        }
    }


    //очистка ячеек перед заполнением
    private void clearCell(XWPFTableCell cell) {
        while (!cell.getParagraphs().isEmpty()) {
            cell.removeParagraph(0);
        }
    }
// добавление текста в ячейку
    private void addTextToCell(XWPFTableCell cell, String text, int fontSize) {
        clearCell(cell);
        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        paragraph.setFirstLineIndent(0);


    }
    // добавление текста в ячейку c настройками для продукции УПП
    private void addTextForYpp5(XWPFTableCell cell, String text, int fontSize) {
        if (cell == null || text == null) {
            logger.warn("Ячейка или текст равны null");
            return;
        }

        XWPFParagraph firstParagraph;

        if (cell.getParagraphs().isEmpty()) {
            firstParagraph = cell.addParagraph();
        } else {
            firstParagraph = cell.getParagraphs().get(0);
        }

        List<XWPFRun> runs = firstParagraph.getRuns();

        if (runs == null || runs.isEmpty()) {
            XWPFRun run = firstParagraph.createRun();
            run.setText(text);
            logger.info("Создан новый Run, так как старых не было");
            return;
        }

        XWPFRun originalRun = runs.get(0);

        CTR originalCTR = originalRun != null && originalRun.getCTR() != null ? originalRun.getCTR() : null;

        while (!runs.isEmpty()) {
            firstParagraph.removeRun(0);
        }

        XWPFRun newRun = firstParagraph.createRun();

        if (originalCTR != null) {
            try {
                newRun.getCTR().set(originalCTR);
            } catch (Exception e) {
                logger.error("Ошибка при копировании стиля из оригинального CTR", e);
            }
        } else {
            newRun.setFontSize(10);
            newRun.setBold(false);
            newRun.setItalic(false);
            logger.warn("CTR стиля отсутствует, применяется дефолтный стиль");
        }

        newRun.setText(text);
    }


}

