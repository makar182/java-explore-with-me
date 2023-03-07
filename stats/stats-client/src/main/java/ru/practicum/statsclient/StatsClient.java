package ru.practicum.statsclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statsdto.GetStatsResponseDto;
import ru.practicum.statsdto.HitRequestDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.uri}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public Map<Long, Long> getStats(List<Long> uris, Boolean unique, @Nullable LocalDateTime start, @Nullable LocalDateTime end) {
        start = LocalDateTime.now().minusYears(100).truncatedTo(ChronoUnit.SECONDS);
        end = LocalDateTime.now().plusYears(100).truncatedTo(ChronoUnit.SECONDS);

        StringBuilder sbUris = new StringBuilder();
        for (Long aLong : uris) {
            sbUris.append("/events/").append(aLong);
        }

        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", sbUris.toString(),
                "unique", unique
        );
        ResponseEntity<Object> objects = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        ObjectMapper objectMapper = new ObjectMapper();
        List<GetStatsResponseDto> stats = objectMapper.convertValue(objects.getBody(), new TypeReference<>() {
        });
        if (stats == null) {
            return new HashMap<>();
        } else {
            return stats.stream()
                    .collect(Collectors.toMap(x -> Long.parseLong(x.getUri().split("/", 0)[2]), GetStatsResponseDto::getHits));
        }
    }

    public ResponseEntity<Object> hit(HitRequestDto hitRequestDto) {
        return post("/hit", hitRequestDto);
    }
}
