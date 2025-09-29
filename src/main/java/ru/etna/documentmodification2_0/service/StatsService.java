package ru.etna.documentmodification2_0.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.etna.documentmodification2_0.repository.StatsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;


    public List<Object[]> top5SlowTemplate() {
        Pageable pageable = PageRequest.of(0, 5);
        return statsRepository.findTopSlowestTemplates(pageable).getContent();
    }
}
