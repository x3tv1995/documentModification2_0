package ru.etna.documentmodification2_0.service.equipment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.dto.EquipmentProcessingRequest;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

@Component("БТР")
public class BtrEquipmentHandler implements EquipmentHandler, EquipmentHandlerForPsi {
    @Autowired
    private StartupManagerService startupManagerService;
    @Autowired
    private DocxUpdateTextService docxUpdateTextService;
    @Override
    public void handler(EquipmentProcessingRequest equipmentProcessingRequest) throws Exception {

        String patternFirst = CodeMapping.BTR_PATTERN_FIRST.getDescription();
        String patternDate = CodeMapping.BTR_PATTERN_DATE.getDescription();
        String patternSpace = CodeMapping.BTR_PATTERN_SPACEANDSIZEWORD.getDescription();
        String numberSearch = CodeMapping.BTR_PATTERN_NUMBER_SEARCH.getDescription();
        String patternNumber = CodeMapping.BTR_PATTERN_NUMBER.getDescription();
        String replaceNumber = CodeMapping.BTR_PATTERN_REPLACE_NUMBER.getDescription();
        int fontSize = CodeMapping.BTR_PATTERN_SPACEANDSIZEWORD.getSize();


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
        String patternFirst = CodeMapping.TRO_PSI_PATTERN_FIRST.getDescription();
        String patternDate =  CodeMapping.TRO_PSI_PATTERN_DATE.getDescription();
        docxUpdateTextService.searchTitleForPsi(patternFirst,lastName,patternDate,document,sizeText);
    }
}
