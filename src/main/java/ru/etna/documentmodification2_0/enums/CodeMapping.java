package ru.etna.documentmodification2_0.enums;

public enum CodeMapping {
    TRO_PATTERN_FIRST(" Представитель ОТК   _______________   ", 10),
    TRO_PATTERN_DATE("                    __________", 10),
    TRO_PATTERN_SPACEANDSIZEWORD("                     ", 10),
    TRO_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*",10),
    TRO_PATTERN_NUMBER(" № ",10),
    TRO_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}",10),


    BTR28D_PATTERN_FIRST(" Представитель ОТК   __________         ", 10),
    BTR28D_PATTERN_DATE("           __________", 10),
    BTR28D_PATTERN_SPACEANDSIZEWORD("            ", 10),
    BTR28D_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*",10),
    BTR28D_PATTERN_NUMBER(" № ",10),
    BTR28D_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}",10),




    BVPP_02_PATTERN_FIRST("Представитель ОТК   _______________   ", 10),
    BVPP_02_PATTERN_DATE("                     __________", 10),
    BVPP_02_SPACEANDSIZEWORD("                       ", 10),
    BVPP_02_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    BVPP_02_NUMBER("заводской № ",10),
    BVPP_02_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),


    MIT_PATTERN_FIRST("     Представитель ОТК _______________            ", 10),
    MIT_PATTERN_DATE("               ______", 10),
    MIT_SPACEANDSIZEWORD("           ", 10),
    MIT_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    MIT_NUMBER(" заводской № ",10),
    MIT_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),



    OKVT_PATTERN_FIRST("Представитель ОТК _____________         ", 10),
    OKVT_PATTERN_DATE("                          ____", 10),
    OKVT_SPACEANDSIZEWORD("                  ", 10),
    OKVT_NUMBER_SEARCH("№\\s*_+\\s*",10),
    OKVT_NUMBER("№ ",10),
    OKVT_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}",10),



    PB_PATTERN_FIRST("Представитель ОТК   _______________   ", 10),
    PB_PATTERN_DATE("                     __________", 10),
    PB_SPACEANDSIZEWORD("                                 ", 10),
    PB_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    PB_NUMBER("заводской № ",10),
    PB_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),

    YPP_PATTERN_FIRST("Представитель ОТК   _______________      ", 10),
    YPP_PATTERN_DATE("                      ______", 10),
    YPP_SPACEANDSIZEWORD("          ", 10),
    YPP_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    YPP_NUMBER("заводской № ",10),
    YPP_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),


    YPP_18_PATTERN_FIRST("Представитель ОТК   _______________  ", 10),
    YPP_18_PATTERN_DATE("                     ______", 10),
    YPP_18_SPACEANDSIZEWORD("            ", 10),
    YPP_18_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    YPP_18_NUMBER("заводской № ",10),
    YPP_18_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),



    ORT_PATTERN_FIRST(" Представитель ОТК   _______________             ", 10),
    ORT_PATTERN_DATE("                   _____", 10),
    ORT_PATTERN_SPACEANDSIZEWORD("                     ", 10),
    ORT_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    ORT_PATTERN_NUMBER("заводской № ",10),
    ORT_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),

    VVEK_PATTERN_FIRST(" Представитель ОТК   _______________             ", 10),
    VVEK_PATTERN_DATE("                  ____", 10),
    VVEK_PATTERN_SPACEANDSIZEWORD("               ", 10),
    VVEK_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    VVEK_PATTERN_NUMBER(" заводской № ",10),
    VVEK_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),


    VVEK11400_PATTERN_FIRST("         Представитель ОТК    _______________             ", 10),
    VVEK11400_PATTERN_DATE("                       ____", 10),
    VVEK11400_PATTERN_SPACEANDSIZEWORD("              ", 10),
    VVEK11400_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    VVEK11400_PATTERN_NUMBER(" заводской № ",10),
    VVEK11400_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),

    EK_PATTERN_FIRST(" Представитель ОТК   ________________                ", 10),
    EK_PATTERN_DATE("                      ______", 10),
    EK_PATTERN_SPACEANDSIZEWORD("                     ", 10),
    EK_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    EK_PATTERN_NUMBER(" заводской № ",10),
    EK_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),


    TVKM_PATTERN_FIRST(" Представитель ОТК   ________________          ", 10),
    TVKM_PATTERN_DATE("                    ________", 10),
    TVKM_PATTERN_SPACEANDSIZEWORD("                   ", 10),
    TVKM_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    TVKM_PATTERN_NUMBER(" заводской № ",10),
    TVKM_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),


    EK6500_PATTERN_FIRST(" Представитель ОТК   ________________              ", 10),
    EK6500_PATTERN_DATE("                      ______", 10),
    EK6500_PATTERN_SPACEANDSIZEWORD("                 ", 10),
    EK6500_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*",10),
    EK6500_PATTERN_NUMBER(" заводской № ",10),
    EK6500_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}",10),



    BTR_PATTERN_FIRST("Представитель ОТК ____________     ", 10),
    BTR_PATTERN_DATE("                 _________", 10),
    BTR_PATTERN_SPACEANDSIZEWORD("                 ", 10),
    BTR_PATTERN_NUMBER_SEARCH("заводской\\s*№\\s*___*",10),
    BTR_PATTERN_NUMBER("заводской № ",10),
    BTR_PATTERN_REPLACE_NUMBER("заводской\\s*№\\s*[A-Za-z0-9]{9}",10),


    BRPP22_PATTERN_FIRST("Представитель ОТК ____________     ", 12),
    BRPP22_PATTERN_DATE("                 _________", 12),
    BRPP22_PATTERN_SPACEANDSIZEWORD("                ", 12),
    BRPP22_PATTERN_NUMBER_SEARCH("заводской\\s*№\\s*___*",12),
    BRPP22_PATTERN_NUMBER("заводской № ",12),
    BRPP22_PATTERN_REPLACE_NUMBER("заводской\\s*№\\s*[A-Za-z0-9]{9}",12),

    BRPP60_PATTERN_FIRST("Представитель ОТК                             ", 12),
    BRPP60_PATTERN_DATE("                _______", 12),
    BRPP60_PATTERN_SPACEANDSIZEWORD("             ", 12),
    BRPP60_PATTERN_NUMBER_SEARCH("заводской\\s*№\\s*___*",12),
    BRPP60_PATTERN_NUMBER("заводской № ",12),
    BRPP60_PATTERN_REPLACE_NUMBER("заводской\\s*№\\s*[A-Za-z0-9]{9}",12);



    private final String description;
    private final int size;

    CodeMapping(String description, int size) {
        this.description = description;
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public int getSize() {
        return size;
    }
}
