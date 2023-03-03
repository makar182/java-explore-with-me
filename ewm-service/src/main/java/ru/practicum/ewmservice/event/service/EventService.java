package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.enums.EventSortType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEvents(String text, List<Long> categoryIds, String paid,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  String onlyAvailable, EventSortType sort, int size, int from);

    EventFullDto getEventById(Long eventId);
}
