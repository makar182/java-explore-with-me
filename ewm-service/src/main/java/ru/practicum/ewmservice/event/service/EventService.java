package ru.practicum.ewmservice.event.service;

import ru.practicum.ewmservice.event.dto.EventResponseDto;

import java.util.List;

public interface EventService {
    List<EventResponseDto> getEvents();

    EventResponseDto getEventById();
}
