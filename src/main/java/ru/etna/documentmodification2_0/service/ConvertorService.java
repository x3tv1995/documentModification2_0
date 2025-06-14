package ru.etna.documentmodification2_0.service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.document.DocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Service
public class ConvertorService {
    private  static Logger logger = LoggerFactory.getLogger(ConvertorService.class);


    public void convertorDocToPdf(String docPath, String pdfPath) throws OfficeException {
        File inputFile = new File(docPath);

        if (pdfPath == null && pdfPath.isEmpty()) {
            logger.error("Ошибка:  неверный путь для сохранения PDF файла: " + pdfPath);
            return;
        }
        String name = UUID.randomUUID() + ".pdf";
        File outputFolder = new File(pdfPath);
        File outputFile = new File(outputFolder,name);

        // Проверка существования входного файла
        if (!inputFile.exists()) {
            logger.error("Ошибка: Входной файл не найден: " + docPath);
            return;
        }

        File officeHome = new File("C:/Program Files/LibreOffice");
        if (!officeHome.exists() || !officeHome.isDirectory()) {
            logger.error("Ошибка: Путь к LibreOffice не найден или не является каталогом: " + officeHome.getAbsolutePath());
            logger.error("Проверьте, установлена ли LibreOffice и правильно ли указан путь.");
            return;
        }

        LocalOfficeManager officeManager = LocalOfficeManager.builder()
                .officeHome(officeHome)
                .build();

        try {
            officeManager.start();

            // Явная регистрация форматов
            DocumentFormatRegistry registry = DefaultDocumentFormatRegistry.getInstance();
            DocumentFormat docxFormat = registry.getFormatByExtension("doc");
            DocumentFormat pdfFormat = registry.getFormatByExtension("pdf");

            if (docxFormat == null) {
                logger.error("Не найден формат DOC");
                return;
            }
            if (pdfFormat == null) {
                logger.error("Не найден формат PDF");
                return;
            } else {
                logger.info("PDF формат найден: " + pdfFormat.getName());
            }


            LocalConverter.make(officeManager).convert(inputFile).as(pdfFormat).to(outputFile).execute();

            System.out.println("Конвертация выполнена успешно!");

        } catch (OfficeException e) {
            logger.error("Ошибка при работе с LibreOffice: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (officeManager != null && officeManager.isRunning()) {
                officeManager.stop();
            }
        }
    }

    //конвертация из DOC формата в DOCX
    public  File convertDocToDocx(File docFile) throws IOException, OfficeException {
        File tempDocx = File.createTempFile("temp", ".docx");

        File officeHome = new File("C:/Program Files/LibreOffice");

        LocalOfficeManager officeManager = LocalOfficeManager.builder()
                .officeHome(officeHome)
                .build();

        try {
            officeManager.start();

            DocumentConverter converter = LocalConverter.make(officeManager);
            converter.convert(docFile).to(tempDocx).execute();

        } finally {
            if (officeManager != null && officeManager.isRunning()) {
                officeManager.stop();
            }
        }
        return tempDocx;
    }

    // Соединение всех pdf в папке в один pdf файл
    public void mergePDFs(List<String> inputFiles, String outputFile) throws IOException {
        if(inputFiles.isEmpty()){
            logger.error("Нет PDF-файлов в указанной папке");
            throw new IllegalArgumentException("Нет PDF-файлов в указанной папке");
        }



        PDFMergerUtility merger = new PDFMergerUtility();
        File folder = new File(outputFile);
        if (!folder.exists() && !folder.mkdirs()) {
            logger.error("Не могу создать папку: " + folder.getAbsolutePath());
            throw new IOException("Не могу создать папку: " + folder.getAbsolutePath());
        }
        String uniqueFileName = "merged_output_" + System.currentTimeMillis() + ".pdf";
        String outputFilePath = Paths.get(folder.getAbsolutePath(), uniqueFileName).toString();

        for (String file : inputFiles) {
            merger.addSource(file);
        }

        merger.setDestinationFileName(outputFilePath);
        merger.mergeDocuments(null);


    }
}
