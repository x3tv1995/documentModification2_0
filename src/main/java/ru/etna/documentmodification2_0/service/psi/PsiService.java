package ru.etna.documentmodification2_0.service.psi;

import org.apache.poi.xwpf.usermodel.*;
import org.jodconverter.core.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.*;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Service
public class PsiService {
    @Autowired
    NumberProductionService numberProductionService;
    @Autowired
    DocxUpdateTextService docxUpdateTextService;
    @Autowired
    FilterEquipmentService filterEquipmentService;
    @Autowired
    ConvertorService convertorService;

    private static final Logger logger = LoggerFactory.getLogger(ConvertorService.class);
    private static final String TRO_KEY = "ТРО";
    private static final int MAX_ROWS = 10;
    private static final int FIRST_TABLE_INDEX = 0;
    private static final int DEFAULT_FONT_SIZE = 10;



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
                    if (TRO_KEY.equals(key)) {
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
                    System.out.println(table);

                    //ячейка т.е. столбец
                    XWPFTableCell cell = cells.get(1);

                    clearCell(cell);
                    addTextToCell(cell, arr.get(i), DEFAULT_FONT_SIZE);
                    count++;
                    rowCounter++;


                    if ((i + 1) % MAX_ROWS == 0 || i == arr.size() - 1) {
                        docxUpdateTextService.searchTitleForPsi(CodeMapping.PSI_PATTERN_FIRST.getDescription(), lastname, CodeMapping.PSI_PATTERN_DATE.getDescription(), document, 12);

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
        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        paragraph.setFirstLineIndent(0);
    }

}
