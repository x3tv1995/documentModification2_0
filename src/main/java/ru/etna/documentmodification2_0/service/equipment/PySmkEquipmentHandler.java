package ru.etna.documentmodification2_0.service.equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.dto.EquipmentProcessingRequest;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;
import ru.etna.documentmodification2_0.service.equipment.handlerImp.EquipmentHandler;

@Component("ПУ_СМК")
public class PySmkEquipmentHandler extends AbstractEquipmentHandler {


    public PySmkEquipmentHandler(StartupManagerService startupManagerService, DocxUpdateTextService docxUpdateTextService) {
        super(startupManagerService, docxUpdateTextService);
    }

    @Override
    protected String getPatternFirst() {
        return CodeMapping.PYSMK_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPatternDate() {
        return CodeMapping.PYSMK_PATTERN_DATE.getDescription();
    }

    @Override
    protected String getPatternSpace() {
        return CodeMapping.PYSMK_PATTERN_SPACEANDSIZEWORD.getDescription();
    }

    @Override
    protected String getNumberSearch() {
        return CodeMapping.PYSMK_PATTERN_NUMBER_SEARCH.getDescription();
    }

    @Override
    protected String getPatternNumber() {
        return CodeMapping.PYSMK_PATTERN_NUMBER.getDescription();
    }

    @Override
    protected String getReplaceNumber() {
        return CodeMapping.PYSMK_PATTERN_REPLACE_NUMBER.getDescription();
    }

    @Override
    protected int getFontSize() {
        return CodeMapping.PYSMK_PATTERN_SPACEANDSIZEWORD.getSize();
    }

    @Override
    protected String getPsiPatternFirst() {
        return "";
    }

    @Override
    protected String getPsiPatternDate() {
        return "";
    }

}
