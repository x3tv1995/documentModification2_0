package ru.etna.documentmodification2_0.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentFormDTO {
    private String pathDirectory;// путь до папки где будем сохранять pdf файлы
    private MultipartFile docPath;//путь до документа который будем изменять
    private String lastName;
    private String data;
    private String pdfInputFolder; //путь где лежать несколько pdf файлов из которых будем делать один pdf файл
    private MultipartFile pathExcel;

    public DocumentFormDTO() {
    }
}
