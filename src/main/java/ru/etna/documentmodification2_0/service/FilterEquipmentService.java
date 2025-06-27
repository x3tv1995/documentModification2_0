package ru.etna.documentmodification2_0.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.equipment.EquipmentHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
public class FilterEquipmentService {

    private static final Map<String, List<String>> EQUIPMENNT = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilterEquipmentService.class);

    @Autowired
    private NumberProductionService numberProductionService;
    @Autowired
    private  Map<String, EquipmentHandler> handlers;

    static {
        EQUIPMENNT.put("БТР", new ArrayList<>(List.of("910", "660", "534", "839", "002", "765")));
        EQUIPMENNT.put("ТРО", new ArrayList<>(List.of("194", "186", "147", "242",
                "207", "186", "212", "215", "213", "202", "246", "210", "229", "214", "218")));
        EQUIPMENNT.put("ЭК", new ArrayList<>(List.of("166", "159", "161")));
        EQUIPMENNT.put("ЭК6500", new ArrayList<>(List.of("032", "030")));
        EQUIPMENNT.put("ОРТ", new ArrayList<>(List.of("162")));
        EQUIPMENNT.put("ВВЭК", new ArrayList<>(List.of("160", "165", "121", "158")));
        EQUIPMENNT.put("ВВЭК11400", new ArrayList<>(List.of("143")));
        EQUIPMENNT.put("БРПП22", new ArrayList<>(List.of("157")));
        EQUIPMENNT.put("БРПП60", new ArrayList<>(List.of("465")));
        EQUIPMENNT.put("БВПП-02", new ArrayList<>(List.of("754")));
        EQUIPMENNT.put("БТР28Д", new ArrayList<>(List.of("895")));
        EQUIPMENNT.put("УПП", new ArrayList<>(List.of("011")));
        EQUIPMENNT.put("УПП-18", new ArrayList<>(List.of("012")));
        EQUIPMENNT.put("ОКВТ_СОКТ", new ArrayList<>(List.of("248", "211", "219", "035", "168", "055","249")));
        EQUIPMENNT.put("МИТ", new ArrayList<>(List.of("025", "785", "786","024")));
        EQUIPMENNT.put("ТВКМ", new ArrayList<>(List.of("136")));


    }

    //Фильтрация по БНШИ
    public String filterBybnshi(String pathExcel) throws FileNotFoundException {
        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        String number = arr.get(0);

        if (number.isEmpty()) {
            throw new IllegalArgumentException("Первая колонка в таблице пустая, заполните только 1 колонку");
        }

        String nameKey = EQUIPMENNT.entrySet()
                .stream()
                .filter(s -> s.getValue().stream().anyMatch(o -> o.startsWith(number.substring(0, 3))))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        if (nameKey == null) {
            return "defaultKey";
        }
//                .orElseThrow(() -> new NoSuchElementException("Не найден ключ для номера, начинающегося с " +  number.substring(0, 3)));
//           logger.info("Имя ключа для поиска: "+nameKey);
        return nameKey;
    }

    //Фильтрация по имени
    public void filterByName(String key, String pathExcel, String docPath, String pathDirectory, String lastName, String data) {
        try {
            String defaultKey = "defaultKey";
            String numberInBold = CodeMapping.FOR_PATTERN_SEARCH_NUMBER.getDescription();
            if ( key.equals(defaultKey)) {
                EquipmentHandler  handlerDefault = handlers.get(key);
                handlerDefault.handler(pathExcel,docPath,pathDirectory,lastName,data,numberInBold);
            }else {
                EquipmentHandler handler = handlers.get(key);
                handler.handler(pathExcel, docPath, pathDirectory, lastName, data, numberInBold);
            }
        } catch (IOException e) {
            logger.error("Ошибка при выполнении enterDatabase: {}", e.getMessage());
            throw new RuntimeException("Ошибка при выполнении enterDatabase", e);
        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage());
            throw new RuntimeException("Неожиданная ошибка при выполнении enterDatabase", e);
        }

    }

}
