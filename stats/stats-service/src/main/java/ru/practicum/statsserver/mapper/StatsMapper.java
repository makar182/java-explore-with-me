package ru.practicum.statsserver.mapper;

import ru.practicum.statsdto.GetStatsResponseDto;
import ru.practicum.statsdto.HitRequestDto;
import ru.practicum.statsserver.model.Stats;
import ru.practicum.statsserver.model.StatsSummary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatsMapper {
    public static List<GetStatsResponseDto> toGetStatsResponseDtoList(List<StatsSummary> stats) {
        if (stats.isEmpty()) return List.of();

        List<GetStatsResponseDto> result = new ArrayList<>();
        for (StatsSummary stat : stats) {
            result.add(StatsMapper.toGetStatsResponseDto(stat));
        }
        return result;
    }

    public static GetStatsResponseDto toGetStatsResponseDto(StatsSummary stat) {
        return GetStatsResponseDto.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(stat.getHits())
                .build();
    }

    public static Stats toEntity(HitRequestDto hitRequestDto) {
        return Stats.builder()
                .app(hitRequestDto.getApp())
                .ip(hitRequestDto.getIp())
                .uri(hitRequestDto.getUri())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
