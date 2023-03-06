package ru.practicum.statsserver.service;

import org.springframework.stereotype.Service;
import ru.practicum.statsdto.GetStatsResponseDto;
import ru.practicum.statsdto.HitRequestDto;
import ru.practicum.statsserver.mapper.StatsMapper;
import ru.practicum.statsserver.model.StatsSummary;
import ru.practicum.statsserver.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public List<GetStatsResponseDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime parsedStart = LocalDateTime.parse(start.replace("T"," "), dateTimeFormatter);
        LocalDateTime parsedEnd = LocalDateTime.parse(end.replace("T"," "), dateTimeFormatter);
        List<StatsSummary> result = statsRepository.getStats(parsedStart, parsedEnd, uris, unique);
        if(result.isEmpty()) {
            return List.of();
        } else {
            return StatsMapper.toGetStatsResponseDtoList(result);
        }
    }

    @Override
    public void hit(HitRequestDto hitRequestDto) {
        statsRepository.hit(StatsMapper.toEntity(hitRequestDto));
    }
}
