package ru.etna.documentmodification2_0.service.psi;

import org.apache.poi.xwpf.usermodel.*;
import org.jodconverter.core.office.OfficeException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.service.*;
import ru.etna.documentmodification2_0.service.psi.equipment.EquipmentHandlerForPsi;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
public class PsiService {
    @Autowired
    NumberProductionService numberProductionService;
    @Autowired
    FilterEquipmentService filterEquipmentService;
    @Autowired
    ConvertorService convertorService;
    @Autowired
    private Map<String, EquipmentHandlerForPsi> handlers;
    private static final Logger logger = LoggerFactory.getLogger(ConvertorService.class);
    private static final String TRO_KEY = "ТРО";
    private static final String SOKT_KEY = "СОКТ";
    private static final String OKVT_KEY = "ОКВТ";
    private static final String YPP1_KEY = "УПП1";
    private static final String YPP7_KEY = "УПП7";
    private static final String YPP_KEY = "УПП";
    private static final String NPEK_KEY = "НПЭК";
    private static final String OKVA_KEY = "ОКВА";
    private static final int MAX_ROWS = 10;
    private static final int FIRST_TABLE_INDEX = 0;
    private static final int DEFAULT_FONT_SIZE = 10;
    private static final int DEFAULT_SIZE_TEXT = 12;


    public void fillingFirstColumnInTable(String pathFile, String outputFile, String pathExcel, String lastname) throws IOException, OfficeException {

        String key = filterEquipmentService.filterBybnshi(pathExcel);

        if (pathFile.isEmpty()) {
            throw new IllegalArgumentException("Ошибка файл пуст");
        }
        int count = 0;
        int rowCounter = 0;
        int startRow = 2;
        XWPFDocument document = null;
        XWPFTable table = null;
        try (FileInputStream fis = new FileInputStream(Objects.requireNonNull(pathFile))) {
            document = new XWPFDocument(fis);
            table = document.getTables().get(FIRST_TABLE_INDEX);
            List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
            for (int i = 0; i < arr.size(); i++) {

                if (count != MAX_ROWS) {
                    if (TRO_KEY.equals(key) ||
                            SOKT_KEY.equals(key) ||
                            OKVA_KEY.equals(key) ||
                            OKVT_KEY.equals(key) ||
                            NPEK_KEY.equals(key)) {
                        startRow = 3;
                    }
                    int targetIndex = rowCounter + startRow;

                    if (targetIndex >= table.getNumberOfRows()) {
                        logger.error("Превышено количество строк в таблице. Невозможно добавить больше записей. " +
                                "Проверьте, возможно, вы пытаетесь загрузить номера от ТРО в обычный шаблон.");

                        throw new IllegalArgumentException("Ошибка: количество изделий превышает вместимость таблицы. " +
                                "Вероятно, вы пытаетесь ввести номера от ТРО в неподходящий шаблон.");
                    }

                    XWPFTableRow row = table.getRows().get(targetIndex);


                    List<XWPFTableCell> cells = row.getTableCells();

                    //ячейка т.е. столбец
                    XWPFTableCell cell = cells.get(1);
                    //тут надо подумать
                    if (key.equals(YPP1_KEY) ||
                            key.equals(YPP7_KEY) ||
                            key.equals(YPP_KEY)) {
                        addTextForYpp5(cell, arr.get(i), DEFAULT_FONT_SIZE);
                    } else {
                        addTextToCell(cell, arr.get(i), DEFAULT_FONT_SIZE);
                    }
                    count++;
                    rowCounter++;


                    if ((i + 1) % MAX_ROWS == 0 || i == arr.size() - 1) {
                        try {

                            EquipmentHandlerForPsi handler = handlers.get(key);
                            handler.handlerPsi(lastname, document, DEFAULT_SIZE_TEXT);
                        } catch (Exception e) {
                            logger.error("Отсутствует изменение ПСИ для " + key, e.getMessage());
                            throw new IllegalStateException("Отсутствует изменение ПСИ для " + key, e);
                        }

                        try (ByteArrayOutputStream fileInMemory = new ByteArrayOutputStream()) {
                            document.write(fileInMemory);
                            String uniqueFileName = "PSI" + System.currentTimeMillis() + ".docx";
                            String filePath = outputFile + "\\" + uniqueFileName;
                            convertorService.convertFromStreamToPdf(fileInMemory, filePath);
                        }
                        document = new XWPFDocument(new FileInputStream(pathFile));
                        table = document.getTables().get(FIRST_TABLE_INDEX);
                        rowCounter = 0;
                        count = 0;
                    }


                }
            }


        }
    }

    private void clearCell(XWPFTableCell cell) {
        while (!cell.getParagraphs().isEmpty()) {
            cell.removeParagraph(0);
        }
    }

    private void addTextToCell(XWPFTableCell cell, String text, int fontSize) {
        clearCell(cell);
        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        paragraph.setFirstLineIndent(0);


    }

    private void addTextForYpp5(XWPFTableCell cell, String text, int fontSize) {
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
}

