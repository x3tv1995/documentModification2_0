package ru.etna.documentmodification2_0.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class DocxUpdateTextService {
    private  static Logger logger = LoggerFactory.getLogger(DocxUpdateTextService.class);
    public  void replaceWordInFile(String docPath, String number, String lastName, String data, String patternFirst,
                                         String patternSpace, int sizeText, String numberSearch, String patternNumber) {
        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
            boolean otkReplaced = false;
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
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

                    Pattern pattern = Pattern.compile(numberSearch);
                    Matcher matcher = pattern.matcher(fullText);

                    if (matcher.find()) {
                        String newText = matcher.replaceAll(patternNumber + number + " ");
                        logger.info("Нашёл заводской номер и стараюсь заменить: " + newText);
                        while (runs.size() > 0) {
                            paragraph.removeRun(0);
                            runs = paragraph.getRuns(); // Обновляем список runs
                        }
                        XWPFRun newRun = paragraph.createRun();
                        newRun.setText(newText, 0);
                        newRun.setFontSize(sizeText);
                        logger.info("ЗАМЕНИЛ!!!!");
                        numberReplaced = true;

                    }
                }
                logger.info("Представитель ОТК: " + numberReplaced + " == True значит номер поменял ");
                if (!otkReplaced) {
                    String template = "Представитель ОТК\\s*_+\\s*_+\\s*_+";
                    Pattern patternResearchOTK = Pattern.compile(template);
                    Matcher matcherOTK = patternResearchOTK.matcher(fullText);

                    if (matcherOTK.find()) {
                        logger.info("ТУТ БУДЕТ ЗАМЕНА ВНИМАНИЕ!!!!");

                        // Заменяю только найденный участок, сохраняя остальной текст
                        String newText = matcherOTK.replaceAll(patternFirst + lastName + patternSpace + data);

                        // удаляю все XWPFRun - ПЕРЕД созданием нового Run
                        while (runs.size() > 0) {
                            paragraph.removeRun(0);
                            runs = paragraph.getRuns(); // Обновляем список runs
                        }
                        XWPFRun newRun = paragraph.createRun();
                        newRun.setText(newText, 0);
                        newRun.setFontSize(sizeText);
                        logger.info("ЗАМЕНИЛ!!!!");
                        otkReplaced = true;
                    }
                }


            }


            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  void replaceWordInFileOnlyNumber(String docPath, String number,
                                                   int sizeText, String numberSearch, String patternNumber) {
        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
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

                    Pattern pattern = Pattern.compile(numberSearch);
                    Matcher matcher = pattern.matcher(fullText);

                    if (matcher.find()) {
                        String newText = matcher.replaceAll(patternNumber + number + " ");
                        logger.info("Нашёл заводской номер и стараюсь заменить: " + newText);
                        while (runs.size() > 0) {
                            paragraph.removeRun(0);
                            runs = paragraph.getRuns(); // Обновляем список runs
                        }
                        XWPFRun newRun = paragraph.createRun();
                        newRun.setText(newText, 0);
                        newRun.setFontSize(sizeText);
                        logger.info("ЗАМЕНИЛ!!!!");
                        numberReplaced = true;

                    }
                }
                logger.info("Представитель ОТК: " + numberReplaced + " == True значит номер поменял ");


            }

            // Сохраняем изменения
            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void replaceWordInFile(String docPath, String number, int sizeText, String replaceNumber, String patternNumber) {
        logger.info("Зашёл для работы");

        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (numberReplaced) break;
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


                Pattern pattern = Pattern.compile(replaceNumber);
                Matcher matcher = pattern.matcher(fullText);
                if (!numberReplaced) {
                    if (matcher.find()) {
                        String newText = matcher.replaceFirst(patternNumber + number);
                        logger.info("Нашёл заводской номер и стараюсь заменить: " + newText);
                        while (runs.size() > 0) {
                            paragraph.removeRun(0);
                            runs = paragraph.getRuns(); // Обновляем список runs
                        }
                        XWPFRun newRun = paragraph.createRun();
                        newRun.setText(newText, 0);
                        newRun.setFontSize(sizeText);
                        logger.info("ЗАМЕНИЛ!!!!");
                        numberReplaced = true;
                    }
                }
            }
            //  Сохраняем документ
            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public  void replaceWordInFile(String docPath, String number, String lastName,
                                         String patternFirst, String patterDate, int sizeText,
                                         String numberSearch, String patternNumber) {
        try (FileInputStream fis = new FileInputStream(docPath);
             XWPFDocument document = new XWPFDocument(fis)) {
            boolean otkReplaced = false;
            boolean numberReplaced = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
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
                    Pattern pattern = Pattern.compile(numberSearch);
                    Matcher matcher = pattern.matcher(fullText);

                    if (matcher.find()) {
                        String newText = matcher.replaceAll(patternNumber + number);
                        logger.info("Нашёл заводской номер и стараюсь заменить: " + newText);
                        while (runs.size() > 0) {
                            paragraph.removeRun(0);
                            runs = paragraph.getRuns(); // Обновляем список runs
                        }
                        XWPFRun newRun = paragraph.createRun();
                        newRun.setText(newText, 0);
                        newRun.setFontSize(sizeText);
                        logger.info("ЗАМЕНИЛ!!!!");
                        numberReplaced = true;
                    }
                }
                logger.info("Представитель ОТК: " + numberReplaced + " == True значит номер поменял ");
                if (!otkReplaced) {
                    String template = "Представитель ОТК\\s*_+\\s*_+\\s*_+";
                    Pattern patternResearchOTK = Pattern.compile(template);
                    Matcher matcherOTK = patternResearchOTK.matcher(fullText);

                    if (matcherOTK.find()) {
                        logger.info("ТУТ БУДЕТ ЗАМЕНА ВНИМАНИЕ!!!!");

                        String newText = matcherOTK.replaceAll(patternFirst + lastName + patterDate);


                        while (runs.size() > 0) {
                            paragraph.removeRun(0);
                            runs = paragraph.getRuns();
                        }
                        XWPFRun newRun = paragraph.createRun();
                        newRun.setText(newText, 0);
                        newRun.setFontSize(sizeText);
                        logger.info("ЗАМЕНИЛ!!!!");
                        otkReplaced = true;
                    }
                }


            }

            // Сохраняем изменения
            try (FileOutputStream fos = new FileOutputStream(docPath)) {
                document.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


