package ru.etna.documentmodification2_0.configuration;

import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfficeConfig {

    @Bean(destroyMethod = "stop")
    public OfficeManager officeManager() {
        LocalOfficeManager manager = LocalOfficeManager.builder()
                .install()
                .portNumbers(2002, 2003, 2004, 2005) //свободные порты
                .taskExecutionTimeout(300_000L)
                .build();

        try {
            manager.start();
            return manager;
        } catch (OfficeException e) {
            throw new RuntimeException("Не удалось запустить LibreOffice", e);
        }
    }


}
