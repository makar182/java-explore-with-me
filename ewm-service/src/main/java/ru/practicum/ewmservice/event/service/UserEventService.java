package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.*;

import java.util.List;

public interface UserEventService {
    List<EventShortDto> getEventsByUserId(Long userId);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventById(Long eventId, Long userId);

    EventFullDto patchEventById(Long eventId, Long userId, PatchEventDto patchEventDto);

    List<UpdateRequestStatusResultDto> getEventRequests();

    List<UpdateRequestStatusResultDto> patchEventRequests();
}
