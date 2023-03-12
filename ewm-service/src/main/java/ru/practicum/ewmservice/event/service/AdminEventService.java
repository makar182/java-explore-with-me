package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.PatchEventDto;
import ru.practicum.ewmservice.event.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<EventFullDto> getEvents(List<Long> users, List<EventState> states,
                                 List<Long> categories, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto patchEventById(Long eventId, PatchEventDto patchEventDto);
}
