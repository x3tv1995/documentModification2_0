package ru.etna.documentmodification2_0.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.repository.StatsRepository;

import java.util.List;

@Service
public class StatsService {

    @Autowired
    private  StatsRepository statsRepository;

    public List<Object[]> top5SlowTemplate(){
        Pageable pageable = PageRequest.of(0, 5);
     return    statsRepository.findTopSlowestTemplates(pageable).getContent();
    }
}
