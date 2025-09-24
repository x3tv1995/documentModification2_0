package ru.etna.documentmodification2_0.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
public class StatisticsDocHandler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int countDoc; // количество документов
    private LocalDate date; //дата работы приложения
    private LocalDateTime startTime;//начало работы
    private  LocalDateTime endTime;//конец работы
    private long durationHandler; // продолжительность обработки

    private String status;//статус успех и провал
    private String errorMessage; //ошибки




}
