package ru.practicum.statsserver.service;

import ru.practicum.statsdto.GetStatsResponseDto;
import ru.practicum.statsdto.HitRequestDto;

import java.util.List;

public interface StatsService {
    List<GetStatsResponseDto> getStats(String start, String end, List<String> uris, Boolean unique);

    void hit(HitRequestDto hitRequestDto);
}
