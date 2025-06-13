package ru.etna.documentmodification2_0.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.dto.DocumentFormDTO;


import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final ConvertorService convertorService;
    private final FilterEquipmentService filterEquipmentService;
    private final ScannerFileService scannerFileService;


    public void process(DocumentFormDTO documentFormDTO) throws Exception {
        String nameEquipment = filterEquipmentService.filterBybnshi(documentFormDTO.getPathExcel());
        filterEquipmentService.filterByName(nameEquipment, documentFormDTO.getPathExcel(), documentFormDTO.getDocPath(),
                documentFormDTO.getPathDirectory(), documentFormDTO.getLastName(), documentFormDTO.getData());



    }
    public  void mergePdf (String folder) throws Exception {
        if (folder != null && !folder.isEmpty()) {
            List<String> listAllPdfName = scannerFileService.arrayPathAbsolute(folder);
            convertorService.mergePDFs(listAllPdfName, folder);
        }
    }
}
