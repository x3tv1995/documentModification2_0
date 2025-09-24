package ru.etna.documentmodification2_0;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.etna.documentmodification2_0.service.StatsService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.etna.documentmodification2_0.DocumentModification20ApplicationTests.USERS;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public StatsService statsService() {
        StatsService mock = mock(StatsService.class);
        when(mock.top5SlowTemplate()).thenReturn(USERS);
        return mock;
    }

}
