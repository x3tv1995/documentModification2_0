package ru.etna.documentmodification2_0;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.etna.documentmodification2_0.service.StatsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Import(TestConfig.class)
class DocumentModification20ApplicationTests {

    @Autowired
    private StatsService statsService;

    public static final Object[] USER_1 = {
            "БРПП150",
            25,
            LocalDate.of(2025, 9, 24),
            LocalDateTime.of(2025, 9, 24, 8, 0, 0),
            LocalDateTime.of(2025, 9, 24, 8, 1, 45),
            105000L,
            "SUCCESS",
            null
    };
    public static final Object[] USER_2 = {
            "ВВЭК11400",
            15,
            LocalDate.of(2025, 9, 23),
            LocalDateTime.of(2025, 9, 23, 16, 20, 0),
            LocalDateTime.of(2025, 9, 23, 16, 20, 18),
            18000L,
            "SUCCESS",
            null
    };
    public static final Object[] USER_3 = {
            "ЭК-20000",
            8,
            LocalDate.of(2025, 9, 23),
            LocalDateTime.of(2025, 9, 23, 17, 10, 0),
            LocalDateTime.of(2025, 9, 23, 17, 10, 10),
            10000L,
            "SUCCESS",
            null
    };
    public static final Object[] USER_4 = {
            "ОРТ",
            3,
            LocalDate.of(2025, 9, 24),
            LocalDateTime.of(2025, 9, 24, 12, 0, 0),
            LocalDateTime.of(2025, 9, 24, 12, 0, 7),
            7000L,
            "SUCCESS",
            null
    };
    public static final Object[] USER_5 = {
            "ПУ_СМК",
            23,
            LocalDate.of(2025, 9, 24),
            LocalDateTime.of(2025, 9, 24, 13, 21, 55, 808_437_000),
            LocalDateTime.of(2025, 9, 24, 13, 21, 58, 8_714_000),
            2L,
            "SUCCESS",
            null
    };
    public static final List<Object[]> USERS = List.of(USER_1, USER_2, USER_3, USER_4, USER_5);

//
//    @Container
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test");
//
//    @Test
//    void testStatsServiceTo() {
//        assertEquals(statsService.top5SlowTemplate(),USERS);
//    }
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url",  postgres::getJdbcUrl);
//        registry.add("spring.datasource.username",  postgres::getUsername);
//        registry.add("spring.datasource.password",  postgres::getPassword);
//        registry.add("spring.liquibase.change-log", () -> "classpath:db/changelog/changeLog.xml");
//    }

}
