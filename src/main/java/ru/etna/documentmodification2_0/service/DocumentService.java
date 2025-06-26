package ru.etna.documentmodification2_0.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.dto.DocumentFormDTO;
import ru.etna.documentmodification2_0.dto.PsiFormDto;
import ru.etna.documentmodification2_0.service.psi.PsiService;


import java.io.File;
import java.nio.file.Files;
import java.util.List;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
@RequiredArgsConstructor
public class DocumentService {
    private final ConvertorService convertorService;
    private final FilterEquipmentService filterEquipmentService;
    private final ScannerFileService scannerFileService;
    private final PsiService psiService;


    public void process(DocumentFormDTO documentFormDTO) throws Exception {
        File tempDocx = Files.createTempFile("docx-", ".docx").toFile();
        documentFormDTO.getDocPath().transferTo(tempDocx);
        String docPath = tempDocx.getAbsolutePath();

        File tempExel = Files.createTempFile("xlsx-", ".xlsx").toFile();
        documentFormDTO.getPathExcel().transferTo(tempExel);
        String pathExcel = tempExel.getAbsolutePath();

        String key = filterEquipmentService.filterBybnshi(pathExcel);
        filterEquipmentService.filterByName(
                key,
                pathExcel,
                docPath,
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
            convertorService.mergePDFs(listAllPdfName, folder);

    }
    public void mergePdfForPsi(String folder) throws Exception {
        if (folder.isEmpty()) {
            throw new RuntimeException("Введите путь к pdf файлам");
        }
            List<String> listAllPdfName = scannerFileService.arrayPathAbsolute(folder);
            if(listAllPdfName.size() <= 1){
                throw new RuntimeException("В папке должно быть более  1 файла .pdf");
            }
            convertorService.mergePDFsPsi(listAllPdfName, folder);

    }

    public void processForDocx(PsiFormDto psiFormDto) throws Exception {
        File tempDocx = Files.createTempFile("docx-", ".docx").toFile();
        psiFormDto.getDocPath().transferTo(tempDocx);
        String docPath = tempDocx.getAbsolutePath();

        File tempExel = Files.createTempFile("xlsx-", ".xlsx").toFile();
        psiFormDto.getPathExcel().transferTo(tempExel);
        String pathExcel = tempExel.getAbsolutePath();

        psiService.fillingFirstColumnInTable(

                docPath,
                psiFormDto.getPathDirectory(),
                pathExcel,
                psiFormDto.getLastName()

        );




    }
}
