package ru.etna.documentmodification2_0.service.equipment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.dto.EquipmentProcessingRequest;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.FilterEquipmentService;
import ru.etna.documentmodification2_0.service.StartupManagerService;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

@Component("ОРТ")
public class OrtEquipmentHandler implements EquipmentHandler, EquipmentHandlerForPsi {
    @Autowired
    private StartupManagerService startupManagerService;
    @Autowired
    private DocxUpdateTextService docxUpdateTextService;


    @Override
    public void handler(EquipmentProcessingRequest equipmentProcessingRequest) throws Exception {

        String patternFirst = CodeMapping.ORT_PATTERN_FIRST.getDescription();
        String patternDate = CodeMapping.ORT_PATTERN_DATE.getDescription();
        String patternSpace = CodeMapping.ORT_PATTERN_SPACEANDSIZEWORD.getDescription();
        String numberSearch = CodeMapping.ORT_PATTERN_NUMBER_SEARCH.getDescription();
        String patternNumber = CodeMapping.ORT_PATTERN_NUMBER.getDescription();
        String replaceNumber = CodeMapping.ORT_PATTERN_REPLACE_NUMBER.getDescription();
        int fontSize = CodeMapping.ORT_PATTERN_SPACEANDSIZEWORD.getSize();



        DocumentReplaceRequestDTO dto = new DocumentReplaceRequestDTO(
                equipmentProcessingRequest.docPath(),
                equipmentProcessingRequest.lastName(),
                equipmentProcessingRequest.data(),
                patternFirst,
                patternSpace,
                numberSearch,
                patternNumber,
                fontSize,
                replaceNumber,
                patternDate,
                equipmentProcessingRequest.pathExcel(),
                equipmentProcessingRequest.pathDirectory()
        );

        startupManagerService.enterDatabase(dto, equipmentProcessingRequest.numberInBold(),equipmentProcessingRequest.nameKey());
    }


    @Override
    public void handlerPsi(String lastName, XWPFDocument document, int sizeText) {
        String patternFirst = CodeMapping.ORT_PSI_PATTERN_FIRST.getDescription();
        String patternDate =  CodeMapping.ORT_PSI_PATTERN_DATE.getDescription();
        docxUpdateTextService.searchTitleForPsi(patternFirst,lastName,patternDate,document,sizeText);
    }
}
