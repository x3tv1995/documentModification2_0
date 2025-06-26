package ru.etna.documentmodification2_0.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
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
    //добавил метод   docxUpdateTextService.numbersInBold(docPath,numberInBold) и переменнную  String numberInBold
    public void enterDatabase(String pathExcel, String docPath, String pdfPath,
                              String lastName, String data, String patternFirst,
                              String patterDate, int sizeText, String patternSpace,
                              String numberSearch, String patternNumber, String replaceNumber,String numberInBold) throws Exception {


        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        File docFile = new File(String.valueOf(docPath));
        if (!docFile.exists()) {
            log.error("Ошибка: Файл input.docx не найден по пути:{}", docPath);
            return;
        }


        boolean firstIteration  = true;
        if (!data.isEmpty()) {

            for (String number : arr) {
                if (firstIteration) {
                    docxUpdateTextService.replaceWordInFile(docPath, number, lastName, data,
                            patternFirst, patternSpace, sizeText, numberSearch, patternNumber);

                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pdfPath);
                    firstIteration = false;
                } else {
                    docxUpdateTextService.replaceWordInFile(docPath, number, sizeText, replaceNumber, patternNumber);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pdfPath);
                }
            }

        } else {
            for (String number : arr) {
                if (firstIteration) {
                    docxUpdateTextService.replaceWordInFile(docPath, number, lastName,
                            patternFirst, patterDate, sizeText, numberSearch, patternNumber);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pdfPath);
                    firstIteration = false;
                } else {
                    docxUpdateTextService.replaceWordInFile(docPath, number, sizeText, replaceNumber, patternNumber);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pdfPath);
                }
            }

        }
    }

    public void enterDatabase(String pathExcel, String docPath, String pdfPath,
                              int sizeText, String numberSearch, String patternNumber, String replaceNumber,String numberInBold) throws Exception {
//        int count = 0;
        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        boolean firstIteration  = true;
        for (String number : arr) {
            try {
                if (firstIteration) {
                    docxUpdateTextService.replaceWordInFileOnlyNumber(docPath, number, sizeText, numberSearch, patternNumber);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    firstIteration = false;
                } else {
                    docxUpdateTextService.replaceWordInFile(docPath, number, sizeText, replaceNumber, patternNumber);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                }
                convertorService.convertorDocToPdf(docPath, pdfPath);

            } catch (Exception e) {
                log.error("Error processing number: " + number, e);
                throw new Exception("Ошибка обработки номера " + number + ": " + e.getMessage(), e);
            }
        }
//        if (count == 0) {
//            for (String number : arr) {
//                docxUpdateTextService.replaceWordInFileOnlyNumber(String.valueOf(docPath), number, sizeText, numberSearch, patternNumber);
//                convertorService.convertorDocToPdf(docPath, pdfPath);
//                count++;
//            }
//        } else {
//            for (String number : arr) {
//                docxUpdateTextService.replaceWordInFile(docPath, number, sizeText, replaceNumber, patternNumber);
//                convertorService.convertorDocToPdf(docPath, pdfPath);
//            }
//        }
    }


}
