package ru.practicum.ewmservice.admin.user.service;

import ru.practicum.ewmservice.user.dto.UserRequestDto;
import ru.practicum.ewmservice.user.dto.UserResponseDto;

import java.util.List;

public interface AdminUserService {
    List<UserResponseDto> getUsers();

    UserResponseDto addUser(UserRequestDto user);

    void deleteUser();
}
