package ru.practicum.ewmservice.request.service;

import ru.practicum.ewmservice.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getRequestsByUserId(Long userId);

    RequestDto addRequest(Long userId, Long eventId);

    RequestDto cancelRequest(Long userId, Long requestId);
}
