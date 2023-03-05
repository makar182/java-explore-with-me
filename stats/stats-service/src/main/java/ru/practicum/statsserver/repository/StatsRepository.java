package ru.practicum.statsserver.repository;

import ru.practicum.statsserver.model.Stats;
import ru.practicum.statsserver.model.StatsSummary;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository {
    void hit(Stats stats);

    List<StatsSummary> getStats(LocalDateTime start, LocalDateTime end, List<Long> uris, Boolean unique);
}
