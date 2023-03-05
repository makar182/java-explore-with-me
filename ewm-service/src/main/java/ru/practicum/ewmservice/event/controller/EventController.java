package ru.practicum.ewmservice.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.enums.EventSortType;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.statsclient.StatsClient;
import ru.practicum.statsdto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventController {
    private final EventService eventService;
    private final StatsClient statsClient;
    private final String app = "ewm-service";


    public EventController(EventService eventService, StatsClient statsClient) {
        this.eventService = eventService;
        this.statsClient = statsClient;
    }

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) List<Long> categoryIds,
                                         @RequestParam(name = "paid", required = false) String paid,
                                         @RequestParam(name = "rangeStart", required = false) LocalDateTime rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) LocalDateTime rangeEnd,
                                         @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false") String onlyAvailable,
                                         @RequestParam(name = "sort", required = false) EventSortType sort,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                         @RequestParam(name = "from", required = false, defaultValue = "0") int from,
                                         HttpServletRequest request) {
        log.info("");
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return eventService.getEvents(text, categoryIds, paid, rangeStart, rangeEnd, onlyAvailable, sort, size, from);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable("id") Long eventId,
                                     HttpServletRequest request) {
        log.info("");
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return eventService.getEventById(eventId);
    }
}
