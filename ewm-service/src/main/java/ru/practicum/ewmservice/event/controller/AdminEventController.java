package ru.practicum.ewmservice.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.PatchEventDto;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.service.AdminEventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Slf4j
public class AdminEventController {
    private final AdminEventService adminEventService;

    public AdminEventController(AdminEventService adminEventService) {
        this.adminEventService = adminEventService;
    }

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                        @RequestParam(name = "states", required = false) List<EventState> states,
                                        @RequestParam(name = "categories", required = false) List<Long> categories,
                                        @RequestParam(name = "rangeStart", required = false) LocalDateTime rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) LocalDateTime rangeEnd,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                        @RequestParam(name = "from", required = false, defaultValue = "0") int from) {
        log.info("Выполнен запрос GET /admin/events с параметрами users = {}, states = {}, categories = {}," +
                        "rangeStart = {}, rangeEnd = {}, size = {}, from = {}", users, states, categories, rangeStart, rangeEnd,
                size, from);
        return adminEventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEventById(@PathVariable("eventId") Long eventId,
                                       @RequestBody @Valid PatchEventDto patchEventDto) {
        log.info("Выполнен запрос GET /admin/events/{} и телом {}", eventId, patchEventDto);
        return adminEventService.patchEventById(eventId, patchEventDto);
    }
}
