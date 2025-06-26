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
import java.util.ArrayList;
import java.util.Comparator;
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
    ScannerFileService scannerFileService;
    @Autowired
    ConvertorService convertorService;

    private static final Logger logger = LoggerFactory.getLogger(ConvertorService.class);

    public  void fillingFirstColumnInTable(String pathFile, String outputFile, String pathExcel,String lastname) throws IOException, OfficeException {

        String key = filterEquipmentService.filterBybnshi(pathExcel);

        if (pathFile.isEmpty()) {
            throw new IllegalArgumentException("Ошибка файл пуст");
        }
        int count = 0;
        int rowCounter = 0;
        int startRow = 2;
        int firstTable = 0;
        int countRow = 10;
        int sizeFont = 10;
        String tro = "ТРО";
        XWPFDocument document = null;
        XWPFTable table = null;
        try (FileInputStream fis = new FileInputStream(Objects.requireNonNull(pathFile))){
            document = new XWPFDocument(fis);

            table = document.getTables().get(firstTable);
            List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(" номер " + i);
                if (count != countRow) {
                    if (tro.equals(key)) {
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

                    while (!cell.getParagraphs().isEmpty()) {
                        cell.removeParagraph(0);
                    }

                    XWPFParagraph paragraph = cell.addParagraph();

                    paragraph.setFirstLineIndent(0);


                    XWPFRun run = paragraph.createRun();
                    run.setText(arr.get(i));
                    run.setFontSize(sizeFont);
                    count++;
                    rowCounter++;


                    if ((i + 1) % 10 == 0 || i == arr.size() - 1) {
                        docxUpdateTextService.searchTitle(CodeMapping.PSI_PATTERN_FIRST.getDescription(), lastname, CodeMapping.PSI_PATTERN_DATE.getDescription(), document, 12);

                        try (ByteArrayOutputStream fileInMemory = new ByteArrayOutputStream()) {
                            document.write(fileInMemory);
                            String uniqueFileName = "PSI" + System.currentTimeMillis() + ".docx";
                            String filePath =outputFile +"\\"+ uniqueFileName;
                          convertorService.convertFromStreamToPdf(fileInMemory, filePath);
                        }
                        document = new XWPFDocument(new FileInputStream(pathFile));
                        table = document.getTables().get(firstTable);
                        rowCounter = 0;
                        count = 0;
                    }


                }
            }


        }
    }



    public  void mergeDocx(String folder) {
        if (folder.isEmpty()) {
            throw new   RuntimeException("Введите путь к папке");
        }
        List<File> sortedFiles= arrayPathAbsoluteForDocx(folder);


        logger.info("Файлы для слияния (по дате изменения): {}", sortedFiles);

        String outputFilePath = folder.endsWith(File.separator)
                ? folder + "merged_output.docx"
                : folder + File.separator + "merged_output.docx";

        try (XWPFDocument mergedDoc = new XWPFDocument()) {

            for (File file : sortedFiles) {
                try (XWPFDocument srcDoc = new XWPFDocument(new FileInputStream(file))) {

                    // Копируем параграфы
                    for (XWPFParagraph srcPar : srcDoc.getParagraphs()) {
                        XWPFParagraph newPar = mergedDoc.createParagraph();
                        newPar.getCTP().set(srcPar.getCTP());
                    }

                    // Копируем таблицы
                    for (XWPFTable srcTable : srcDoc.getTables()) {
                        XWPFTable newTable = mergedDoc.createTable();
                        copyTable(mergedDoc, newTable, srcTable);
                    }
                }
            }

            try (FileOutputStream out = new FileOutputStream(outputFilePath)) {
                mergedDoc.write(out);
            }
        } catch (IOException e) {
            logger.error("Ошибка ", e);
            throw new RuntimeException(e);

        }
    }

    public   List<File> arrayPathAbsoluteForDocx(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<String> listFilesPdfAbsolute = new ArrayList<>();
        if (folder.isDirectory() && folder.exists()) {
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.getAbsolutePath().toLowerCase().endsWith(".docx")) {
                        listFilesPdfAbsolute.add(file.getAbsolutePath());
                    }
                }
            }
        }else {
            logger.info("Папки не существует");
            throw  new IllegalArgumentException("Папки не существует");
        }
        return listFilesPdfAbsolute.stream()
                .map(File::new)
                .sorted(Comparator.comparingLong(File::lastModified))
                .toList();
    }

    public static void copyTable(XWPFDocument doc, XWPFTable newTable, XWPFTable srcTable) {
        // Копируем свойства таблицы
        newTable.getCTTbl().setTblPr(srcTable.getCTTbl().getTblPr());

        // Копируем строки
        for (XWPFTableRow srcRow : srcTable.getRows()) {
            XWPFTableRow newTableRow = newTable.createRow();
            List<XWPFTableCell> newCells = newTableRow.getTableCells();

            // Удаляем стандартную пустую ячейку
            newCells.clear();

            // Копируем каждую ячейку
            for (XWPFTableCell srcCell : srcRow.getTableCells()) {
                XWPFTableCell newCell = newTableRow.addNewTableCell();
                newCell.getCTTc().set(srcCell.getCTTc()); // копируем всю структуру ячейки
            }
        }
    }
}
