package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.PatchEventDto;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<Event> getEvents(List<Long> users, List<EventState> states,
                          List<Long> categories, LocalDateTime rangeStart,
                          LocalDateTime rangeEnd, Integer from, Integer size);

    Event patchEventById(Long eventId, PatchEventDto patchEventDto);
}
