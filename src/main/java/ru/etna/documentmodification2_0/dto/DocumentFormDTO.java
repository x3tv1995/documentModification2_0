package ru.etna.documentmodification2_0.dto;

import lombok.Data;

@Data
public class DocumentFormDTO {
    private String pathDirectory;// путь до папки где будем сохранять pdf файлы
    private String docPath;//путь до документа который будем изменять
    private String lastName;
    private String data;
    private String pdfInputFolder; //путь где лежать несколько pdf файлов из которых будем делать один pdf файл
    private String pathExcel;

    public  DocumentFormDTO() {}
}
