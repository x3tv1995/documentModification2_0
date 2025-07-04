package ru.etna.documentmodification2_0.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.dto.DocumentReplaceRequestDTO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
public class DocxUpdateTextService {
    private final Logger logger = LoggerFactory.getLogger(DocxUpdateTextService.class);


    public void replaceWordInFile(DocumentReplaceRequestDTO documentReplaceRequestDTO, String number) {
        String docPath = documentReplaceRequestDTO.getDocPath();
        String patternNumber = documentReplaceRequestDTO.getPatternNumber();
        String numberSearch = documentReplaceRequestDTO.getNumberSearch();
        int sizeText = documentReplaceRequestDTO.getFontSize();
        String patternFirst = documentReplaceRequestDTO.getPatternFirst();
        String lastName = documentReplaceRequestDTO.getLastName();
        String patternSpace = documentReplaceRequestDTO.getPatternSpace();
        String data = documentReplaceRequestDTO.getData();


        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
//        try {
//
//            documentCacheService.templateLoad(docPath);
//            XWPFDocument document = documentCacheService.getCachedDocTemplate();
            boolean otkReplaced = false;
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (otkReplaced && numberReplaced) {
                    break;
                }


                List<XWPFRun> runs = paragraph.getRuns();
                StringBuilder paragraphText = new StringBuilder(); // Собираем текст параграфа

                // Собираем текст из всех XWPFRun
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        paragraphText.append(text);
                    }
                }

                String fullText = paragraphText.toString();


                logger.info("Заводской №: " + numberReplaced + " == True значит номер поменял ");
                logger.info(fullText);
                if (!numberReplaced) {
                    String newText = patternNumber + number + " ";
                    numberReplaced = replaceInParagraph(paragraph, fullText, numberSearch, newText, sizeText);
                    logger.info("ЗАМЕНИЛ!!!!");
                }

                logger.info("Представитель ОТК: " + numberReplaced + " == True значит номер поменял ");
                if (!otkReplaced) {
                    String template = "Представитель ОТК\\s*_+\\s*_+\\s*_+";
                    String replacement = patternFirst + lastName + patternSpace + data;
                    otkReplaced = replaceInParagraph(paragraph, fullText, template, replacement, sizeText);
                    logger.info("Представитель ОТК: {} == {}", lastName, otkReplaced ? "заменён" : "не найден");
                }

            }
            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());

        }
    }


    public void replaceWordInFileOnlyNumber(DocumentReplaceRequestDTO documentReplaceRequestDTO, String number) {
        String docPath = documentReplaceRequestDTO.getDocPath();
        String patternNumber = documentReplaceRequestDTO.getPatternNumber();
        String numberSearch = documentReplaceRequestDTO.getNumberSearch();
        int sizeText = documentReplaceRequestDTO.getFontSize();

        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
//        try {
//
//            documentCacheService.templateLoad(docPath);
//            XWPFDocument document = documentCacheService.getCachedDocTemplate();
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {

                if (numberReplaced) {
                    break;
                }
                List<XWPFRun> runs = paragraph.getRuns();

                StringBuilder paragraphText = new StringBuilder();

                for (XWPFRun run : runs) {
                    String text = run.getText(0);

                    if (text != null) {
                        paragraphText.append(text);
                    }
                }

                String fullText = paragraphText.toString();
                logger.info("Заводской №: " + numberReplaced + " == True значит номер поменял ");
                logger.info(fullText);

                if (!numberReplaced) {
                    String newText = patternNumber + number + " ";
                    numberReplaced = replaceInParagraph(paragraph, fullText, numberSearch, newText, sizeText);
                    logger.info("ЗАМЕНИЛ!!!!");
                }

            }
            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }
    }


    public void replaceWordInFileReplay(DocumentReplaceRequestDTO documentReplaceRequestDTO, String number) {
        String docPath = documentReplaceRequestDTO.getDocPath();
        String patternNumber = documentReplaceRequestDTO.getPatternNumber();
        int sizeText = documentReplaceRequestDTO.getFontSize();
        String replaceNumber = documentReplaceRequestDTO.getReplaceNumber();
        logger.info("Зашёл для работы");

        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
//        try {
//
//            documentCacheService.templateLoad(docPath);
//            XWPFDocument document = documentCacheService.getCachedDocTemplate();
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (numberReplaced) break;
                List<XWPFRun> runs = paragraph.getRuns();
                StringBuilder paragraphText = new StringBuilder(); // Собираем текст параграфа

                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        paragraphText.append(text);
                    }
                }

                String fullText = paragraphText.toString();
                logger.info("Заводской №: " + numberReplaced + " == True значит номер поменял ");
                logger.info(fullText);

                if (!numberReplaced) {
                    String newText = patternNumber + number;
                    numberReplaced = replaceInParagraph(paragraph, fullText, replaceNumber, newText, sizeText);
                    logger.info("ЗАМЕНИЛ!!!!");
                }

            }
            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    // добавил метод
    public void numbersInBold(String docPath, String replaceNumber, int size) {
        logger.info("Зашёл для работы");

        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            boolean numberReplaced = false;

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (numberReplaced) break;

                List<XWPFRun> runs = paragraph.getRuns();
                if (runs.isEmpty()) continue;

                StringBuilder fullTextBuilder = new StringBuilder();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        fullTextBuilder.append(text);
                    }
                }

                String fullText = fullTextBuilder.toString();
                logger.info("Полный текст параграфа: " + fullText);

                Pattern pattern = Pattern.compile(replaceNumber);
                Matcher matcher = pattern.matcher(fullText);

                if (!numberReplaced && matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();

                    String before = fullText.substring(0, start);
                    String matched = fullText.substring(start, end);
                    String after = fullText.substring(end);

                    while (!paragraph.getRuns().isEmpty()) {
                        paragraph.removeRun(0);
                    }


                    if (!before.isEmpty()) {
                        XWPFRun beforeRun = paragraph.createRun();
                        beforeRun.setText(before);
                        beforeRun.setFontSize(size);
                        beforeRun.setBold(false);
                    }

                    if (!matched.isEmpty()) {
                        XWPFRun boldRun = paragraph.createRun();
                        boldRun.setText(matched);
                        boldRun.setFontSize(size);
                        boldRun.setBold(true);
                        logger.info("Выделил жирным: " + matched);
                        numberReplaced = true;
                    }


                    if (!after.isEmpty()) {
                        XWPFRun afterRun = paragraph.createRun();
                        afterRun.setText(after);
                        afterRun.setFontSize(size);
                        afterRun.setBold(false);
                    }

                }
            }


            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обработке документа", e);
        }
    }


    public void replaceWordInFileNoDate(DocumentReplaceRequestDTO documentReplaceRequestDTO, String number) {
        String docPath = documentReplaceRequestDTO.getDocPath();
        String patternNumber = documentReplaceRequestDTO.getPatternNumber();
        String numberSearch = documentReplaceRequestDTO.getNumberSearch();
        int sizeText = documentReplaceRequestDTO.getFontSize();
        String patternFirst = documentReplaceRequestDTO.getPatternFirst();
        String lastName = documentReplaceRequestDTO.getLastName();
        String patterDate = documentReplaceRequestDTO.getPatterDate();

        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
//        try {
//
//            documentCacheService.templateLoad(docPath);
//            XWPFDocument document = documentCacheService.getCachedDocTemplate();
            boolean otkReplaced = false;
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (otkReplaced && numberReplaced) {
                    break;
                }
                List<XWPFRun> runs = paragraph.getRuns();
                StringBuilder paragraphText = new StringBuilder(); // Собираем текст параграфа

                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        paragraphText.append(text);
                    }
                }

                String fullText = paragraphText.toString();
                logger.info("Заводской №: " + numberReplaced + " == True значит номер поменял ");
                logger.info(fullText);
                if (!numberReplaced) {
                    String newText = patternNumber + number + " ";
                    numberReplaced = replaceInParagraph(paragraph, fullText, numberSearch, newText, sizeText);
                    logger.info("ЗАМЕНИЛ!!!!");
                }
                logger.info("Представитель ОТК: " + numberReplaced + " == True значит номер поменял ");
                if (!otkReplaced) {
                    String template = "Представитель ОТК\\s*_+\\s*_+\\s*_+";
                    String replacement = patternFirst + lastName + patterDate;
                    otkReplaced = replaceInParagraph(paragraph, fullText, template, replacement, sizeText);
                    logger.info("Представитель ОТК: {} == {}", lastName, otkReplaced ? "заменён" : "не найден");
                }


            }
            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка" + e.getMessage());
        }
    }

    public void searchTitleForPsi(String patternFirst,
                                  String lastName, String patterDate, XWPFDocument document, int sizeText) {
        boolean otkReplaced = false;
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (otkReplaced) {
                break;
            }
            List<XWPFRun> runs = paragraph.getRuns();

            StringBuilder paragraphText = new StringBuilder();

            for (XWPFRun run : runs) {
                String text = run.getText(0);
                if (text != null) {
                    paragraphText.append(text);
                }
            }

            String fullText = paragraphText.toString();
            if (!otkReplaced) {
                String template = "Представитель ОТК\\s*_+\\s*_+\\s*_+";
                String replacement = patternFirst + lastName + patterDate;
                otkReplaced = replaceInParagraph(paragraph, fullText, template, replacement, sizeText);
                logger.info("Представитель ОТК: {} == {}", lastName, otkReplaced ? "заменён" : "не найден");

                if (!otkReplaced) {
                    logger.warn("Не удалось найти шаблон в параграфе: {}", fullText);
                }
            }
        }
    }

    //замена текста
    private boolean replaceInParagraph(XWPFParagraph paragraph, String fullText,
                                       String pattern, String replacement, int fontSize) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fullText);
        if (m.find()) {
            String newText = m.replaceAll(replacement);
            
            while (!paragraph.getRuns().isEmpty()) {
                paragraph.removeRun(0);
            }

            XWPFRun newRun = paragraph.createRun();
            newRun.setText(newText, 0);
            newRun.setFontSize(fontSize);

            return true;
        }
        return false;
    }


}


