package ru.etna.documentmodification2_0.service.equipment;

import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;


@Component("ОКВТ")
public class OkvtEquipmentHandler extends  AbstractEquipmentHandler {

    public OkvtEquipmentHandler(StartupManagerService startupManagerService, DocxUpdateTextService docxUpdateTextService) {
        super(startupManagerService, docxUpdateTextService);
    }

    @Override
    protected String getPatternFirst() {
        return CodeMapping.OKVT_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPatternDate() {
        return CodeMapping.OKVT_PATTERN_DATE.getDescription();
    }

    @Override
    protected String getPatternSpace() {
        return CodeMapping.OKVT_SPACEANDSIZEWORD.getDescription();
    }

    @Override
    protected String getNumberSearch() {
        return CodeMapping.OKVT_NUMBER_SEARCH.getDescription();
    }

    @Override
    protected String getPatternNumber() {
        return CodeMapping.OKVT_NUMBER.getDescription();
    }

    @Override
    protected String getReplaceNumber() {
        return CodeMapping.OKVT_PATTERN_REPLACE_NUMBER.getDescription();
    }

    @Override
    protected int getFontSize() {
        return CodeMapping.OKVT_SPACEANDSIZEWORD.getSize();
    }

    @Override
    protected String getPsiPatternFirst() {
        return CodeMapping.OKVT_PSI_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPsiPatternDate() {
        return CodeMapping.OKVT_PSI_PATTERN_DATE.getDescription();
    }

}
