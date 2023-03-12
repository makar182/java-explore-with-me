package ru.practicum.ewmservice.user.mapper;

import ru.practicum.ewmservice.user.dto.NewUserDto;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(NewUserDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static List<UserDto> toDtoList(List<User> users) {
        List<UserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(UserMapper.toDto(user));
        }
        return result;
    }
}
