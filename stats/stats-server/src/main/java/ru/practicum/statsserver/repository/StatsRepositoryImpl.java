package ru.practicum.statsserver.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.model.Stats;
import ru.practicum.statsserver.model.StatsSummary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class StatsRepositoryImpl implements StatsRepository {
    private final JdbcTemplate jdbcTemplate;

    public StatsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<StatsSummary> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String sql;
        List<StatsSummary> result;
        List<Object> params = new ArrayList<>();

        if (uris.size() == 0) {
            if (unique) {
                sql = "SELECT app, uri, count(distinct ip) as cnt FROM stats " +
                        "WHERE timestamp BETWEEN ? AND ? AND uri <> '/events' GROUP BY app, uri ORDER BY cnt DESC";
            } else {
                sql = "SELECT app, uri, count(1) as cnt FROM stats WHERE timestamp " +
                        "BETWEEN ? AND ? GROUP BY app, uri ORDER BY cnt DESC";
            }
            result = jdbcTemplate.query(sql, this::makeStatsSummary, Timestamp.valueOf(start), Timestamp.valueOf(end));
        } else {
            String inSql = String.join(",", Collections.nCopies(uris.size(), "?"));
            if (unique) {
                sql = String.format("SELECT app, uri, count(distinct ip) cnt FROM stats " +
                        "WHERE timestamp BETWEEN ? AND ? AND uri IN (%s) GROUP BY app, uri ORDER BY cnt DESC", inSql);
            } else {
                sql = String.format("SELECT app, uri, count(1) cnt FROM stats " +
                        "WHERE timestamp BETWEEN ? AND ? AND uri IN (%s) GROUP BY app, uri ORDER BY cnt DESC", inSql);
            }
            params.add(start);
            params.add(end);
            params.addAll(uris);
            result = jdbcTemplate.query(sql, this::makeStatsSummary, params.toArray());
        }

        return result;
    }

    public void hit(Stats stats) {
        SimpleJdbcInsert simpleJdbcInsertFilm = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("stats")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("app", stats.getApp());
        parameters.put("uri", stats.getUri());
        parameters.put("ip", stats.getIp());
        parameters.put("timestamp", stats.getTimestamp());
        simpleJdbcInsertFilm.executeAndReturnKey(parameters).longValue();
    }

    private StatsSummary makeStatsSummary(ResultSet rs, int rowNum) throws SQLException {
        return StatsSummary.builder()
                .app(rs.getString("app"))
                .uri(rs.getString("uri"))
                .hits(rs.getLong("cnt"))
                .build();
    }
}
