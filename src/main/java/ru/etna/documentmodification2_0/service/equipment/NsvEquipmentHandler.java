package ru.etna.documentmodification2_0.service.equipment;

import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;


@Component("НСВ")
public class NsvEquipmentHandler extends AbstractEquipmentHandler{


    public NsvEquipmentHandler(StartupManagerService startupManagerService, DocxUpdateTextService docxUpdateTextService) {
        super(startupManagerService, docxUpdateTextService);
    }

    @Override
    protected String getPatternFirst() {
        return CodeMapping.NSV_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPatternDate() {
        return CodeMapping.NSV_PATTERN_DATE.getDescription();
    }

    @Override
    protected String getPatternSpace() {
        return  CodeMapping.NSV_PATTERN_SPACEANDSIZEWORD.getDescription();
    }

    @Override
    protected String getNumberSearch() {
        return CodeMapping.NSV_PATTERN_NUMBER_SEARCH.getDescription();
    }

    @Override
    protected String getPatternNumber() {
        return CodeMapping.NSV_PATTERN_NUMBER.getDescription();
    }

    @Override
    protected String getReplaceNumber() {
        return CodeMapping.NSV_PATTERN_REPLACE_NUMBER.getDescription();
    }

    @Override
    protected int getFontSize() {
        return CodeMapping.NSV_PATTERN_SPACEANDSIZEWORD.getSize();
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
