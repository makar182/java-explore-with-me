package ru.practicum.statsserver.service;

import org.springframework.stereotype.Service;
import ru.practicum.statsdto.GetStatsResponseDto;
import ru.practicum.statsdto.HitRequestDto;
import ru.practicum.statsserver.mapper.StatsMapper;
import ru.practicum.statsserver.repository.StatsRepository;

import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public List<GetStatsResponseDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        return StatsMapper.toGetStatsResponseDtoList(statsRepository.getStats(start, end, uris, unique));
    }

    @Override
    public void hit(HitRequestDto hitRequestDto) {
        statsRepository.hit(StatsMapper.toEntity(hitRequestDto));
    }
}
