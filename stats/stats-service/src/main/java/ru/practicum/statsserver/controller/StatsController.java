package ru.practicum.statsserver.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsdto.GetStatsResponseDto;
import ru.practicum.statsdto.HitRequestDto;
import ru.practicum.statsserver.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public List<GetStatsResponseDto> getStats(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @Valid LocalDateTime start,
                                              @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @Valid LocalDateTime end,
                                              @RequestParam(name = "uris", required = false) List<String> uris,
                                              @RequestParam(name = "unique", required = false, defaultValue = "false")
                                              Boolean unique) {
        return statsService.getStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody @Valid HitRequestDto hitRequestDto) {
        statsService.hit(hitRequestDto);
    }
}
