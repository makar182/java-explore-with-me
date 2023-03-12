package ru.practicum.ewmservice.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.exception.UserNotExistsException;
import ru.practicum.ewmservice.user.dto.NewUserDto;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        int page = from == 0 ? 0 : (from / size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return UserMapper.toDtoList(userRepository.findAllByIdIn(ids, pageable));
    }

    @Override
    public UserDto addUser(NewUserDto newUser) {
        User result = userRepository.saveAndFlush(UserMapper.toEntity(newUser));
        return UserMapper.toDto(result);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> {
            log.info("Пользователь {} не существует!", userId);
            throw new UserNotExistsException(String.format("Пользователь %d не существует!", userId));
        });
        userRepository.deleteById(userId);
    }
}
