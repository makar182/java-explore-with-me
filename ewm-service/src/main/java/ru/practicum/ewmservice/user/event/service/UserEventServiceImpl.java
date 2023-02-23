package ru.practicum.ewmservice.user.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.user.event.dto.UserEventRequest_ResponseDto;
import ru.practicum.ewmservice.user.event.dto.UserEventResponseDto;

import java.util.List;

@Service
public class UserEventServiceImpl implements UserEventService{
    @Override
    public List<UserEventResponseDto> getEventsByUserId() {
        return null;
    }

    @Override
    public UserEventResponseDto addEvent() {
        return null;
    }

    @Override
    public UserEventResponseDto getEventById() {
        return null;
    }

    @Override
    public UserEventResponseDto patchEventById() {
        return null;
    }

    @Override
    public List<UserEventRequest_ResponseDto> getEventRequests() {
        return null;
    }

    @Override
    public List<UserEventRequest_ResponseDto> patchEventRequests() {
        return null;
    }
}
