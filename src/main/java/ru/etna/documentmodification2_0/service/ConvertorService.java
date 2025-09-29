package ru.etna.documentmodification2_0.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.document.DocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */

@Service
@RequiredArgsConstructor
public class ConvertorService {
    private final Logger logger = LoggerFactory.getLogger(ConvertorService.class);
    private final OfficeManager officeManager;


    public void convertorDocToPdf(String docPath, String pdfPath, String number) throws OfficeException, IOException {
        File inputFile = new File(docPath);

        if (pdfPath == null || pdfPath.isEmpty()) {
            logger.error("Ошибка:  неверный путь для сохранения PDF файла:{}", pdfPath);
            throw new IOException("Укажите корректный путь для сохранения PDF");
        }
        String name = number + ".pdf";
        File outputFolder = new File(pdfPath);
        File outputFile = new File(outputFolder, name);


        if (!inputFile.exists()) {
            logger.error("Ошибка: Входной файл не найден:{}", docPath);
            throw new IOException("Входной файл не найден: " + docPath);
        }


        File officeHome = findLibreOfficePath();
        if (officeHome == null) {
            logger.error("""
                    Проверьте, установлена ли LibreOffice и правильно указан путь.
                    правильный путь LibreOffice C:/Program Files/LibreOffice
                    или C:/Program Files (x86)/LibreOffice""");
            throw new IllegalStateException("LibreOffice не найден");

        }

        DocumentFormatRegistry registry = DefaultDocumentFormatRegistry.getInstance();
        DocumentFormat docxFormat = registry.getFormatByExtension("docx");
        DocumentFormat pdfFormat = registry.getFormatByExtension("pdf");

        if (docxFormat == null) {
            logger.error("Не найден формат DOC");
            throw new IllegalStateException("Не удалось получить формат DOCX или PDF");
        }
        if (pdfFormat == null) {
            logger.error("Не найден формат PDF");
            throw new FileNotFoundException("Не найден формат PDF");
        } else {
            logger.info("PDF формат найден{}", pdfFormat.getName());
        }


        LocalConverter.make(officeManager)
                .convert(inputFile)
                .as(pdfFormat)
                .to(outputFile)
                .execute();

        logger.info("Конвертация выполнена успешно!");


    }

    public File findLibreOfficePath() {
        String userHome = System.getProperty("user.home");
        File[] candidates = {
                new File(userHome + "/Desktop/DocumentTool/libreoffice"),
                new File("./libreoffice"),
                new File("C:/Program Files/LibreOffice"),
                new File("C:/Program Files (x86)/LibreOffice")

        };

        for (File path : candidates) {
            if (path.exists() && new File(path, "program/soffice.exe").exists()) {
                return path;
            }
        }

        return null;
    }

    public void convertFromStreamToPdf(ByteArrayOutputStream docxStream, String outputPdfPath) throws IOException {
        File outputFile = new File(outputPdfPath);
        File outputFolder = new File(outputPdfPath).getParentFile();
        if (!outputFolder.exists() && !outputFolder.mkdirs()) {
            throw new RuntimeException("Не удалось создать папку: " + outputFolder);
        }

//        String name = "PSI_" + UUID.randomUUID() + ".pdf";
//        File outputFile = new File(outputFolder, name);

        logger.info("Конвертирую файл в PDF: {}", outputFile);

        try (InputStream inputStream = new ByteArrayInputStream(docxStream.toByteArray())) {
            DocumentFormatRegistry registry = DefaultDocumentFormatRegistry.getInstance();
            DocumentFormat pdfFormat = registry.getFormatByExtension("pdf");
            if (pdfFormat == null) {
                throw new FileNotFoundException("Формат PDF не найден");
            }

            LocalConverter.make(officeManager)
                    .convert(inputStream)
                    .as(pdfFormat)
                    .to(outputFile)
                    .execute();

            logger.info("PDF успешно сохранен: {}", outputFile);

        } catch (OfficeException e) {
            logger.error("Ошибка конвертации в PDF", e);
            throw new RuntimeException("Ошибка конвертации LibreOffice", e);
        }
    }


    // Соединение всех pdf в папке в один pdf файл
    public void mergePDFs(List<String> inputFiles, String outputFile) throws IOException {
        if (inputFiles.isEmpty()) {
            logger.error("Нет PDF-файлов в указанной папке");
            throw new IllegalArgumentException("Нет PDF-файлов в указанной папке");
        }


        PDFMergerUtility merger = new PDFMergerUtility();
        File folder = new File(outputFile);

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        Arrays.sort(files, Comparator.comparing(File::getName));
        if (!folder.exists() && !folder.mkdirs()) {
            logger.error("Не могу создать папку:{}", folder.getAbsolutePath());
            throw new IOException("Не могу создать папку: " + folder.getAbsolutePath());
        }
        String uniqueFileName = "merged_output_" + System.currentTimeMillis() + ".pdf";
        String outputFilePath = Paths.get(folder.getAbsolutePath(), uniqueFileName).toString();
        logger.info("Начинаю мёрж PDF-файлов : {},", uniqueFileName);
        for (File file : files) {
            merger.addSource(file);
        }

        merger.setDestinationFileName(outputFilePath);
        merger.mergeDocuments(null);
        logger.info("PDF успешно объединены: {}", outputFilePath);

        for (File pdfFile : files) {
            if (pdfFile.delete()) {
                logger.info("🗑️ Удалён: " + pdfFile.getName());
            }
        }

    }

    //слияние всех Psi.pdf в один документов merge.PDF
    public void mergePDFsPsi(List<String> inputFiles, String outputFile) throws IOException {
        if (inputFiles.isEmpty()) {
            logger.error("Нет PDF-файлов в указанной папке");
            throw new IllegalArgumentException("Нет PDF-файлов в указанной папке");
        }

        List<File> sortedFiles = inputFiles.stream()
                .map(File::new)
                .filter(s -> s.getName().startsWith("PSI"))
//                .sorted(Comparator.comparingLong(File::lastModified))
                .toList();

        if (sortedFiles.isEmpty()) {
            logger.warn("В указанной папке нет файлов  ПСИ");
            throw new IllegalArgumentException("Нет файлов ПСИ в папке");
        }
        if (sortedFiles.size() == 1) {
            throw new IllegalArgumentException("В указанной папке  один файл ПСИ");
        }
        logger.info("Файлы для слияния (по дате изменения): {}", sortedFiles);

        PDFMergerUtility merger = new PDFMergerUtility();
        File folder = new File(outputFile);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        Arrays.sort(files, Comparator.comparing(File::getName));
        if (!folder.exists() && !folder.mkdirs()) {
            logger.error("Не могу создать папку:{}", folder.getAbsolutePath());
            throw new IOException("Не могу создать папку: " + folder.getAbsolutePath());
        }
        String uniqueFileName = "merged_output_" + System.currentTimeMillis() + ".pdf";
        String outputFilePath = Paths.get(folder.getAbsolutePath(), uniqueFileName).toString();
        logger.info("Начинаю мёрж PDF-файлов : {},", uniqueFileName);
        for (File file : sortedFiles) {
            merger.addSource(file);

        }

        merger.setDestinationFileName(outputFilePath);
        merger.mergeDocuments(null);
        logger.info("PDF успешно объединены: {}", outputFilePath);
        for (File pdfFile : files) {
            if (pdfFile.delete()) {
                logger.info("Удалён: " + pdfFile.getName());
            }
        }
    }
}
