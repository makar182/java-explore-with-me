package ru.practicum.ewmservice.admin.user.service;

import ru.practicum.ewmservice.admin.user.dto.AdminUserResponseDto;
import ru.practicum.ewmservice.admin.user.model.AdminUser;

import java.util.List;

public interface AdminUserService {
    List<AdminUserResponseDto> getUsers();

    AdminUserResponseDto addUser();

    void deleteUser();
}
