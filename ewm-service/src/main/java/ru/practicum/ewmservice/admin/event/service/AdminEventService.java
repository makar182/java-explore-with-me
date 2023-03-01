package ru.practicum.ewmservice.admin.event.service;

import ru.practicum.ewmservice.event.model.Event;

import java.util.List;

public interface AdminEventService {
    List<Event> getEvents();

    Event patchEventById();
}
