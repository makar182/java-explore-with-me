package ru.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.*;
import ru.practicum.ewmservice.event.service.UserEventService;
import ru.practicum.ewmservice.request.dto.PatchRequestDto;
import ru.practicum.ewmservice.request.dto.RequestDto;

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
        log.info("Выполнен запрос GET /users/{userId}/events с данными userId = {}", userId);
        return userEventService.getEventsByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable("userId") Long userId,
                                 @RequestBody @Valid NewEventDto newEventDto) {
        log.info("Выполнен запрос POST /users/{}/events и телом {}", userId, newEventDto);
        return userEventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable("userId") Long userId,
                                     @PathVariable("eventId") Long eventId) {
        log.info("Выполнен запрос GET /users/{}/events/{}", userId, eventId);
        return userEventService.getEventById(eventId, userId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEventById(@PathVariable("userId") Long userId,
                                       @PathVariable("eventId") Long eventId,
                                       @RequestBody @Valid PatchEventDto patchEventDto) {
        log.info("Выполнен запрос PATCH /users/{}/events/{} и телом {}", userId, eventId, patchEventDto);
        return userEventService.patchEventById(eventId, userId, patchEventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable("userId") Long userId,
                                             @PathVariable("eventId") Long eventId) {
        log.info("Выполнен запрос GET /users/{}/events/{}/requests", userId, eventId);
        return userEventService.getEventRequestsByUserId(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public UpdateRequestStatusResultDto patchEventRequests(@PathVariable("userId") Long userId,
                                                           @PathVariable("eventId") Long eventId,
                                                           @RequestBody PatchRequestDto patchRequestDto) {
        log.info("Выполнен запрос PATCH /users/{}/events/{}/requests и телом {}", userId, eventId, patchRequestDto);
        return userEventService.patchEventRequests(eventId, userId, patchRequestDto);
    }
}
