package ru.etna.documentmodification2_0.service;

import org.jodconverter.core.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.enums.CodeMapping;

import java.io.FileNotFoundException;
import java.util.*;
@Service
public class FilterEquipmentService {
    private static Map<String, List<String>> EQUIPMENNT = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(FilterEquipmentService.class);
@Autowired
private StartupManagerService startupManagerService;

@Autowired
private  NumberProductionService numberProductionService;

    static  {
        EQUIPMENNT.put("БТР", new ArrayList<>(List.of("910", "660", "534", "839", "002","765")));
        EQUIPMENNT.put("ТРО", new ArrayList<>(List.of("194", "186", "147","242",
                "207","186","212","215","213","202","246","210","229","214","218")));
        EQUIPMENNT.put("ЭК", new ArrayList<>(List.of("166","159","161")));
        EQUIPMENNT.put("ЭК6500", new ArrayList<>(List.of("032","030")));
        EQUIPMENNT.put("ОРТ", new ArrayList<>(List.of("162")));
        EQUIPMENNT.put("ВВЭК", new ArrayList<>(List.of("160","165","121","158")));
        EQUIPMENNT.put("ВВЭК11400", new ArrayList<>(List.of("143")));
        EQUIPMENNT.put("БРПП22", new ArrayList<>(List.of("157")));
        EQUIPMENNT.put("БРПП60", new ArrayList<>(List.of("465")));
        EQUIPMENNT.put("БВПП-02", new ArrayList<>(List.of("754")));
        EQUIPMENNT.put("БТР28Д", new ArrayList<>(List.of("895")));
        EQUIPMENNT.put("УПП", new ArrayList<>(List.of("011")));
        EQUIPMENNT.put("УПП-18", new ArrayList<>(List.of("012")));
        EQUIPMENNT.put("ОКВТ_СОКТ", new ArrayList<>(List.of("248","211","219","035","168","055")));
        EQUIPMENNT.put("МИТ", new ArrayList<>(List.of("025","785","786")));
        EQUIPMENNT.put("ТВКМ",new ArrayList<>(List.of("136")));


    }

