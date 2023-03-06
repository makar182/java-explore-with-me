package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.*;
import ru.practicum.ewmservice.request.dto.PatchRequestDto;
import ru.practicum.ewmservice.request.dto.RequestDto;

import java.util.List;

public interface UserEventService {
    List<EventShortDto> getEventsByUserId(Long userId);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventById(Long eventId, Long userId);

    EventFullDto patchEventById(Long eventId, Long userId, PatchEventDto patchEventDto);

    List<RequestDto> getEventRequestsByUserId(Long eventId, Long userId);

    UpdateRequestStatusResultDto patchEventRequests(Long eventId, Long userId, PatchRequestDto patchRequestDto);
}
