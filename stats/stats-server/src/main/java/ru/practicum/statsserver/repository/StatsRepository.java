package ru.practicum.statsserver.repository;

import ru.practicum.statsserver.model.Stats;
import ru.practicum.statsserver.model.StatsSummary;

import java.util.List;

public interface StatsRepository {
    void hit(Stats stats);

    List<StatsSummary> getStats(String start, String end, List<String> uris, Boolean unique);
}
