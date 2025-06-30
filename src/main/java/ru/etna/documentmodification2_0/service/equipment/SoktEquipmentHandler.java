package ru.etna.documentmodification2_0.service.equipment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

@Component("СОКТ")
public class SoktEquipmentHandler implements EquipmentHandler, EquipmentHandlerForPsi {
    @Autowired
    private StartupManagerService startupManagerService;
    @Autowired
    private DocxUpdateTextService docxUpdateTextService;
    @Override
    public void handler(String pathExcel, String docPath, String pathDirectory, String lastName, String data, String numberInBold) throws Exception {

        String patternFirst = CodeMapping.SOKT_PATTERN_FIRST.getDescription();
        String patternDate = CodeMapping.SOKT_PATTERN_DATE.getDescription();
        String patternSpace = CodeMapping.SOKT_SPACEANDSIZEWORD.getDescription();
        String numberSearch = CodeMapping.SOKT_NUMBER_SEARCH.getDescription();
        String patternNumber = CodeMapping.SOKT_NUMBER.getDescription();
        String replaceNumber = CodeMapping.SOKT_PATTERN_REPLACE_NUMBER.getDescription();
        int fontSize = CodeMapping.SOKT_SPACEANDSIZEWORD.getSize();


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
    @Override
    public void handlerPsi(String lastName, XWPFDocument document, int sizeText) {
        String patternFirst = CodeMapping.SOKT_PSI_PATTERN_FIRST.getDescription();
        String patternDate =  CodeMapping.SOKT_PSI_PATTERN_DATE.getDescription();
        docxUpdateTextService.searchTitleForPsi(patternFirst,lastName,patternDate,document,sizeText);
    }
}
