package ru.etna.documentmodification2_0.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.dto.DocumentFormDTO;


import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final ConvertorService convertorService;
    private final FilterEquipmentService filterEquipmentService;
    private final ScannerFileService scannerFileService;


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
        if (folder != null && !folder.isEmpty()) {
            List<String> listAllPdfName = scannerFileService.arrayPathAbsolute(folder);
            convertorService.mergePDFs(listAllPdfName, folder);
        }
    }
}
