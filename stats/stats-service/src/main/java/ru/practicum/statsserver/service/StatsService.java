package ru.practicum.statsserver.service;

import ru.practicum.statsdto.GetStatsResponseDto;
import ru.practicum.statsdto.HitRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<GetStatsResponseDto> getStats(LocalDateTime start, LocalDateTime end, List<Long> uris, Boolean unique);

    void hit(HitRequestDto hitRequestDto);
}
