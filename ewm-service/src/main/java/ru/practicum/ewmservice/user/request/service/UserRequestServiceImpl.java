package ru.practicum.ewmservice.user.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.user.request.dto.UserRequest_ResponseDto;

import java.util.List;

@Service
public class UserRequestServiceImpl implements UserRequestService{
    @Override
    public List<UserRequest_ResponseDto> getUserRequests() {
        return null;
    }

    @Override
    public UserRequest_ResponseDto addUserRequest() {
        return null;
    }

    @Override
    public UserRequest_ResponseDto cancelUserRequest() {
        return null;
    }
}
