package ru.etna.documentmodification2_0.service.equipment;

public interface EquipmentHandler {
     void handler(String pathExcel, String docPath, String pathDirectory,
                  String lastName, String data, String numberInBold) throws Exception;


}
