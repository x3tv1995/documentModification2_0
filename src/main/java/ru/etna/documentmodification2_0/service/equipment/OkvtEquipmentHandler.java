package ru.etna.documentmodification2_0.service.equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.StartupManagerService;

@Component("ОКВТ_СОКТ")
public class OkvtEquipmentHandler implements EquipmentHandler {
    @Autowired
    private StartupManagerService startupManagerService;
    @Override
    public void handler(String pathExcel, String docPath, String pathDirectory, String lastName, String data, String numberInBold) throws Exception {

        String patternFirst = CodeMapping.OKVT_PATTERN_FIRST.getDescription();
        String patternDate = CodeMapping.OKVT_PATTERN_DATE.getDescription();
        String patternSpace = CodeMapping.OKVT_SPACEANDSIZEWORD.getDescription();
        String numberSearch = CodeMapping.OKVT_NUMBER_SEARCH.getDescription();
        String patternNumber = CodeMapping.OKVT_NUMBER.getDescription();
        String replaceNumber = CodeMapping.OKVT_PATTERN_REPLACE_NUMBER.getDescription();
        int fontSize = CodeMapping.OKVT_SPACEANDSIZEWORD.getSize();


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
