package ru.practicum.ewmservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.user.dto.NewUserDto;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.service.AdminUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                  @RequestParam(name = "from", required = false, defaultValue = "0") int from,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        log.info("Выполнен запрос GET /admin/users с параметрами ids = {}, from = {} и size = {}", ids, from, size);
        return adminUserService.getUsers(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid NewUserDto user) {
        log.info("Выполнен запрос POST /admin/users с телом {}", user);
        return adminUserService.addUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId) {
        log.info("Выполнен запрос DELETE /admin/users/{}", userId);
        adminUserService.deleteUser(userId);
    }
}
