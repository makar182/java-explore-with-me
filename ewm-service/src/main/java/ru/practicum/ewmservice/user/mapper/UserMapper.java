package ru.practicum.ewmservice.user.mapper;

import ru.practicum.ewmservice.user.dto.UserRequestDto;
import ru.practicum.ewmservice.user.dto.UserResponseDto;
import ru.practicum.ewmservice.user.model.User;

public class UserMapper {
    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(UserRequestDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
