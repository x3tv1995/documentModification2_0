package ru.etna.documentmodification2_0.dto;

import lombok.Data;

import java.io.File;
@Data
public class TempFilesDTO {
    private final String docxPath;
    private final String excelPath;
}
