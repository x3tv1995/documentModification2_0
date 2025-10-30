package ru.etna.documentmodification2_0.enums;

import lombok.Getter;

@Getter
public enum CodeMapping {
    TRO_PATTERN_FIRST(" Представитель ОТК   _______________   ", 10),
    TRO_PATTERN_DATE("                    __________", 10),
    TRO_PATTERN_SPACEANDSIZEWORD("                     ", 10),
    TRO_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    TRO_PATTERN_NUMBER(" № ", 10),
    TRO_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),


    FOR_PATTERN_SEARCH_NUMBER("\\s*\\s*[A-Za-z0-9]{9}", 10),


    BTR28D_PATTERN_FIRST(" Представитель ОТК   __________         ", 10),
    BTR28D_PATTERN_DATE("           __________", 10),
    BTR28D_PATTERN_SPACEANDSIZEWORD("            ", 10),
    BTR28D_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    BTR28D_PATTERN_NUMBER(" № ", 10),
    BTR28D_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),

    BTR4DL_PATTERN_FIRST(" Представитель ОТК   __________         ", 10),
    BTR4DL_PATTERN_DATE("              ______", 10),
    BTR4DL_PATTERN_SPACEANDSIZEWORD("            ", 10),
    BTR4DL_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    BTR4DL_PATTERN_NUMBER(" № ", 10),
    BTR4DL_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),

    BTR12D_PATTERN_FIRST(" Представитель ОТК   __________     ", 10),
    BTR12D_PATTERN_DATE("          ______", 10),
    BTR12D_PATTERN_SPACEANDSIZEWORD("            ", 10),
    BTR12D_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    BTR12D_PATTERN_NUMBER(" № ", 10),
    BTR12D_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),


    BTR14_PATTERN_FIRST(" Представитель ОТК   __________        ", 10),
    BTR14_PATTERN_DATE("                 _______", 10),
    BTR14_PATTERN_SPACEANDSIZEWORD("             ", 10),
    BTR14_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    BTR14_PATTERN_NUMBER(" № ", 10),
    BTR14_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),

    BTR26_PATTERN_FIRST(" Представитель ОТК   __________     ", 10),
    BTR26_PATTERN_DATE("             _______", 10),
    BTR26_PATTERN_SPACEANDSIZEWORD("             ", 10),
    BTR26_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    BTR26_PATTERN_NUMBER(" № ", 10),
    BTR26_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),

    BTR27GL_PATTERN_FIRST(" Представитель ОТК   __________        ", 10),
    BTR27GL_PATTERN_DATE("               _______", 10),
    BTR27GL_PATTERN_SPACEANDSIZEWORD("             ", 10),
    BTR27GL_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    BTR27GL_PATTERN_NUMBER(" № ", 10),
    BTR27GL_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),


    BYI_PATTERN_FIRST(" Представитель ОТК   __________                ", 10),
    BYI_PATTERN_DATE("                    _______", 10),
    BYI_PATTERN_SPACEANDSIZEWORD("                ", 10),
    BYI_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    BYI_PATTERN_NUMBER(" заводской № ", 10),
    BYI_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    BVPP_02_PATTERN_FIRST("Представитель ОТК   _______________   ", 10),
    BVPP_02_PATTERN_DATE("                     __________", 10),
    BVPP_02_PATTERN_SPACEANDSIZEWORD("                       ", 10),
    BVPP_02_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    BVPP_02_PATTERN_NUMBER(" № ", 10),
    BVPP_02_PATTERN_REPLACE_NUMBER("\\s*\\s*№\\s*[A-Za-z0-9]{9}\\s*", 10),


    MIT_PATTERN_FIRST("     Представитель ОТК _______________            ", 10),
    MIT_PATTERN_DATE("                  _______", 10),
    MIT_PATTERN_SPACEANDSIZEWORD("            ", 10),
    MIT_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    MIT_PATTERN_NUMBER(" заводской № ", 10),
    MIT_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    PYSMK_PATTERN_FIRST("     Представитель ОТК ____________          ", 10),
    PYSMK_PATTERN_DATE("                  _______", 10),
    PYSMK_PATTERN_SPACEANDSIZEWORD("               ", 10),
    PYSMK_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    PYSMK_PATTERN_NUMBER(" заводской № ", 10),
    PYSMK_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    KEKM_PATTERN_FIRST("     Представитель ОТК _______________             ", 10),
    KEKM_PATTERN_DATE("                       _______", 10),
    KEKM_PATTERN_SPACEANDSIZEWORD("                 ", 10),
    KEKM_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    KEKM_PATTERN_NUMBER(" заводской № ", 10),
    KEKM_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),


    NSV_PATTERN_FIRST("Представитель ОТК _____________           ", 10),
    NSV_PATTERN_DATE("                       ______", 10),
    NSV_PATTERN_SPACEANDSIZEWORD("                   ", 10),
    NSV_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    NSV_PATTERN_NUMBER(" заводской № ", 10),
    NSV_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    TVKM5000_PATTERN_FIRST("Представитель ОТК ____________                ", 10),
    TVKM5000_PATTERN_DATE("                 ______", 10),
    TVKM5000_PATTERN_SPACEANDSIZEWORD("               ", 10),
    TVKM5000_PATTERN_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    TVKM5000_PATTERN_NUMBER(" № ", 10),
    TVKM5000_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),

    NPEK_PATTERN_FIRST("Представитель ОТК _____________              ", 10),
    NPEK_PATTERN_DATE("                    ______", 10),
    NPEK_PATTERN_SPACEANDSIZEWORD("                ", 10),
    NPEK_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    NPEK_PATTERN_NUMBER(" заводской № ", 10),
    NPEK_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    NR4_PATTERN_FIRST("Представитель ОТК _____________        ", 10),
    NR4_PATTERN_DATE("           ______", 10),
    NR4_PATTERN_SPACEANDSIZEWORD("       ", 10),
    NR4_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    NR4_PATTERN_NUMBER(" заводской № ", 10),
    NR4_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    PB15_PATTERN_FIRST("Представитель ОТК _________________         ", 10),
    PB15_PATTERN_DATE("                  ______", 10),
    PB15_PATTERN_SPACEANDSIZEWORD("              ", 10),
    PB15_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    PB15_PATTERN_NUMBER(" заводской № ", 10),
    PB15_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    NR6_PATTERN_FIRST("Представитель ОТК _____________                 ", 10),
    NR6_PATTERN_DATE("           ______", 10),
    NR6_PATTERN_SPACEANDSIZEWORD("       ", 10),
    NR6_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    NR6_PATTERN_NUMBER(" заводской № ", 10),
    NR6_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    OKVT_PATTERN_FIRST("Представитель ОТК _____________         ", 10),
    OKVT_PATTERN_DATE("                         ______", 10),
    OKVT_SPACEANDSIZEWORD("                     ", 10),
    OKVT_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    OKVT_NUMBER(" № ", 10),
    OKVT_PATTERN_REPLACE_NUMBER("№\\s*[A-Za-z0-9]{9}", 10),

    OKVA_PATTERN_FIRST("Представитель ОТК _____________         ", 10),
    OKVA_PATTERN_DATE("                      ______", 10),
    OKVA_SPACEANDSIZEWORD("                     ", 10),
    OKVA_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    OKVA_NUMBER(" № ", 10),
    OKVA_PATTERN_REPLACE_NUMBER("№\\s*[A-Za-z0-9]{9}", 10),

    SOKT_PATTERN_FIRST("Представитель ОТК ______________       ", 10),
    SOKT_PATTERN_DATE("                           ______", 10),
    SOKT_SPACEANDSIZEWORD("                       ", 10),
    SOKT_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    SOKT_NUMBER("№ ", 10),
    SOKT_PATTERN_REPLACE_NUMBER("№\\s*[A-Za-z0-9]{9}", 10),

    SB_PATTERN_FIRST("Представитель ОТК _____________      ", 10),
    SB_PATTERN_DATE("             ______", 10),
    SB_SPACEANDSIZEWORD("          ", 10),
    SB_NUMBER_SEARCH("№\\s*_+\\s*", 10),
    SB_NUMBER("№ ", 10),
    SB_PATTERN_REPLACE_NUMBER("№\\s*[A-Za-z0-9]{9}", 10),


    PB_PATTERN_FIRST("Представитель ОТК   _______________   ", 10),
    PB_PATTERN_DATE("                     __________", 10),
    PB_PATTERN_SPACEANDSIZEWORD("                                 ", 10),
    PB_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    PB_PATTERN_NUMBER("заводской № ", 10),
    PB_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    YPP_PATTERN_FIRST("Представитель ОТК   __________________      ", 10),
    YPP_PATTERN_DATE("                     _______", 10),
    YPP_PATTERN_SPACEANDSIZEWORD("                  ", 10),
    YPP_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    YPP_PATTERN_NUMBER(" заводской № ", 10),
    YPP_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    YPP7_PATTERN_FIRST("Представитель ОТК   _____________       ", 10),
    YPP7_PATTERN_DATE("                      _______", 10),
    YPP7_PATTERN_SPACEANDSIZEWORD("                   ", 10),
    YPP7_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    YPP7_PATTERN_NUMBER(" заводской № ", 10),
    YPP7_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    YPP1_PATTERN_FIRST("Представитель ОТК   __________________      ", 10),
    YPP1_PATTERN_DATE("                   _______", 10),
    YPP1_PATTERN_SPACEANDSIZEWORD("                ", 10),
    YPP1_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    YPP1_PATTERN_NUMBER(" заводской № ", 10),
    YPP1_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    YPP1_PSI_PATTERN_FIRST("Представитель ОТК   __________________                       ", 10),
    YPP1_PSI_PATTERN_DATE("                               _________", 10),
    YPP1_PSI_SPACEANDSIZEWORD("          ", 10),

    BRPP22_PSI_PATTERN_FIRST("Представитель ОТК   __________________       ", 10),
    BRPP22_PSI_PATTERN_DATE("                 _______", 10),
    BRPP22_PSI_SPACEANDSIZEWORD("          ", 10),

    BRPP60_PSI_PATTERN_FIRST("Представитель ОТК   _____________________                              ", 10),
    BRPP60_PSI_PATTERN_DATE("                      ____________", 10),
    BRPP60_PSI_SPACEANDSIZEWORD("          ", 10),

    BRPP150_PSI_PATTERN_FIRST("Представитель ОТК   _______________        ", 10),
    BRPP150_PSI_PATTERN_DATE("           ____________", 10),
    BRPP150_PSI_SPACEANDSIZEWORD("          ", 10),

    TRO_PSI_PATTERN_FIRST("Представитель ОТК   __________________         ", 10),
    TRO_PSI_PATTERN_DATE("                               _________", 10),
    TRO_PSI_SPACEANDSIZEWORD("          ", 10),

    TVKM5000_PSI_PATTERN_FIRST("Представитель ОТК   __________________                    ", 10),
    TVKM5000_PSI_PATTERN_DATE("                          _________", 10),
    TVKM5000_PSI_SPACEANDSIZEWORD("          ", 10),

    RPP_PSI_PATTERN_FIRST("Представитель ОТК   __________________         ", 10),
    RPP_PSI_PATTERN_DATE("                _________", 10),
    RPP_PSI_SPACEANDSIZEWORD("          ", 10),

    NPEK_PSI_PATTERN_FIRST("Представитель ОТК   __________________                   ", 10),
    NPEK_PSI_PATTERN_DATE("                     _________", 10),
    NPEK_PSI_SPACEANDSIZEWORD("          ", 10),

    BTR4DL_PSI_PATTERN_FIRST("Представитель ОТК   __________________                  ", 10),
    BTR4DL_PSI_PATTERN_DATE("                       _________", 10),
    BTR4DL_PSI_SPACEANDSIZEWORD("          ", 10),

    TVKM_PSI_PATTERN_FIRST("Представитель ОТК   __________________                     ", 10),
    TVKM_PSI_PATTERN_DATE("                    _______", 10),
    TVKM_PSI_SPACEANDSIZEWORD("          ", 10),

    OKVT_PSI_PATTERN_FIRST("Представитель ОТК   __________________           ", 10),
    OKVT_PSI_PATTERN_DATE("                               ________", 10),
    OKVT_PSI_SPACEANDSIZEWORD("          ", 10),

    SOKT_PSI_PATTERN_FIRST("Представитель ОТК   __________________           ", 10),
    SOKT_PSI_PATTERN_DATE("                               ________", 10),
    SOKT_PSI_SPACEANDSIZEWORD("          ", 10),

    SB_PSI_PATTERN_FIRST("Представитель ОТК   __________________                 ", 10),
    SB_PSI_PATTERN_DATE("                            ________", 10),
    SB_PSI_SPACEANDSIZEWORD("          ", 10),


    VVEK_PSI_PATTERN_FIRST("Представитель ОТК    ________________                         ", 10),
    VVEK_PSI_PATTERN_DATE("                          ___________", 10),
    VVEK_PSI_SPACEANDSIZEWORD("          ", 10),
    VVEK_PSI_PATTERN_NUMBER_SEARCH("\\s*зав.\\s*№\\s*_+\\s*", 10),
    VVEK_PSI_PATTERN_NUMBER(" зав. № ", 10),
    VVEK_PSI_PATTERN_REPLACE_NUMBER("\\s*зав.\\s*№\\s*[A-Za-z0-9]{9}", 10),

    EK_PSI_PATTERN_FIRST("Представитель ОТК    ________________                         ", 10),
    EK_PSI_PATTERN_DATE("                          ___________", 10),
    EK_PSI_SPACEANDSIZEWORD("          ", 10),
    EK_PSI_PATTERN_NUMBER_SEARCH(" зав. № ", 10),
    EK_PSI_PATTERN_NUMBER(" зав. № ", 10),
    EK_PSI_PATTERN_REPLACE_NUMBER("\\s*зав.\\s*№\\s*[A-Za-z0-9]{9}", 10),


    EK6500_PSI_PATTERN_FIRST("Представитель ОТК    __________________                                            ", 10),
    EK6500_PSI_PATTERN_DATE("                          ___________", 10),
    EK6500_PSI_SPACEANDSIZEWORD("          ", 10),
    EK6500_PSI_PATTERN_NUMBER_SEARCH(" зав. № ", 10),
    EK6500_PSI_PATTERN_NUMBER(" зав. № ", 10),
    EK6500_PSI_PATTERN_REPLACE_NUMBER("\\s*зав.\\s*№\\s*[A-Za-z0-9]{9}", 10),

    ORT_PSI_PATTERN_FIRST("Представитель ОТК   __________________                    ", 10),
    ORT_PSI_PATTERN_DATE("                      _______", 10),
    ORT_PSI_SPACEANDSIZEWORD("             ", 10),

    YPP_18_PATTERN_FIRST("Представитель ОТК   _______________     ", 10),
    YPP_18_PATTERN_DATE("                      _______", 10),
    YPP_18_PATTERN_SPACEANDSIZEWORD("                  ", 10),
    YPP_18_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    YPP_18_PATTERN_NUMBER("заводской № ", 10),
    YPP_18_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),


    ORT_PATTERN_FIRST("       Представитель ОТК   _______________       ", 10),
    ORT_PATTERN_DATE("                _______", 10),
    ORT_PATTERN_SPACEANDSIZEWORD("             ", 10),
    ORT_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    ORT_PATTERN_NUMBER("заводской № ", 10),
    ORT_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    VVEK_PATTERN_FIRST(" Представитель ОТК   _______________          ", 10),
    VVEK_PATTERN_DATE("                  ______", 10),
    VVEK_PATTERN_SPACEANDSIZEWORD("               ", 10),
    VVEK_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    VVEK_PATTERN_NUMBER(" заводской № ", 10),
    VVEK_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    VVEK24000_PATTERN_FIRST(" Представитель ОТК   _______________                   ", 10),
    VVEK24000_PATTERN_DATE("              ____________", 10),
    VVEK24000_PATTERN_SPACEANDSIZEWORD("                 ", 10),
    VVEK24000_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    VVEK24000_PATTERN_NUMBER(" заводской № ", 10),
    VVEK24000_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),


    VVEK11400_PATTERN_FIRST("         Представитель ОТК    _______________             ", 10),
    VVEK11400_PATTERN_DATE("                     ______", 10),
    VVEK11400_PATTERN_SPACEANDSIZEWORD("              ", 10),
    VVEK11400_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    VVEK11400_PATTERN_NUMBER(" заводской № ", 10),
    VVEK11400_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    EK_PATTERN_FIRST("Представитель ОТК [ _]+", 10),
    EK_PATTERN_DATE("                    ______", 10),
    EK_PATTERN_SPACEANDSIZEWORD("                   ", 10),
    EK_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    EK_PATTERN_NUMBER(" заводской № ", 10),
    EK_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),

    EK20000_PATTERN_FIRST(" Представитель ОТК   ________________       ", 10),
    EK20000_PATTERN_DATE("                  ______", 10),
    EK20000_PATTERN_SPACEANDSIZEWORD("                 ", 10),
    EK20000_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    EK20000_PATTERN_NUMBER(" заводской № ", 10),
    EK20000_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),


    TVKM_PATTERN_FIRST(" Представитель ОТК   ________________          ", 10),
    TVKM_PATTERN_DATE("                    ________", 10),
    TVKM_PATTERN_SPACEANDSIZEWORD("                   ", 10),
    TVKM_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    TVKM_PATTERN_NUMBER(" заводской № ", 10),
    TVKM_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),


    EK6500_PATTERN_FIRST(" Представитель ОТК   ________________      ", 10),
    EK6500_PATTERN_DATE("                   ______", 10),
    EK6500_PATTERN_SPACEANDSIZEWORD("                 ", 10),
    EK6500_PATTERN_NUMBER_SEARCH("\\s*заводской\\s*№\\s*_+\\s*", 10),
    EK6500_PATTERN_NUMBER(" заводской № ", 10),
    EK6500_PATTERN_REPLACE_NUMBER("\\s*заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),


    BTR_PATTERN_FIRST("Представитель ОТК ____________     ", 10),
    BTR_PATTERN_DATE("                 _________", 10),
    BTR_PATTERN_SPACEANDSIZEWORD("                 ", 10),
    BTR_PATTERN_NUMBER_SEARCH("заводской\\s*№\\s*___*", 10),
    BTR_PATTERN_NUMBER("заводской № ", 10),
    BTR_PATTERN_REPLACE_NUMBER("заводской\\s*№\\s*[A-Za-z0-9]{9}", 10),


    BRPP22_PATTERN_FIRST("Представитель ОТК ____________      ", 12),
    BRPP22_PATTERN_DATE("           ________", 12),
    BRPP22_PATTERN_SPACEANDSIZEWORD("          ", 12),
    BRPP22_PATTERN_NUMBER_SEARCH("заводской\\s*№\\s*___*", 12),
    BRPP22_PATTERN_NUMBER("заводской № ", 12),
    BRPP22_PATTERN_REPLACE_NUMBER("заводской\\s*№\\s*[A-Za-z0-9]{9}", 12),

    RPP_PATTERN_FIRST("Представитель ОТК _________________           ", 10),
    RPP_PATTERN_DATE("                ________", 10),
    RPP_PATTERN_SPACEANDSIZEWORD("             ", 10),
    RPP_PATTERN_NUMBER_SEARCH("\\s*№\\s*_+\\s*", 10),
    RPP_PATTERN_NUMBER(" № ", 10),
    RPP_PATTERN_REPLACE_NUMBER("\\s*№\\s*[A-Za-z0-9]{9}", 10),

    BRPP60_PATTERN_FIRST("Представитель ОТК ____________      ", 12),
    BRPP60_PATTERN_DATE("            _______", 12),
    BRPP60_PATTERN_SPACEANDSIZEWORD("         ", 12),
    BRPP60_PATTERN_NUMBER_SEARCH("заводской\\s*№\\s*___*", 12),
    BRPP60_PATTERN_NUMBER("заводской № ", 12),
    BRPP60_PATTERN_REPLACE_NUMBER("заводской\\s*№\\s*[A-Za-z0-9]{9}", 12),

    BRPP150_PATTERN_FIRST("Представитель ОТК ____________      ", 12),
    BRPP150_PATTERN_DATE("            _______", 12),
    BRPP150_PATTERN_SPACEANDSIZEWORD("        ", 12),
    BRPP150_PATTERN_NUMBER_SEARCH("заводской\\s*№\\s*___*", 12),
    BRPP150_PATTERN_NUMBER("заводской № ", 12),
    BRPP150_PATTERN_REPLACE_NUMBER("заводской\\s*№\\s*[A-Za-z0-9]{9}", 12);


    private final String description;
    private final int size;

    CodeMapping(String description, int size) {
        this.description = description;
        this.size = size;
    }

}
