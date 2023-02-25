package ru.practicum.ewmservice.user.event.service;

import ru.practicum.ewmservice.user.event.dto.UserEventRequest_ResponseDto;
import ru.practicum.ewmservice.user.event.dto.UserEventResponseDto;

import java.util.List;

public interface UserEventService {
    List<UserEventResponseDto> getEventsByUserId();

    UserEventResponseDto addEvent();

    UserEventResponseDto getEventById();

    UserEventResponseDto patchEventById();

    List<UserEventRequest_ResponseDto> getEventRequests();

    List<UserEventRequest_ResponseDto> patchEventRequests();
}
