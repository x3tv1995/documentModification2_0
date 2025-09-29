package ru.etna.documentmodification2_0.service.psi;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.DocxUpdateTextService;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class PsiDocxHelper {
    private static final Logger logger = LoggerFactory.getLogger(PsiDocxHelper.class);

    private final DocxUpdateTextService docxUpdateTextService;
    private final Map<String, EquipmentHandlerForPsi> handlers;


    //fillTable- заполнение таблицы ФИО
    void fillTableLastname(XWPFDocument document, String key, String lastName, String number) {
        XWPFTable table = document.getTables().get(0);
        int startRow = 3;
        int totalRowsToFill = 9;
        if (key.startsWith(PsiConstants.VVEK11400_KEY)) totalRowsToFill = 10;
        logger.info("****КЛЮЧ = {},totalRowsToFill ={}", key, totalRowsToFill);

        for (int rowIndex = startRow; rowIndex < startRow + totalRowsToFill && rowIndex < table.getRows().size(); rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            if (row == null) continue;

            List<XWPFTableCell> cells = row.getTableCells();
            if (cells.isEmpty()) continue;

            XWPFTableCell targetCell = determineTargetCell(cells, key, rowIndex);
            if (number.startsWith(PsiConstants.VVEK_11400_VERSIA_01) && rowIndex == 8) continue;
            if (number.startsWith(PsiConstants.VVEK_11400_VERSIA) && rowIndex == 9) continue;
            if (key.startsWith(PsiConstants.VVEK11400_KEY)) {
                addTextToCell(targetCell, lastName, PsiConstants.VVEK_11400_FONT_SIZE);
            }else {
                addTextToCell(targetCell, lastName, PsiConstants.DEFAULT_FONT_SIZE);
            }
        }
    }

    //определение столбца для заполнения
    private XWPFTableCell determineTargetCell(List<XWPFTableCell> cells, String key, int rowIndex) {
        if (key.startsWith(PsiConstants.VVEK11400_KEY) && cells.size() > 5) {
            return cells.get(6);
        }
        if (key.startsWith(PsiConstants.VVEK_KEY) && cells.size() > 5) {
            return cells.get(5);
        }
        if (key.startsWith(PsiConstants.VVEK6500_KEY) && rowIndex ==9){
            return cells.get(6);
        }
        if (rowIndex == 9 || rowIndex == 10) {
            return cells.get(7);
        }
        return cells.get(6);
    }

    //замена номеров в ПСИ ввэк и эк
    void replaceNumbersInPsiVVEKANDEK(XWPFDocument document, String productionNumber) throws FileNotFoundException {
        String searchPattern = CodeMapping.VVEK_PSI_PATTERN_NUMBER_SEARCH.getDescription();
        String numberPattern = CodeMapping.VVEK_PSI_PATTERN_NUMBER.getDescription();
        int patternSize = CodeMapping.VVEK_PSI_PATTERN_NUMBER_SEARCH.getSize();
        int maxReplaced = 2;
        int count = 0;


        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (count >= maxReplaced) break;

            StringBuilder paragraphText = new StringBuilder();
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null) {
                    paragraphText.append(text);
                }
            }

            String fullText = paragraphText.toString();
            logger.info("Текст параграфа: {}", fullText);
            Pattern pattern = Pattern.compile(searchPattern);
            Matcher matcher = pattern.matcher(fullText);
            if (matcher.find()) {
                String newText = numberPattern + productionNumber + " ";
                boolean replaced = docxUpdateTextService.replaceInParagraph(paragraph, fullText, searchPattern, newText, patternSize);
                if (replaced) {
                    count++;
                    logger.info("ЗАМЕНИЛ номер №{}", count);
                }
            }
        }
    }

    public void addTextToCell(XWPFTableCell cell, String text, int fontSize) {
        clearCell(cell);
        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        paragraph.setFirstLineIndent(0);
    }

    // добавление текста в ячейку c настройками для продукции УПП
    void addTextForYpp5(XWPFTableCell cell, String text, int fontSize) {
        if (cell == null || text == null) {
            logger.warn("Ячейка или текст равны null");
            return;
        }

        XWPFParagraph firstParagraph;

        if (cell.getParagraphs().isEmpty()) {
            firstParagraph = cell.addParagraph();
        } else {
            firstParagraph = cell.getParagraphs().get(0);
        }

        List<XWPFRun> runs = firstParagraph.getRuns();

        if (runs == null || runs.isEmpty()) {
            XWPFRun run = firstParagraph.createRun();
            run.setText(text);
            logger.info("Создан новый Run, так как старых не было");
            return;
        }

        XWPFRun originalRun = runs.get(0);

        CTR originalCTR = originalRun != null && originalRun.getCTR() != null ? originalRun.getCTR() : null;

        while (!runs.isEmpty()) {
            firstParagraph.removeRun(0);
        }

        XWPFRun newRun = firstParagraph.createRun();

        if (originalCTR != null) {
            try {
                newRun.getCTR().set(originalCTR);
            } catch (Exception e) {
                logger.error("Ошибка при копировании стиля из оригинального CTR", e);
            }
        } else {
            newRun.setFontSize(10);
            newRun.setBold(false);
            newRun.setItalic(false);
            logger.warn("CTR стиля отсутствует, применяется дефолтный стиль");
        }

        newRun.setText(text);
    }

    private void clearCell(XWPFTableCell cell) {
        while (!cell.getParagraphs().isEmpty()) {
            cell.removeParagraph(0);
        }
    }

    public XWPFDocument cloneDocument(XWPFDocument original) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.write(out);
        return new XWPFDocument(new ByteArrayInputStream(out.toByteArray()));
    }

    public boolean isYppKey(String key) {
        return PsiConstants.YPP1_KEY.equals(key) ||
                PsiConstants.YPP7_KEY.equals(key) ||
                PsiConstants.YPP_KEY.equals(key);
    }

    public int getStartRow(String key) {
        Set<String> keysWithOffset = Set.of(
                PsiConstants.TRO_KEY,
                PsiConstants.SOKT_KEY,
                PsiConstants.OKVA_KEY,
                PsiConstants.OKVT_KEY,
                PsiConstants.NPEK_KEY
        );
        return keysWithOffset.contains(key) ? 3 : 2;
    }

    public void applyEquipmentHandler(String key, String lastName, XWPFDocument document, int fontSize) {
        EquipmentHandlerForPsi handler = handlers.get(key);
        if (handler == null) {
            throw new IllegalStateException("Отсутствует обработчик для ключа: " + key);
        }
        try {
            handler.handlerPsi(lastName, document, fontSize);
        } catch (Exception e) {
            logger.error("Ошибка при обработке ПСИ для " + key, e);
            throw new IllegalStateException("Ошибка применения обработчика ПСИ для " + key, e);
        }
    }
}