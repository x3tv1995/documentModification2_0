package ru.etna.documentmodification2_0.service.equipment;

import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;


@Component("КЭКМ")
public class KekmEquipmentHandler extends AbstractEquipmentHandler {


    public KekmEquipmentHandler(StartupManagerService startupManagerService, DocxUpdateTextService docxUpdateTextService) {
        super(startupManagerService, docxUpdateTextService);
    }

    @Override
    protected String getPatternFirst() {
        return CodeMapping.KEKM_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPatternDate() {
        return CodeMapping.KEKM_PATTERN_DATE.getDescription();
    }

    @Override
    protected String getPatternSpace() {
        return CodeMapping.KEKM_PATTERN_SPACEANDSIZEWORD.getDescription();
    }

    @Override
    protected String getNumberSearch() {
        return CodeMapping.KEKM_PATTERN_NUMBER_SEARCH.getDescription();
    }

    @Override
    protected String getPatternNumber() {
        return CodeMapping.KEKM_PATTERN_NUMBER.getDescription();
    }

    @Override
    protected String getReplaceNumber() {
        return CodeMapping.KEKM_PATTERN_REPLACE_NUMBER.getDescription();
    }

    @Override
    protected int getFontSize() {
        return CodeMapping.KEKM_PATTERN_SPACEANDSIZEWORD.getSize();
    }

    @Override
    protected String getPsiPatternFirst() {
        return CodeMapping.TRO_PSI_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPsiPatternDate() {
        return CodeMapping.TRO_PSI_PATTERN_DATE.getDescription();
    }
}
