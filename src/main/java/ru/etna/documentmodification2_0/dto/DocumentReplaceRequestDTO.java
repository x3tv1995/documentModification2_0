package ru.etna.documentmodification2_0.dto;


import lombok.Data;

@Data
public class DocumentReplaceRequestDTO {
    private String docPath;
//    private String number;
    private String lastName;
    private String data;

    private String patternFirst;
    private String patternSpace;
    private String numberSearch;
    private String patternNumber;

    private int fontSize;
    private  String replaceNumber;
    private  String patterDate;

    private String pathExcel;
    private String pdfPath;
    private  String pathDirectory;

    public DocumentReplaceRequestDTO(String docPath, String lastName, String data, String patternFirst,
                                     String patternSpace, String numberSearch, String patternNumber,
                                     int fontSize, String replaceNumber, String patterDate, String pathExcel,
                                     String pathDirectory) {
        this.docPath = docPath;
        this.lastName = lastName;
        this.data = data;
        this.patternFirst = patternFirst;
        this.patternSpace = patternSpace;
        this.numberSearch = numberSearch;
        this.patternNumber = patternNumber;
        this.fontSize = fontSize;
        this.replaceNumber = replaceNumber;
        this.patterDate = patterDate;
        this.pathExcel = pathExcel;
        this.pathDirectory = pathDirectory;
    }

    public DocumentReplaceRequestDTO() {

    }

    public DocumentReplaceRequestDTO(String docPath, String lastName, String data, String patternFirst,
                                     String patternSpace, String numberSearch, String patternNumber,
                                     int fontSize, String replaceNumber, String patterDate, String pathExcel, String pdfPath, String pathDirectory) {
        this.docPath = docPath;
        this.lastName = lastName;
        this.data = data;
        this.patternFirst = patternFirst;
        this.patternSpace = patternSpace;
        this.numberSearch = numberSearch;
        this.patternNumber = patternNumber;
        this.fontSize = fontSize;
        this.replaceNumber = replaceNumber;
        this.patterDate = patterDate;
        this.pathExcel = pathExcel;
        this.pdfPath = pdfPath;
        this.pathDirectory = pathDirectory;
    }

    public DocumentReplaceRequestDTO(String docPath, String numberSearch, String patternNumber,
                                     int fontSize, String replaceNumber, String pathExcel,
                                     String pathDirectory) {
        this.docPath = docPath;
        this.numberSearch = numberSearch;
        this.patternNumber = patternNumber;
        this.fontSize = fontSize;
        this.replaceNumber = replaceNumber;
        this.pathExcel = pathExcel;
        this.pathDirectory = pathDirectory;
    }
}
