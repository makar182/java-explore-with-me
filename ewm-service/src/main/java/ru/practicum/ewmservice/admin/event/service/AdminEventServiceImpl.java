package ru.practicum.ewmservice.admin.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;

@Service
public class AdminEventServiceImpl implements AdminEventService{
    @Override
    public List<Event> getEvents() {
        return null;
    }

    @Override
    public Event patchEventById() {
        return null;
    }
}
