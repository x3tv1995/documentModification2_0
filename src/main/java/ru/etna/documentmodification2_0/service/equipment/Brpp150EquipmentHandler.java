package ru.etna.documentmodification2_0.service.equipment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.dto.EquipmentProcessingRequest;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;


@Component("БРПП150")
public class Brpp150EquipmentHandler extends AbstractEquipmentHandler {


    public Brpp150EquipmentHandler(StartupManagerService startupManagerService, DocxUpdateTextService docxUpdateTextService) {
        super(startupManagerService, docxUpdateTextService);
    }

    @Override
    protected String getPatternFirst() {
        return CodeMapping.BRPP150_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPatternDate() {
        return CodeMapping.BRPP150_PATTERN_DATE.getDescription();
    }

    @Override
    protected String getPatternSpace() {
        return CodeMapping.BRPP150_PATTERN_SPACEANDSIZEWORD.getDescription();
    }

    @Override
    protected String getNumberSearch() {
        return CodeMapping.BRPP150_PATTERN_NUMBER_SEARCH.getDescription();
    }

    @Override
    protected String getPatternNumber() {
        return CodeMapping.BRPP150_PATTERN_NUMBER.getDescription();
    }

    @Override
    protected String getReplaceNumber() {
        return CodeMapping.BRPP150_PATTERN_REPLACE_NUMBER.getDescription();
    }

    @Override
    protected int getFontSize() {
        return CodeMapping.BRPP150_PATTERN_SPACEANDSIZEWORD.getSize();
    }

    @Override
    protected String getPsiPatternFirst() {
        return CodeMapping.BRPP150_PSI_PATTERN_FIRST.getDescription();
    }

    @Override
    protected String getPsiPatternDate() {
        return CodeMapping.BRPP150_PSI_PATTERN_DATE.getDescription();
    }

}
