package ru.etna.documentmodification2_0.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.etna.documentmodification2_0.dto.DocumentFormDTO;
import ru.etna.documentmodification2_0.dto.PsiFormDto;
import ru.etna.documentmodification2_0.dto.TempFilesDTO;
import ru.etna.documentmodification2_0.service.psi.PsiService;


import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final Logger logger = LoggerFactory.getLogger(DocumentService.class);
    private final ConvertorService convertorService;
    private final FilterEquipmentService filterEquipmentService;
    private final ScannerFileService scannerFileService;
    private final PsiService psiService;



    public void process(DocumentFormDTO documentFormDTO) throws Exception {
        logger.info("Запускаю процесс для обработки паспортов");
        TempFilesDTO files = createTempDocxAndExcel(documentFormDTO.getDocPath(),documentFormDTO.getPathExcel());



        String key = filterEquipmentService.filterBybnshi(files.getExcelPath());
        logger.info("Получаю ключ для поиска шаблона для: {}", key);
        filterEquipmentService.filterByName(
                key,
                files.getExcelPath(),
                files.getDocxPath(),
                documentFormDTO.getPathDirectory(),
                documentFormDTO.getLastName(),
                documentFormDTO.getData()
        );



    }

    public void mergePdf(String folder) throws Exception {
        if (folder.isEmpty()) {
            throw new RuntimeException("Введите путь к pdf файлам");
        }
        List<String> listAllPdfName = scannerFileService.arrayPathAbsolute(folder);
        if(listAllPdfName.size() <= 1){
            throw new RuntimeException("В папке должно быть более  1 файла .pdf");
        }
        logger.info("Начинаю мёрж PDF паспортов");
            convertorService.mergePDFs(listAllPdfName, folder);
        logger.info("закончил мёрж PDF паспортов");
    }
    public void mergePdfForPsi(String folder) throws Exception {
        if (folder.isEmpty()) {
            throw new RuntimeException("Введите путь к pdf файлам");
        }
            List<String> listAllPdfName = scannerFileService.arrayPathAbsolute(folder);
            if(listAllPdfName.size() <= 1){
                throw new RuntimeException("В папке должно быть более  1 файла .pdf");
            }
        logger.info("Начинаю мёрж PDF ПСИ документов");
            convertorService.mergePDFsPsi(listAllPdfName, folder);
        logger.info("Закончил мёрж PDF ПСИ документов");

    }

    public void processForDocx(PsiFormDto psiFormDto) throws Exception {
        logger.info("Запускаю процесс для обработки ПСИ");
        TempFilesDTO files = createTempDocxAndExcel(psiFormDto.getDocPath(),psiFormDto.getPathExcel());

        psiService.fillingPsi(
                files.getDocxPath(),
                psiFormDto.getPathDirectory(),
                files.getExcelPath(),
                psiFormDto.getLastName()
        );
    }
    private TempFilesDTO createTempDocxAndExcel(MultipartFile fileDocPath, MultipartFile fileExcelPath) throws Exception {
        File tempDocx = Files.createTempFile("docx-", ".docx").toFile();
        logger.info("Сохраняю временный файл 'Docx': {}", tempDocx.getAbsolutePath());
        fileDocPath.transferTo(tempDocx);
        String docPath = tempDocx.getAbsolutePath();

        File tempExel = Files.createTempFile("xlsx-", ".xlsx").toFile();
        logger.info("Сохраняю временный файл 'Exel': {}", tempExel.getAbsolutePath());
        fileExcelPath.transferTo(tempExel);
        String pathExcel = tempExel.getAbsolutePath();

        return  new TempFilesDTO(docPath, pathExcel);
    }

}
