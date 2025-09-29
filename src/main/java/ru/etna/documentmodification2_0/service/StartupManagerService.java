package ru.etna.documentmodification2_0.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.entity.StatisticsDocHandler;
import ru.etna.documentmodification2_0.repository.StatsRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
@RequiredArgsConstructor
public class StartupManagerService {


    private static final Logger log = LoggerFactory.getLogger(StartupManagerService.class);

    private final DocxUpdateTextService docxUpdateTextService;
    private final NumberProductionService numberProductionService;
    private final StatsRepository statsRepository;


    public void enterDatabase(DocumentReplaceRequestDTO documentReplaceRequestDTO,
                              String numberInBold, String keyProduction) throws Exception {

        String docPath = documentReplaceRequestDTO.getDocPath();
        String pathExcel = documentReplaceRequestDTO.getPathExcel();
        String pathDirectory = documentReplaceRequestDTO.getPathDirectory();

        LocalDateTime  localStartTime = LocalDateTime.now(); // время начало для статистики в бд
        long startTime = System.currentTimeMillis(); // время начало для статистики в бд


        List<String> listNumbersProduction = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);


        StatisticsDocHandler statisticsDocHandler = new StatisticsDocHandler(); //объект для сбора статистики
        statisticsDocHandler.setCountDoc(listNumbersProduction.size());
        statisticsDocHandler.setDate(LocalDateTime.now().toLocalDate());
        statisticsDocHandler.setTitle(keyProduction);


        File docFile = new File(String.valueOf(docPath));
        if (!docFile.exists()) {
            log.error("Ошибка: Файл input.docx не найден по пути:{}", docPath);
            return;
        }


        byte[] templateBytes;
        try (FileInputStream fis = new FileInputStream(docFile);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            templateBytes = byteArrayOutputStream.toByteArray();
        }
        int threadCount= Math.min(listNumbersProduction.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        try {
            statisticsDocHandler.setStartTime(localStartTime);

            List<CompletableFuture<Void>> completableFutures = new ArrayList<>();

            for (String number : listNumbersProduction) {
               CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                   try{
                          docxUpdateTextService.processSingleNumber(templateBytes,
                                  documentReplaceRequestDTO, number, numberInBold, pathDirectory);


                   }catch (Exception e){
                       statisticsDocHandler.setStatus("FAILED");
                       statisticsDocHandler.setErrorMessage(e.getMessage());
                       log.error(" Ошибка при обработке номера: " + number, e);
                       throw new RuntimeException(e);
                   }
               }, threadPool);
               completableFutures.add(completableFuture);
            }
            CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        }finally {
            threadPool.shutdown();
            Thread.currentThread().interrupt();
        }
        long endTime = System.currentTimeMillis(); //  время окончания обработки для статистики в бд
        LocalDateTime localEndTime = LocalDateTime.now(); // дата и время для статистики в бд

        statisticsDocHandler.setEndTime(localEndTime);
        statisticsDocHandler.setDurationHandler((endTime - startTime)/1000);
        log.info(" Обработка {} номеров завершена.", listNumbersProduction.size());
        statisticsDocHandler.setStatus("SUCCESS");
        statsRepository.save(statisticsDocHandler);
    }

    public void enterDatabaseDefault(DocumentReplaceRequestDTO documentReplaceRequestDTO,
                              String numberInBold) throws Exception {
        String docPath = documentReplaceRequestDTO.getDocPath();
        String data = documentReplaceRequestDTO.getData();
        String pathExcel = documentReplaceRequestDTO.getPathExcel();
        String pathDirectory = documentReplaceRequestDTO.getPathDirectory();


        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        File docFile = new File(String.valueOf(docPath));
        if (!docFile.exists()) {
            log.error("Ошибка: Файл input.docx не найден по пути:{}", docPath);
            return;
        }
        byte[] templateBytes;
        try (FileInputStream fis = new FileInputStream(docFile);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            templateBytes = byteArrayOutputStream.toByteArray();
        }
        int threadCount= Math.min(arr.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        try {
            List<CompletableFuture<Void>> completableFutures = new ArrayList<>();

            for (String number : arr) {
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                    try{
                        docxUpdateTextService.processDefaultSingleNumber(templateBytes,
                                documentReplaceRequestDTO, number, numberInBold, pathDirectory);


                    }catch (Exception e){
                        log.error(" Ошибка при обработке номера: " + number, e);
                        throw new RuntimeException(e);
                    }
                }, threadPool);
                completableFutures.add(completableFuture);
            }
            CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        }finally {
            threadPool.shutdown();
            Thread.currentThread().interrupt();
        }

        log.info(" Обработка {} номеров завершена.", arr.size());
    }
}
