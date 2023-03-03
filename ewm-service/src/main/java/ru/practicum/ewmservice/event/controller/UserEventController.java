package ru.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.*;
import ru.practicum.ewmservice.event.service.UserEventService;
import ru.practicum.ewmservice.request.dto.PatchRequestDto;
import ru.practicum.ewmservice.request.dto.RequestDto;
import ru.practicum.statsclient.StatsClient;
import ru.practicum.statsdto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
public class UserEventController {
    private final UserEventService userEventService;
    private final StatsClient statsClient;
    private final String app = "ewm-service";

    @GetMapping
    public List<EventShortDto> getEventsByUserId(@PathVariable("userId") Long userId,
                                                 HttpServletRequest request) {
        log.info(String.format("Выполнен запрос GET /users/{userId}/events с данными userId = %d", userId));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return userEventService.getEventsByUserId(userId);
    }

    @PostMapping
    public EventFullDto addEvent(@PathVariable("userId") Long userId,
                                 @RequestBody @Validated NewEventDto newEventDto,
                                 HttpServletRequest request) {
        log.info(String.format("Выполнен запрос POST /users/{userId}/events с данными userId = %d и %s", userId, newEventDto));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return userEventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable("userId") Long userId,
                                     @PathVariable("eventId") Long eventId,
                                     HttpServletRequest request) {
        log.info("");//ДОДЕЛАТЬ!
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return userEventService.getEventById(eventId, userId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEventById(@PathVariable("userId") Long userId,
                                       @PathVariable("eventId") Long eventId,
                                       @RequestBody @Valid PatchEventDto patchEventDto,
                                       HttpServletRequest request) {
        log.info(String.format("Выполнен запрос PATCH /users/{userId}/events/{eventId} с данными userId = %d, eventId = %d и %s", userId, eventId, patchEventDto));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return userEventService.patchEventById(eventId, userId, patchEventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable("userId") Long userId,
                                             @PathVariable("eventId") Long eventId,
                                             HttpServletRequest request) {
        log.info("");//ДОДЕЛАТЬ!
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return userEventService.getEventRequestsByUserId(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public List<RequestDto> patchEventRequests(@PathVariable("userId") Long userId,
                                                                 @PathVariable("eventId") Long eventId,
                                                                 @RequestBody PatchRequestDto patchRequestDto,
                                                                 HttpServletRequest request) {
        log.info("");//ДОДЕЛАТЬ!
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return userEventService.patchEventRequests(eventId, userId, patchRequestDto);
    }
}
