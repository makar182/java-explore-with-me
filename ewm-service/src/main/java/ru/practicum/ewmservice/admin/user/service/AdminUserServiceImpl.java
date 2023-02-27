package ru.practicum.ewmservice.admin.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.admin.user.repository.AdminUserRepository;
import ru.practicum.ewmservice.user.dto.UserRequestDto;
import ru.practicum.ewmservice.user.dto.UserResponseDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService{
    private final AdminUserRepository adminUserRepository;

    public AdminUserServiceImpl(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public List<UserResponseDto> getUsers() {
        return null;
    }

    @Override
    public UserResponseDto addUser(UserRequestDto user) {
        User result = adminUserRepository.saveAndFlush(UserMapper.toEntity(user));
        return UserMapper.toDto(result);
    }

    @Override
    public void deleteUser() {

    }
}
