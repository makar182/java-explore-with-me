package ru.practicum.ewmservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.user.dto.NewUserDto;
import ru.practicum.ewmservice.user.service.AdminUserService;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.statsclient.StatsClient;
import ru.practicum.statsdto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final AdminUserService adminUserService;
    private final StatsClient statsClient;
    private final String app = "ewm-service";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) Optional<List<Long>> ids,
                                  @RequestParam(name = "from", required = false, defaultValue = "0") int from,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                  HttpServletRequest request) {
        log.info(String.format("Выполнен запрос GET /admin/users с данными ids = %s, from = %d и size = %d", ids, from, size));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return adminUserService.getUsers(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid NewUserDto user, HttpServletRequest request) {
        log.info(String.format("Выполнен запрос POST /admin/users с данными %s", user));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return adminUserService.addUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId, HttpServletRequest request) {
        log.info(String.format("Выполнен запрос DELETE /admin/users/{userId} с данными %d", userId));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        adminUserService.deleteUser(userId);
    }
}
