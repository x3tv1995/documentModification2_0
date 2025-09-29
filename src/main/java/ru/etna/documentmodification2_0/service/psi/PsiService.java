package ru.etna.documentmodification2_0.service.psi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.service.*;


import java.io.*;


/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
@RequiredArgsConstructor
public class PsiService {

    private final FilterEquipmentService filterEquipmentService;
    private final NumberProductionService numberProductionService;
    private final PsiBatchProcessor batchProcessor;


    public void fillingPsi(String pathFile, String outputFile, String pathExcel, String lastname) throws IOException {
        if (pathFile.isEmpty()) {
            throw new IllegalArgumentException("Ошибка: файл шаблона пуст");
        }

        String key = filterEquipmentService.filterBybnshi(pathExcel);
        boolean isVvekEk = PsiConstants.EK_VVEK.contains(key);

        if (isVvekEk) {
            batchProcessor.processVvekEk(key,
                    pathFile,
                    outputFile,
                    pathExcel,
                    lastname,
                    numberProductionService);
        } else {
            batchProcessor.processStandard(key,
                    pathFile,
                    outputFile,
                    pathExcel,
                    lastname,
                    numberProductionService);
        }
    }
}

