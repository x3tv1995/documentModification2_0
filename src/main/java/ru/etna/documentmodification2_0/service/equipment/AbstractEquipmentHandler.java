package ru.etna.documentmodification2_0.service.equipment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;
import ru.etna.documentmodification2_0.dto.EquipmentProcessingRequest;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.StartupManagerService;
import ru.etna.documentmodification2_0.service.equipment.handlerImp.EquipmentHandler;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

public abstract class AbstractEquipmentHandler implements EquipmentHandler, EquipmentHandlerForPsi {

    private final StartupManagerService startupManagerService;

    private final DocxUpdateTextService docxUpdateTextService;

    protected abstract String getPatternFirst();
    protected abstract String getPatternDate();
    protected abstract String getPatternSpace();
    protected abstract String getNumberSearch();
    protected abstract String getPatternNumber();
    protected abstract String getReplaceNumber();
    protected abstract int getFontSize();

    protected abstract String getPsiPatternFirst();
    protected abstract String getPsiPatternDate();

    public AbstractEquipmentHandler(StartupManagerService startupManagerService, DocxUpdateTextService docxUpdateTextService) {
        this.startupManagerService = startupManagerService;
        this.docxUpdateTextService = docxUpdateTextService;
    }

    @Override
    public void handler(EquipmentProcessingRequest request) throws Exception {
        DocumentReplaceRequestDTO dto = new DocumentReplaceRequestDTO(
                request.docPath(),
                request.lastName(),
                request.data(),
                getPatternFirst(),
                getPatternSpace(),
                getNumberSearch(),
                getPatternNumber(),
                getFontSize(),
                getReplaceNumber(),
                getPatternDate(),
                request.pathExcel(),
                request.pathDirectory()
        );

        startupManagerService.enterDatabase(dto, request.numberInBold(), request.nameKey());
    }

    @Override
    public void handlerPsi(String lastName, XWPFDocument document, int sizeText) {
        docxUpdateTextService.searchTitleForPsi(
                getPsiPatternFirst(),
                lastName,
                getPsiPatternDate(),
                document,
                sizeText
        );
    }
}
