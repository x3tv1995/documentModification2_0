package ru.etna.documentmodification2_0.service.equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.dto.EquipmentProcessingRequest;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.StartupManagerService;

@Component("ЭК")
public class EkEquipmentHandler implements EquipmentHandler{
    @Autowired
    private StartupManagerService startupManagerService;

    @Override
    public void handler(EquipmentProcessingRequest equipmentProcessingRequest) throws Exception {
        String patternFirst = CodeMapping.EK_PATTERN_FIRST.getDescription();
        String patternDate = CodeMapping.EK_PATTERN_DATE.getDescription();
        String patternSpace = CodeMapping.EK_PATTERN_SPACEANDSIZEWORD.getDescription();
        String numberSearch = CodeMapping.EK_PATTERN_NUMBER_SEARCH.getDescription();
        String patternNumber = CodeMapping.EK_PATTERN_NUMBER.getDescription();
        String replaceNumber = CodeMapping.EK_PATTERN_REPLACE_NUMBER.getDescription();
        int fontSize = CodeMapping.EK_PATTERN_SPACEANDSIZEWORD.getSize();


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
}
