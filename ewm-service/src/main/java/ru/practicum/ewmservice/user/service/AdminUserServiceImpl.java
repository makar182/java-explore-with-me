package ru.practicum.ewmservice.user.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.user.dto.NewUserDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.AdminUserRepository;
import ru.practicum.ewmservice.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository adminUserRepository;

    public AdminUserServiceImpl(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public List<UserDto> getUsers(Optional<List<Long>> ids, int from, int size) {
        int page = from == 0 ? 0 : (from / size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ids.map(longs -> UserMapper.toDtoList(adminUserRepository.findAllByIdIn(longs, pageable))).orElseGet(()
                -> UserMapper.toDtoList(adminUserRepository.findAll(pageable).getContent()));
    }

    @Override
    public UserDto addUser(NewUserDto user) {
        User result = adminUserRepository.saveAndFlush(UserMapper.toEntity(user));
        return UserMapper.toDto(result);
    }

    @Override
    public void deleteUser(Long userId) {
        adminUserRepository.deleteById(userId);
    }
}
