package ru.etna.documentmodification2_0.service;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NumberProductionService {
    private static final Logger log = LoggerFactory.getLogger(NumberProductionService.class);

    public List<String> numberProductionFromExcelInArray(String excelFilePath, int sheetIndex, int columnIndex) throws FileNotFoundException {
        List<String> numbers = new ArrayList<>();
        File file = new File(excelFilePath);
        if (!file.exists()) {
            log.error("Файл не найден: ");
            throw new FileNotFoundException("Файл не найден: " + excelFilePath);
        }
        if (!excelFilePath.endsWith(".xlsx")) {
            log.error("Файл должен быть формата .xlsx: ");
            throw new IllegalArgumentException("Файл должен быть формата .xlsx: " + excelFilePath);
        }

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            if (!(workbook instanceof XSSFWorkbook)) {
                log.error("Неверный тип Excel-файла. Поддерживаются только .xlsx");
                throw new IllegalArgumentException("Неверный тип Excel-файла. Поддерживаются только .xlsx");
            }


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
            if (numbers.isEmpty()) {
                log.error("В Excel-файле нет данных в указанной колонке ");
                throw new IllegalArgumentException("В Excel-файле нет данных в указанной колонке");
            }
        } catch (InvalidFormatException e) {
            log.error("Ошибка "+ e);
            throw new IllegalArgumentException("Неверный формат Excel-файла: " + e.getMessage(), e);

        } catch (IOException e) {
            log.error("Ошибка "+ e);
            throw new IllegalArgumentException("Ошибка чтения Excel-файла", e);
        }

        return numbers;
    }
}
