package ru.practicum.ewmservice.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.event.dto.EventResponseDto;
import ru.practicum.ewmservice.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponseDto> getEvents() {
        log.info("");
        return eventService.getEvents();
    }

    @GetMapping("/{id}")
    public EventResponseDto getEventById(@PathVariable("id") Long eventId) {
        log.info("");
        return eventService.getEventById();
    }
}
