package ru.etna.documentmodification2_0.service.psi;

import java.util.List;

public class PsiConstants {
    private PsiConstants() {}

    public static final String TRO_KEY = "ТРО";
    public static final String SOKT_KEY = "СОКТ";
    public static final String OKVT_KEY = "ОКВТ";
    public static final String YPP1_KEY = "УПП1";
    public static final String YPP7_KEY = "УПП7";
    public static final String YPP_KEY = "УПП";
    public static final String NPEK_KEY = "НПЭК";
    public static final String OKVA_KEY = "ОКВА";
    public static final String VVEK11400_KEY = "ВВЭК11400";
    public static final String VVEK_KEY = "ВВЭК";
    public static final String VVEK6500_KEY = "ЭК6500";

    public static final List<String> EK_VVEK = List.of("ЭК20000", "ЭК6500", "ВВЭК", "ВВЭК11400", "ВВЭК24000");

    public static final int MAX_ROWS = 10;
    public static final int FIRST_TABLE_INDEX = 0;
    public static final int DEFAULT_FONT_SIZE = 10;
    public static final int VVEK_11400_FONT_SIZE = 8;
    public static final int DEFAULT_SIZE_TEXT = 12;
    public static final int DATA_COLUMN_INDEX = 1;

    public static final String VVEK_11400_VERSIA_01 = "14301";
    public static final String VVEK_11400_VERSIA = "14300";
}
