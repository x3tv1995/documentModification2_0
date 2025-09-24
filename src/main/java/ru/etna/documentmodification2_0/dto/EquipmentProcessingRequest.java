package ru.etna.documentmodification2_0.dto;

public record EquipmentProcessingRequest(
        String pathExcel,
        String docPath,
        String pathDirectory,
        String lastName,
        String data,
        String numberInBold,
        String nameKey
) {
//    public HandlerDTO (
//        String pathExcel,
//        String docPath,
//        String pathDirectory,
//        String lastName,
//        String data,
//        String numberInBold){
//        this(pathExcel, docPath, pathDirectory, lastName, data, numberInBold, null);
//    }
}
