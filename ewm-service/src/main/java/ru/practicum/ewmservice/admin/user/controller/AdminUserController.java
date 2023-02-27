package ru.practicum.ewmservice.admin.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.admin.user.service.AdminUserService;
import ru.practicum.ewmservice.user.dto.UserRequestDto;
import ru.practicum.ewmservice.user.dto.UserResponseDto;
import ru.practicum.statsclient.StatsClient;
import ru.practicum.statsdto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final AdminUserService adminUserService;
    private final StatsClient statsClient;
    private final String app = "ewm-service";

    @GetMapping
    public List<UserResponseDto> getUsers() {
        log.info("");
        return adminUserService.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto addUser(@RequestBody @Valid UserRequestDto user, HttpServletRequest request) {
        log.info(String.format("Выполнен запрос POST /admin/users с данными %s", user));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return adminUserService.addUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        log.info("");
        adminUserService.deleteUser();
    }
}
