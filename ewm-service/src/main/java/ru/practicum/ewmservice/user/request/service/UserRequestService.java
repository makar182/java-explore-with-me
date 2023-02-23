package ru.practicum.ewmservice.user.request.service;

import ru.practicum.ewmservice.user.request.dto.UserRequest_ResponseDto;

import java.util.List;

public interface UserRequestService {
    List<UserRequest_ResponseDto> getUserRequests();

    UserRequest_ResponseDto addUserRequest();

    UserRequest_ResponseDto cancelUserRequest();
}
