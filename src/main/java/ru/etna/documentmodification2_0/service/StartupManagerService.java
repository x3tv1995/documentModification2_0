package ru.etna.documentmodification2_0.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;

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
    public void enterDatabase(DocumentReplaceRequestDTO documentReplaceRequestDTO, String numberInBold) throws Exception {
        String docPath = documentReplaceRequestDTO.getDocPath();
        int sizeText = documentReplaceRequestDTO.getFontSize();
        String data = documentReplaceRequestDTO.getData();
        String pathExcel = documentReplaceRequestDTO.getPathExcel();
        String pathDirectory = documentReplaceRequestDTO.getPathDirectory();


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
                    docxUpdateTextService.replaceWordInFile(documentReplaceRequestDTO,number);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pathDirectory);
                    firstIteration = false;
                } else {
                    docxUpdateTextService.replaceWordInFileReplay(documentReplaceRequestDTO,number);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pathDirectory);
                }
            }

        } else {
            for (String number : arr) {
                if (firstIteration) {
                    docxUpdateTextService.replaceWordInFileNoDate(documentReplaceRequestDTO,number);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pathDirectory);
                    firstIteration = false;
                } else {
                    docxUpdateTextService.replaceWordInFileReplay(documentReplaceRequestDTO,number);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    convertorService.convertorDocToPdf(docPath, pathDirectory);
                }
            }

        }
    }

    public void enterDatabaseDefault(DocumentReplaceRequestDTO documentReplaceRequestDTO,String numberInBold) throws Exception {
        String docPath = documentReplaceRequestDTO.getDocPath();
        int sizeText = documentReplaceRequestDTO.getFontSize();
        String pathExcel = documentReplaceRequestDTO.getPathExcel();
        String pathDirectory = documentReplaceRequestDTO.getPathDirectory();

        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        boolean firstIteration  = true;
        for (String number : arr) {
            try {
                if (firstIteration) {
                    docxUpdateTextService.replaceWordInFileOnlyNumber(documentReplaceRequestDTO,number);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                    firstIteration = false;
                } else {
                    docxUpdateTextService.replaceWordInFile(documentReplaceRequestDTO,number);
                    docxUpdateTextService.numbersInBold(docPath,numberInBold,sizeText);
                }
                convertorService.convertorDocToPdf(docPath, pathDirectory);

            } catch (Exception e) {
                log.error("Error processing number: " + number, e);
                throw new Exception("Ошибка обработки номера " + number + ": " + e.getMessage(), e);
            }
        }
    }


}
