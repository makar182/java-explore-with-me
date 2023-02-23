package ru.practicum.ewmservice.user.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.admin.category.dto.AdminCategoryResponseDto;
import ru.practicum.ewmservice.user.event.dto.UserEventRequest_ResponseDto;
import ru.practicum.ewmservice.user.event.dto.UserEventResponseDto;
import ru.practicum.ewmservice.user.event.service.UserEventService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Slf4j
public class UserEventController {
    private final UserEventService userEventService;

    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping
    public List<UserEventResponseDto> getEventsByUserId(@PathVariable("userId") Long userId) {
        log.info("");
        return userEventService.getEventsByUserId();
    }

    @PostMapping
    public UserEventResponseDto addEvent(@PathVariable("userId") Long userId) {
        log.info("");
        return userEventService.addEvent();
    }

    @GetMapping("/{eventId}")
    public UserEventResponseDto getEventById(@PathVariable("userId") Long userId,
                                             @PathVariable("eventId") Long eventId) {
        log.info("");
        return userEventService.getEventById();
    }

    @PatchMapping("/{eventId}")
    public UserEventResponseDto patchEventById(@PathVariable("userId") Long userId,
                                               @PathVariable("eventId") Long eventId) {
        log.info("");
        return userEventService.patchEventById();
    }

    @GetMapping("/{eventId}/requests")
    public List<UserEventRequest_ResponseDto> getEventRequests(@PathVariable("userId") Long userId,
                                                               @PathVariable("eventId") Long eventId) {
        log.info("");
        return userEventService.getEventRequests();
    }

    @PatchMapping("/{eventId}/requests")
    public List<UserEventRequest_ResponseDto> patchEventRequests(@PathVariable("userId") Long userId,
                                                                 @PathVariable("eventId") Long eventId) {
        log.info("");
        return userEventService.patchEventRequests();
    }
}
