package ru.practicum.ewmservice.user.service;

import ru.practicum.ewmservice.user.dto.NewUserDto;
import ru.practicum.ewmservice.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto addUser(NewUserDto user);

    void deleteUser(Long userId);
}
