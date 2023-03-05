package ru.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.dto.PatchEventDto;
import ru.practicum.ewmservice.event.service.UserEventService;
import ru.practicum.ewmservice.request.dto.PatchRequestDto;
import ru.practicum.ewmservice.request.dto.RequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
public class UserEventController {
    private final UserEventService userEventService;

    @GetMapping
    public List<EventShortDto> getEventsByUserId(@PathVariable("userId") Long userId) {
        log.info(String.format("Выполнен запрос GET /users/{userId}/events с данными userId = %d", userId));
        return userEventService.getEventsByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable("userId") Long userId,
                                 @RequestBody @Valid NewEventDto newEventDto) {
        log.info(String.format("Выполнен запрос POST /users/{userId}/events с данными userId = %d и %s", userId, newEventDto));
        return userEventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable("userId") Long userId,
                                     @PathVariable("eventId") Long eventId) {
        log.info("");//ДОДЕЛАТЬ!
        return userEventService.getEventById(eventId, userId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEventById(@PathVariable("userId") Long userId,
                                       @PathVariable("eventId") Long eventId,
                                       @RequestBody @Valid PatchEventDto patchEventDto) {
        log.info(String.format("Выполнен запрос PATCH /users/{userId}/events/{eventId} с данными userId = %d, eventId = %d и %s", userId, eventId, patchEventDto));
        return userEventService.patchEventById(eventId, userId, patchEventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable("userId") Long userId,
                                             @PathVariable("eventId") Long eventId) {
        log.info("");//ДОДЕЛАТЬ!
        return userEventService.getEventRequestsByUserId(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public List<RequestDto> patchEventRequests(@PathVariable("userId") Long userId,
                                                                 @PathVariable("eventId") Long eventId,
                                                                 @RequestBody PatchRequestDto patchRequestDto) {
        log.info("");//ДОДЕЛАТЬ!
        return userEventService.patchEventRequests(eventId, userId, patchRequestDto);
    }
}
