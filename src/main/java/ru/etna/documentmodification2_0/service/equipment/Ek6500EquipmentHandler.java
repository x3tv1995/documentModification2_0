package ru.etna.documentmodification2_0.service.equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.StartupManagerService;

@Component("ЭК6500")
public class Ek6500EquipmentHandler implements EquipmentHandler {
    @Autowired
    private StartupManagerService startupManagerService;

    @Override
    public void handler(String pathExcel, String docPath, String pathDirectory, String lastName, String data, String numberInBold) throws Exception {
        String patternFirst = CodeMapping.EK6500_PATTERN_FIRST.getDescription();
        String patternDate = CodeMapping.EK6500_PATTERN_DATE.getDescription();
        String patternSpace = CodeMapping.EK6500_PATTERN_SPACEANDSIZEWORD.getDescription();
        String numberSearch = CodeMapping.EK6500_PATTERN_NUMBER_SEARCH.getDescription();
        String patternNumber = CodeMapping.EK6500_PATTERN_NUMBER.getDescription();
        String replaceNumber = CodeMapping.EK6500_PATTERN_REPLACE_NUMBER.getDescription();
        int fontSize = CodeMapping.EK6500_PATTERN_SPACEANDSIZEWORD.getSize();


        DocumentReplaceRequestDTO dto = new DocumentReplaceRequestDTO(
                docPath,
                lastName,
                data,
                patternFirst,
                patternSpace,
                numberSearch,
                patternNumber,
                fontSize,
                replaceNumber,
                patternDate,
                pathExcel,
                pathDirectory
        );

        startupManagerService.enterDatabase(dto, numberInBold);
    }
}