    //Фильтрация по БНШИ
    public  String filterBybnshi(String pathExcel) throws FileNotFoundException {
        List<String> arr = numberProductionService.numberProductionFromExcelInArray(pathExcel, 0, 0);
        String number = arr.get(0);
        String nameKey = EQUIPMENNT.entrySet()
                .stream()
                .filter(s -> s.getValue().stream().anyMatch(o -> o.startsWith(number.substring(0, 3))))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Не найден ключ для номера, начинающегося с " +  number.substring(0, 3)));
           logger.info("Имя ключа для поиска: "+nameKey);
        return nameKey;
    }
//Фильтрация по имени
    public  void filterByName(String key,String pathExcel,String docPath, String pathDirectory,String lastName,String data) {
        switch (key) {
            case "БТР":
                String patternFirstBTR = CodeMapping.BTR_PATTERN_FIRST.getDescription();
                String patterDateBTR = CodeMapping.BTR_PATTERN_DATE.getDescription();
                String patternSpaceBTR = CodeMapping.BTR_PATTERN_SPACEANDSIZEWORD.getDescription();
                String numberSearchBTR = CodeMapping.BTR_PATTERN_NUMBER_SEARCH.getDescription();
                String patternNumberBTR = CodeMapping.BTR_PATTERN_NUMBER.getDescription();
                String replaceNumberBTR = CodeMapping.BTR_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextBTR = CodeMapping.BTR_PATTERN_SPACEANDSIZEWORD.getSize();

                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstBTR,patterDateBTR,sizeTextBTR,
                            patternSpaceBTR,numberSearchBTR,patternNumberBTR,replaceNumberBTR);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "ТВКМ":
                String patternFirstTVKM = CodeMapping.TVKM_PATTERN_FIRST.getDescription();
                String patterDateTVKM = CodeMapping.TVKM_PATTERN_DATE.getDescription();
                String patternSpaceTVKM = CodeMapping.TVKM_PATTERN_SPACEANDSIZEWORD.getDescription();
                String numberSearchTVKM = CodeMapping.TVKM_PATTERN_NUMBER_SEARCH.getDescription();
                String patternNumberTVKM = CodeMapping.TVKM_PATTERN_NUMBER.getDescription();
                String replaceNumberTVKM = CodeMapping.TVKM_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextTVKM = CodeMapping.TVKM_PATTERN_SPACEANDSIZEWORD.getSize();

                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstTVKM,patterDateTVKM,sizeTextTVKM,patternSpaceTVKM,
                            numberSearchTVKM,patternNumberTVKM,replaceNumberTVKM);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ТРО":
                String patternFirstTRO = CodeMapping.TRO_PATTERN_FIRST.getDescription();
                String patterDateTRO = CodeMapping.TRO_PATTERN_DATE.getDescription();
                String patternSpaceTRO = CodeMapping.TRO_PATTERN_SPACEANDSIZEWORD.getDescription();
                String numberSearchTRO = CodeMapping.TRO_PATTERN_NUMBER_SEARCH.getDescription();
                String patternNumberTRO = CodeMapping.TRO_PATTERN_NUMBER.getDescription();
                String replaceNumberTRO = CodeMapping.TRO_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextTRO = CodeMapping.TRO_PATTERN_SPACEANDSIZEWORD.getSize();

                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstTRO,patterDateTRO,sizeTextTRO,patternSpaceTRO,
                            numberSearchTRO,patternNumberTRO,replaceNumberTRO);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ОКВТ_СОКТ":
                String patternFirstOKVT = CodeMapping.OKVT_PATTERN_FIRST.getDescription();
                String patterDateOKVT = CodeMapping.OKVT_PATTERN_DATE.getDescription();
                String patternSpaceOKVT = CodeMapping.OKVT_SPACEANDSIZEWORD.getDescription();
                String numberSearchOKVT= CodeMapping.OKVT_NUMBER_SEARCH.getDescription();
                String patternNumberOKVT = CodeMapping.OKVT_NUMBER.getDescription();
                String replaceNumberOKVT = CodeMapping.OKVT_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextOKVT = CodeMapping.TRO_PATTERN_SPACEANDSIZEWORD.getSize();

                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstOKVT,patterDateOKVT,sizeTextOKVT,patternSpaceOKVT,
                            numberSearchOKVT,patternNumberOKVT,replaceNumberOKVT);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ЭК":
                String patternFirstEK =  CodeMapping.EK_PATTERN_FIRST.getDescription();
                String patterDateEK   =  CodeMapping.EK_PATTERN_DATE.getDescription();
                String patternSpaceEK  = CodeMapping.EK_PATTERN_SPACEANDSIZEWORD.getDescription();
                String patternNumberEK  = CodeMapping.EK_PATTERN_NUMBER.getDescription();
                String numberSearchEK  = CodeMapping.EK_PATTERN_NUMBER_SEARCH.getDescription();
                String replaceNumberEK  = CodeMapping.EK_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextEK = CodeMapping.EK_PATTERN_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstEK,patterDateEK,
                            sizeTextEK,patternSpaceEK,numberSearchEK,
                            patternNumberEK,replaceNumberEK);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ЭК6500":
                String patternFirstEK6500 =  CodeMapping.EK6500_PATTERN_FIRST.getDescription();
                String patterDateEK6500   =  CodeMapping.EK6500_PATTERN_DATE.getDescription();
                String patternSpaceEK6500  = CodeMapping.EK6500_PATTERN_SPACEANDSIZEWORD.getDescription();
                String patternNumberEK6500  = CodeMapping.EK6500_PATTERN_NUMBER.getDescription();
                String numberSearchEK6500  = CodeMapping.EK6500_PATTERN_NUMBER_SEARCH.getDescription();
                String replaceNumberEK6500  = CodeMapping.EK6500_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextEK6500 = CodeMapping.EK6500_PATTERN_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstEK6500,patterDateEK6500,
                            sizeTextEK6500,patternSpaceEK6500,numberSearchEK6500,
                            patternNumberEK6500,replaceNumberEK6500);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ОРТ":
                String patternFirstORT = CodeMapping.ORT_PATTERN_FIRST.getDescription();
                String patterDateORT = CodeMapping.ORT_PATTERN_DATE.getDescription();
                String patternSpaceORT = CodeMapping.ORT_PATTERN_SPACEANDSIZEWORD.getDescription();
                String numberSearchORT = CodeMapping.ORT_PATTERN_NUMBER_SEARCH.getDescription();
                String patternNumberORT = CodeMapping.ORT_PATTERN_NUMBER.getDescription();
                String replaceNumberORT = CodeMapping.ORT_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextORT = CodeMapping.ORT_PATTERN_SPACEANDSIZEWORD.getSize();

                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstORT,patterDateORT,sizeTextORT,patternSpaceORT,
                            numberSearchORT,patternNumberORT,replaceNumberORT);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "ВВЭК":
                String patternFirstVVEK =  CodeMapping.VVEK_PATTERN_FIRST.getDescription();
                String patterDateVVEK   =  CodeMapping.VVEK_PATTERN_DATE.getDescription();
                String patternSpaceVVEK  = CodeMapping.VVEK_PATTERN_SPACEANDSIZEWORD.getDescription();
                String patternNumberVVEK  = CodeMapping.VVEK_PATTERN_NUMBER.getDescription();
                String numberSearchVVEK  = CodeMapping.VVEK_PATTERN_NUMBER_SEARCH.getDescription();
                String replaceNumberVVEK  = CodeMapping.VVEK_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextVVEK = CodeMapping.VVEK_PATTERN_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstVVEK,patterDateVVEK,
                            sizeTextVVEK,patternSpaceVVEK,numberSearchVVEK,
                            patternNumberVVEK,replaceNumberVVEK);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "ВВЭК11400":
                String patternFirstVVEK11400 =  CodeMapping.VVEK11400_PATTERN_FIRST.getDescription();
                String patterDateVVEK11400   =  CodeMapping.VVEK11400_PATTERN_DATE.getDescription();
                String patternSpaceVVEK11400  = CodeMapping.VVEK11400_PATTERN_SPACEANDSIZEWORD.getDescription();
                String patternNumberVVEK11400  = CodeMapping.VVEK11400_PATTERN_NUMBER.getDescription();
                String numberSearchVVEK11400  = CodeMapping.VVEK11400_PATTERN_NUMBER_SEARCH.getDescription();
                String replaceNumberVVEK11400  = CodeMapping.VVEK11400_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextVVEK11400 = CodeMapping.VVEK11400_PATTERN_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstVVEK11400,patterDateVVEK11400,
                            sizeTextVVEK11400,patternSpaceVVEK11400,numberSearchVVEK11400,
                            patternNumberVVEK11400,replaceNumberVVEK11400);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "БРПП22":
                String patternFirstBRPP22 =  CodeMapping.BRPP22_PATTERN_FIRST.getDescription();
                String patterDateBRPP22 =  CodeMapping.BRPP22_PATTERN_DATE.getDescription();
                String patternSpaceBRPP22 = CodeMapping.BRPP22_PATTERN_SPACEANDSIZEWORD.getDescription();
                String patternNumberBRPP22 = CodeMapping.BRPP22_PATTERN_NUMBER.getDescription();
                String numberSearchBRPP22 = CodeMapping.BRPP22_PATTERN_NUMBER_SEARCH.getDescription();
                String replaceNumberBRPP22 = CodeMapping.BRPP22_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextBRPP22 = CodeMapping.BRPP22_PATTERN_SPACEANDSIZEWORD.getSize();
                try {
                   startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstBRPP22,patterDateBRPP22,
                            sizeTextBRPP22,patternSpaceBRPP22,numberSearchBRPP22,
                            patternNumberBRPP22,replaceNumberBRPP22);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "БРПП60":
                String patternFirstBRPP60 =  CodeMapping.BRPP60_PATTERN_FIRST.getDescription();
                String patterDateBRPP60 =  CodeMapping.BRPP60_PATTERN_DATE.getDescription();
                String patternSpaceBRPP60 = CodeMapping.BRPP60_PATTERN_SPACEANDSIZEWORD.getDescription();
                String patternNumberBRPP60 = CodeMapping.BRPP60_PATTERN_NUMBER.getDescription();
                String numberSearchBRPP60 = CodeMapping.BRPP60_PATTERN_NUMBER_SEARCH.getDescription();
                String replaceNumberBRPP60 = CodeMapping.BRPP60_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextBRPP60 = CodeMapping.BRPP60_PATTERN_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstBRPP60,patterDateBRPP60,
                            sizeTextBRPP60,patternSpaceBRPP60,numberSearchBRPP60,
                            patternNumberBRPP60,replaceNumberBRPP60);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "БВПП-02":
                String patternFirstBVPP_02 =  CodeMapping.BVPP_02_PATTERN_FIRST.getDescription();
                String patterDateBVPP_02 =  CodeMapping.BVPP_02_PATTERN_DATE.getDescription();
                String patternSpaceBVPP_02 = CodeMapping.BVPP_02_SPACEANDSIZEWORD.getDescription();
                String patternNumberBVPP_02 = CodeMapping.BVPP_02_NUMBER.getDescription();
                String numberSearchBVPP_02 = CodeMapping.BVPP_02_NUMBER_SEARCH.getDescription();
                String replaceNumberBVPP_02= CodeMapping.BVPP_02_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextBVPP_02 = CodeMapping.BVPP_02_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstBVPP_02,patterDateBVPP_02,
                            sizeTextBVPP_02,patternSpaceBVPP_02,numberSearchBVPP_02,
                            patternNumberBVPP_02,replaceNumberBVPP_02);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "МИТ":
                String patternFirstMIT =  CodeMapping.MIT_PATTERN_FIRST.getDescription();
                String patterDateMIT =  CodeMapping.MIT_PATTERN_DATE.getDescription();
                String patternSpaceMIT = CodeMapping.MIT_SPACEANDSIZEWORD.getDescription();
                String patternNumberMIT = CodeMapping.MIT_NUMBER.getDescription();
                String numberSearchMIT = CodeMapping.MIT_NUMBER_SEARCH.getDescription();
                String replaceNumberMIT= CodeMapping.MIT_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextMIT = CodeMapping.MIT_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstMIT,patterDateMIT,
                            sizeTextMIT,patternSpaceMIT,numberSearchMIT,
                            patternNumberMIT,replaceNumberMIT);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "БТР28Д":
                String patternFirstBTR28D = CodeMapping.BTR28D_PATTERN_FIRST.getDescription();
                String patterDateBTR28D = CodeMapping.BTR28D_PATTERN_DATE.getDescription();
                String patternSpaceBTR28D = CodeMapping.BTR28D_PATTERN_SPACEANDSIZEWORD.getDescription();
                String numberSearchBTR28D = CodeMapping.BTR28D_PATTERN_NUMBER_SEARCH.getDescription();
                String patternNumberBTR28D = CodeMapping.BTR28D_PATTERN_NUMBER.getDescription();
                String replaceNumberBTR28D = CodeMapping.BTR28D_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextBTR28D = CodeMapping.BTR28D_PATTERN_SPACEANDSIZEWORD.getSize();

                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstBTR28D,patterDateBTR28D,sizeTextBTR28D,patternSpaceBTR28D,
                            numberSearchBTR28D,patternNumberBTR28D,replaceNumberBTR28D);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "УПП":
                String patternFirstYPP =  CodeMapping.YPP_PATTERN_FIRST.getDescription();
                String patterDateYPP  =  CodeMapping.YPP_PATTERN_DATE.getDescription();
                String patternSpaceYPP  = CodeMapping.YPP_SPACEANDSIZEWORD.getDescription();
                String patternNumberYPP  = CodeMapping.YPP_NUMBER.getDescription();
                String numberSearchYPP  = CodeMapping.YPP_NUMBER_SEARCH.getDescription();
                String replaceNumberYPP = CodeMapping.YPP_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextYPP  = CodeMapping.YPP_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstYPP,patterDateYPP,
                            sizeTextYPP,patternSpaceYPP,numberSearchYPP,
                            patternNumberYPP,replaceNumberYPP);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "УПП-18":
                String patternFirstYPP18 =  CodeMapping.YPP_18_PATTERN_FIRST.getDescription();
                String patterDateYPP18  =  CodeMapping.YPP_18_PATTERN_DATE.getDescription();
                String patternSpaceYPP18  = CodeMapping.YPP_18_SPACEANDSIZEWORD.getDescription();
                String patternNumberYPP18  = CodeMapping.YPP_18_NUMBER.getDescription();
                String numberSearchYPP18  = CodeMapping.YPP_18_NUMBER_SEARCH.getDescription();
                String replaceNumberYPP18 = CodeMapping.YPP_18_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextYPP18  = CodeMapping.YPP_18_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            lastName,data,patternFirstYPP18,patterDateYPP18,
                            sizeTextYPP18,patternSpaceYPP18,numberSearchYPP18,
                            patternNumberYPP18,replaceNumberYPP18);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                String patternNumberDefault  = CodeMapping.YPP_NUMBER.getDescription();
                String numberSearchDefault  = CodeMapping.YPP_NUMBER_SEARCH.getDescription();
                String replaceNumberDefault = CodeMapping.YPP_PATTERN_REPLACE_NUMBER.getDescription();
                int sizeTextDefault  = CodeMapping.YPP_SPACEANDSIZEWORD.getSize();
                try {
                    startupManagerService.enterDatabase(pathExcel,docPath,pathDirectory,
                            sizeTextDefault,numberSearchDefault,patternNumberDefault,replaceNumberDefault);
                } catch (OfficeException |FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

    }
}
