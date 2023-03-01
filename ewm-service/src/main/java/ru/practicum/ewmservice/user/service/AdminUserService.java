package ru.practicum.ewmservice.user.service;

import ru.practicum.ewmservice.user.dto.NewUserDto;
import ru.practicum.ewmservice.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface AdminUserService {
    List<UserDto> getUsers(Optional<List<Long>> ids, int from, int size);

    UserDto addUser(NewUserDto user);

    void deleteUser(Long userId);
}
