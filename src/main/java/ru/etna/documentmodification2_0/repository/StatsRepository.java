package ru.etna.documentmodification2_0.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.etna.documentmodification2_0.entity.StatisticsDocHandler;

import org.springframework.data.domain.Pageable;

import java.util.List;


public interface StatsRepository extends JpaRepository<StatisticsDocHandler,Long> {

    @Query("SELECT s.title, AVG(s.durationHandler) as avgDuration " +
            "FROM StatisticsDocHandler s " +
            "WHERE s.status = 'SUCCESS' " +
            "GROUP BY s.title " +
            "ORDER BY avgDuration DESC")
    Page<Object[]> findTopSlowestTemplates(Pageable pageable);


    List<StatisticsDocHandler> findAll();
}
