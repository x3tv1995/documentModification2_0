package ru.etna.documentmodification2_0.service;


import org.apache.pdfbox.multipdf.PDFMergerUtility;

import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.document.DocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */

@Service
public class ConvertorService {
    private  final Logger logger = LoggerFactory.getLogger(ConvertorService.class);

    public void convertorDocToPdf(String docPath, String pdfPath) throws OfficeException, FileNotFoundException {
        File inputFile = new File(docPath);

        if (pdfPath == null || pdfPath.isEmpty()) {
            logger.error("Ошибка:  неверный путь для сохранения PDF файла:{}", pdfPath);
            return;
        }
        String name = UUID.randomUUID() + ".pdf";
        File outputFolder = new File(pdfPath);
        File outputFile = new File(outputFolder,name);

        // Проверка существования входного файла
        if (!inputFile.exists()) {
            logger.error("Ошибка: Входной файл не найден:{}", docPath);
            return;
        }


        File officeHome = findLibreOfficePath();
        if (officeHome == null ) {
            logger.error("Ошибка: Путь к LibreOffice не найден или не является каталогом{}",  officeHome.getAbsolutePath());
            logger.error("""
                    Проверьте, установлена ли LibreOffice и правильно указан путь.
                    правильный путь LibreOffice C:/Program Files/LibreOffice
                    или C:/Program Files (x86)/LibreOffice""");
            throw new FileNotFoundException("LibreOffice не найден по пути: " + officeHome);

        }

        LocalOfficeManager officeManager = LocalOfficeManager.builder()
                .officeHome(officeHome)
                .build();

        try {
            officeManager.start();

            // Явная регистрация форматов
            DocumentFormatRegistry registry = DefaultDocumentFormatRegistry.getInstance();
            DocumentFormat docxFormat = registry.getFormatByExtension("docx");
            DocumentFormat pdfFormat = registry.getFormatByExtension("pdf");

            if (docxFormat == null) {
                logger.error("Не найден формат DOC");
                return;
            }
            if (pdfFormat == null) {
                logger.error("Не найден формат PDF");
                return;
            } else {
                logger.info("PDF формат найден{}", pdfFormat.getName());
            }


            LocalConverter.make(officeManager).convert(inputFile).as(pdfFormat).to(outputFile).execute();

            System.out.println("Конвертация выполнена успешно!");

        } catch (OfficeException e) {
            logger.error("Ошибка при работе с LibreOffice{}",  e.getMessage());
          throw new RuntimeException("Ошибка при работе с LibreOffice: "+ e.getMessage())  ;
        } finally {
            if (officeManager.isRunning()) {
                officeManager.stop();
            }
        }
    }
    private File findLibreOfficePath() {
        String userHome = System.getProperty("user.home");
        File[] candidates = {
                new File(userHome + "/Desktop/DocumentTool/libreoffice"),
                new File("./libreoffice"),
                new File("C:/Program Files/LibreOffice"),
                new File("C:/Program Files (x86)/LibreOffice"),

        };

        for (File path : candidates) {
            if (path.exists() && new File(path, "program/soffice.exe").exists()) {
                return path;
            }
        }

        return null;
    }

    public void convertFromStreamToPdf(ByteArrayOutputStream docxStream, String outputPdfPath) throws IOException, OfficeException {
        LocalOfficeManager officeManager = LocalOfficeManager.builder()
                .install()
                .build();

        File outputFolder = new File(outputPdfPath).getParentFile();
        if (!outputFolder.exists()) {
            logger.error("папки не существует");
            throw new RuntimeException("папки не существует");
        }
        String name = "PSI"+UUID.randomUUID() + ".pdf";
        File outputFile = new File(outputFolder,name);

        try (InputStream inputStream = new ByteArrayInputStream(docxStream.toByteArray())) {
            officeManager.start();
            // Явная регистрация форматов
            DocumentFormatRegistry registry = DefaultDocumentFormatRegistry.getInstance();
            DocumentFormat pdfFormat = registry.getFormatByExtension("pdf");
            if (pdfFormat == null) {
                logger.error("Не найден формат PDF");
                throw new RuntimeException("Не найден формат PDF");

            }

            LocalConverter.make(officeManager).convert(inputStream).as(pdfFormat).to(outputFile).execute();

        } finally {
            if (officeManager.isRunning()) {
                officeManager.stop();
            }
        }
    }


    // Соединение всех pdf в папке в один pdf файл
    public void mergePDFs(List<String> inputFiles, String outputFile) throws IOException {
        if(inputFiles.isEmpty()){
            logger.error("Нет PDF-файлов в указанной папке");
            throw new IllegalArgumentException("Нет PDF-файлов в указанной папке");
        }
        List<File> sortedFiles = inputFiles.stream()
                .map(File::new)
                .sorted(Comparator.comparingLong(File::lastModified))
                .toList();
        logger.info("Файлы для слияния (по дате изменения): {}", sortedFiles);

        PDFMergerUtility merger = new PDFMergerUtility();
        File folder = new File(outputFile);
        if (!folder.exists() && !folder.mkdirs()) {
            logger.error("Не могу создать папку:{}", folder.getAbsolutePath());
            throw new IOException("Не могу создать папку: " + folder.getAbsolutePath());
        }
        String uniqueFileName = "merged_output_" + System.currentTimeMillis() + ".pdf";
        String outputFilePath = Paths.get(folder.getAbsolutePath(), uniqueFileName).toString();

        for (File file : sortedFiles) {
            merger.addSource(file);
        }

        merger.setDestinationFileName(outputFilePath);
        merger.mergeDocuments(null);


    }
    public void mergePDFsPsi(List<String> inputFiles, String outputFile) throws IOException {
        if(inputFiles.isEmpty()){
            logger.error("Нет PDF-файлов в указанной папке");
            throw new IllegalArgumentException("Нет PDF-файлов в указанной папке");
        }
        long countFilesPsi = inputFiles.stream()
                .map(File::new)
                .filter(f->f.getName().startsWith("PSI"))
                .count();
        if (countFilesPsi ==1){
            throw new IllegalArgumentException("В указанной папке  один файл ПСИ");
        }
        List<File> sortedFiles = inputFiles.stream()
                .map(File::new)
                .filter(s->s.getName().startsWith("PSI"))
                .sorted(Comparator.comparingLong(File::lastModified))
                .toList();
        logger.info("Файлы для слияния (по дате изменения): {}", sortedFiles);

        PDFMergerUtility merger = new PDFMergerUtility();
        File folder = new File(outputFile);
        if (!folder.exists() && !folder.mkdirs()) {
            logger.error("Не могу создать папку:{}", folder.getAbsolutePath());
            throw new IOException("Не могу создать папку: " + folder.getAbsolutePath());
        }
        String uniqueFileName = "merged_output_" + System.currentTimeMillis() + ".pdf";
        String outputFilePath = Paths.get(folder.getAbsolutePath(), uniqueFileName).toString();

        for (File file : sortedFiles) {
                merger.addSource(file);

        }

        merger.setDestinationFileName(outputFilePath);
        merger.mergeDocuments(null);


    }
}
