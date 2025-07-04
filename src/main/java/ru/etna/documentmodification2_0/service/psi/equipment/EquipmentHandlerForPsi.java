package ru.etna.documentmodification2_0.service.psi.equipment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
public interface EquipmentHandlerForPsi {
    void  handlerPsi(String lastName, XWPFDocument document, int sizeText);
}
