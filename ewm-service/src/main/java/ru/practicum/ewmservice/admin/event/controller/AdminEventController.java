package ru.practicum.ewmservice.admin.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.admin.event.service.AdminEventService;
import ru.practicum.ewmservice.event.model.Event;

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
    public List<Event> getEvents() {
        log.info("");
        return adminEventService.getEvents();
    }

    @PatchMapping("/{eventId}")
    public Event patchEventById(@PathVariable("eventId") Long eventId) {
        log.info("");
        return adminEventService.patchEventById();
    }
}
