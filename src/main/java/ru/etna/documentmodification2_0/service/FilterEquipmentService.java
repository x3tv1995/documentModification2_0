package ru.etna.documentmodification2_0.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.dto.EquipmentProcessingRequest;
import ru.etna.documentmodification2_0.enums.CodeMapping;
import ru.etna.documentmodification2_0.service.equipment.handlerImp.EquipmentHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Service
@RequiredArgsConstructor
public class FilterEquipmentService {

    private static final Map<String, List<String>> EQUIPMENNT = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FilterEquipmentService.class);


    private final NumberProductionService numberProductionService;
    private final Map<String, EquipmentHandler> handlers;


    static {
        EQUIPMENNT.put("БТР", new ArrayList<>(List.of("910")));
        EQUIPMENNT.put("ТРО", new ArrayList<>(List.of("194", "186", "147", "242",
                "207","205", "186", "212", "215", "213", "202", "246", "210", "229", "214", "218","247","208","224")));
        EQUIPMENNT.put("ЭК20000", new ArrayList<>(List.of( "161","159","166")));
        EQUIPMENNT.put("ЭК6500", new ArrayList<>(List.of("032", "030","033")));
        EQUIPMENNT.put("ОРТ", new ArrayList<>(List.of("162")));
        EQUIPMENNT.put("ВВЭК", new ArrayList<>(List.of("160", "165","158")));
        EQUIPMENNT.put("ВВЭК24000", new ArrayList<>(List.of( "121")));
        EQUIPMENNT.put("ВВЭК11400", new ArrayList<>(List.of("143")));
        EQUIPMENNT.put("БРПП22", new ArrayList<>(List.of("157","156")));
        EQUIPMENNT.put("БРПП60", new ArrayList<>(List.of("465")));
        EQUIPMENNT.put("БРПП150", new ArrayList<>(List.of("355","656")));
        EQUIPMENNT.put("БРПП800", new ArrayList<>(List.of("001")));
        EQUIPMENNT.put("БВПП-02", new ArrayList<>(List.of("754","610")));
        EQUIPMENNT.put("БТР28Д", new ArrayList<>(List.of("895")));
        EQUIPMENNT.put("БТР4ДЛ", new ArrayList<>(List.of("382","491","374","328")));
        EQUIPMENNT.put("БТР12Д", new ArrayList<>(List.of("393")));
        EQUIPMENNT.put("БТР14", new ArrayList<>(List.of("415","660","534","520","559","576","002")));
        EQUIPMENNT.put("БТР26", new ArrayList<>(List.of("765")));
        EQUIPMENNT.put("БТР27ГЛ", new ArrayList<>(List.of("839","895",  "002")));
        EQUIPMENNT.put("БУИ", new ArrayList<>(List.of("240")));
        EQUIPMENNT.put("УПП", new ArrayList<>(List.of("011")));
        EQUIPMENNT.put("УПП1", new ArrayList<>(List.of("010","015","026","016","023")));
        EQUIPMENNT.put("УПП7", new ArrayList<>(List.of("017","018","019","034","036","030","037","031","032")));
        EQUIPMENNT.put("УПП-18", new ArrayList<>(List.of("012","013")));
        EQUIPMENNT.put("ОКВТ", new ArrayList<>(List.of("248", "211", "219",  "168","253","216","190","189",
                "203","148","151","154","182","184","220","151","153","183","181","209","289","200","187","254","047","057","040")));
        EQUIPMENNT.put("ОКВА", new ArrayList<>(List.of("252","249")));
        EQUIPMENNT.put("СОКТ",new ArrayList<>(List.of( "035", "054", "055","049")));
        EQUIPMENNT.put("МИТ", new ArrayList<>(List.of("025", "785", "786","042")));
        EQUIPMENNT.put("ПУ_СМК",new ArrayList<>(List.of("024","022")));
        EQUIPMENNT.put("КЭКМ", new ArrayList<>(List.of("295")));
        EQUIPMENNT.put("ТВКМ", new ArrayList<>(List.of("136")));
        EQUIPMENNT.put("НСВ", new ArrayList<>(List.of("001","004","296","180")));
        EQUIPMENNT.put("ТВКМ5000", new ArrayList<>(List.of("048","149","068")));
        EQUIPMENNT.put("СБ", new ArrayList<>(List.of("307","081")));
        EQUIPMENNT.put("НПЭК", new ArrayList<>(List.of("126")));
        EQUIPMENNT.put("НР4", new ArrayList<>(List.of("375")));
        EQUIPMENNT.put("НР6", new ArrayList<>(List.of("387")));
        EQUIPMENNT.put("ПБ15", new ArrayList<>(List.of("007","009")));
        EQUIPMENNT.put("РПП", new ArrayList<>(List.of("008","013","006")));



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
            String numberInBold = CodeMapping.FOR_PATTERN_SEARCH_NUMBER.getDescription();

               EquipmentHandler handler = handlers.get(key);
            if (handler == null) {
                handler = handlers.get("defaultKey");
            }
               logger.info(" В МЕТОДЕ filterbyName "+ handler);
            EquipmentProcessingRequest equipmentProcessingRequest = new EquipmentProcessingRequest(pathExcel, docPath, pathDirectory, lastName, data, numberInBold,key);
                   handler.handler(equipmentProcessingRequest);

        } catch (IOException e) {
            logger.error("Ошибка при выполнении enterDatabase: {}", e.getMessage());
            throw new RuntimeException("Ошибка при выполнении enterDatabase", e);
        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage());
            throw new RuntimeException("Неожиданная ошибка при выполнении enterDatabase", e);
        }

    }

}
