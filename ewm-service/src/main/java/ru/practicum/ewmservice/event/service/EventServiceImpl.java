package ru.practicum.ewmservice.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.dto.EventResponseDto;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{
    @Override
    public List<EventResponseDto> getEvents() {
        return null;
    }

    @Override
    public EventResponseDto getEventById() {
        return null;
    }
}
