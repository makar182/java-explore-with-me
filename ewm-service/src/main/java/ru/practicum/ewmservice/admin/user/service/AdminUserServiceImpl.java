package ru.practicum.ewmservice.admin.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.admin.user.dto.AdminUserResponseDto;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService{
    @Override
    public List<AdminUserResponseDto> getUsers() {
        return null;
    }

    @Override
    public AdminUserResponseDto addUser() {
        return null;
    }

    @Override
    public void deleteUser() {

    }
}
