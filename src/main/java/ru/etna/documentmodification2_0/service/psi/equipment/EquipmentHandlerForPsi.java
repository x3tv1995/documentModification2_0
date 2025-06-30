package ru.etna.documentmodification2_0.service.psi.equipment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface EquipmentHandlerForPsi {
    void  handlerPsi(String lastName, XWPFDocument document, int sizeText);
}
