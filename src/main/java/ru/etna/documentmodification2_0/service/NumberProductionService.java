package ru.etna.documentmodification2_0.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class NumberProductionService {
    private static final Logger log = LoggerFactory.getLogger(NumberProductionService.class);
    public  List<String> numberProductionFromExcelInArray(String excelFilePath, int sheetIndex, int columnIndex) {
        List<String> numbers = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            DataFormatter dataFormatter = new DataFormatter();
            for (Row row : sheet) {
                Cell cell = row.getCell(columnIndex);

                if (cell != null) {
                    String cellValue = dataFormatter.formatCellValue(cell);

                    if (!cellValue.isEmpty()) {
                        numbers.add(cellValue.trim());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Ошибка при чтении Excel: " +e.getMessage());
            e.printStackTrace();

        }

        return numbers;
    }
}
