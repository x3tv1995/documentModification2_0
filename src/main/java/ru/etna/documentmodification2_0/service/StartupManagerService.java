package ru.etna.documentmodification2_0.service;

import org.jodconverter.core.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class StartupManagerService {
    @Autowired
    private ConvertorService convertorService;
    @Autowired
    private DocxUpdateTextService docxUpdateTextService;
    @Autowired
    private NumberProductionService numberProductionService;
    private static final Logger log = LoggerFactory.getLogger(StartupManagerService.class);

    //ВНИМАТЕЛЬНО ПОСМОТРЕТЬ НОВЫЕ ДАННЫЕ ПО ДОБАВЛЕНИЮ ШАБЛОН СТРОК и т.д.
    public void enterDatabase(String pathExcel, String docPath, String pdfPath,
                              String lastName, String data, String patternFirst,
                              String patterDate, int sizeText, String patternSpace,
                              String numberSearch, String patternNumber, String replaceNumber) throws OfficeException, FileNotFoundException {


        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        File docFile = new File(docPath);
        if (!docFile.exists()) {
            log.error("Ошибка: Файл input.docx не найден по пути: " + docPath);
            return;
        }


        int count = 0;
        if (!data.isEmpty()) {

            for (String number : arr) {
                if (count == 0) {
                    docxUpdateTextService.replaceWordInFile(docPath, number, lastName, data,
                            patternFirst, patternSpace, sizeText, numberSearch, patternNumber);


                    convertorService.convertorDocToPdf(docPath, pdfPath);
                    count++;
                } else {
                    docxUpdateTextService.replaceWordInFile(docPath, number, sizeText, replaceNumber, patternNumber);
                    convertorService.convertorDocToPdf(docPath, pdfPath);
                }
            }

        } else {
            for (String number : arr) {
                if (count == 0) {
                    docxUpdateTextService.replaceWordInFile(docPath, number, lastName, data,
                            patternFirst, patterDate, sizeText, numberSearch, patternNumber);
                    convertorService.convertorDocToPdf(docPath, pdfPath);
                    count++;
                } else {
                    docxUpdateTextService.replaceWordInFile(docPath, number, sizeText, replaceNumber, patternNumber);
                    convertorService.convertorDocToPdf(docPath, pdfPath);
                }
            }

        }
    }

    public void enterDatabase(String pathExcel, String docPath, String pdfPath,
                              int sizeText, String numberSearch, String patternNumber, String replaceNumber) throws OfficeException, FileNotFoundException {
        int count = 0;
        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        if (count == 0) {
            for (String number : arr) {
                docxUpdateTextService.replaceWordInFileOnlyNumber(docPath, number, sizeText, numberSearch, patternNumber);
                convertorService.convertorDocToPdf(docPath, pdfPath);
                count++;
            }
        } else {
            for (String number : arr) {
                docxUpdateTextService.replaceWordInFile(docPath, number, sizeText, replaceNumber, patternNumber);
                convertorService.convertorDocToPdf(docPath, pdfPath);
            }
        }
    }
}
