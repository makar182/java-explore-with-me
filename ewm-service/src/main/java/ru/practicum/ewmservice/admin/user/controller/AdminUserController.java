package ru.practicum.ewmservice.admin.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.admin.user.dto.AdminUserResponseDto;
import ru.practicum.ewmservice.admin.user.service.AdminUserService;
import ru.practicum.ewmservice.event.dto.EventResponseDto;
import ru.practicum.ewmservice.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Slf4j
public class AdminUserController {
    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public List<AdminUserResponseDto> getUsers() {
        log.info("");
        return adminUserService.getUsers();
    }

    @PostMapping
    public AdminUserResponseDto addUser() {
        log.info("");
        return adminUserService.addUser();
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        log.info("");
        adminUserService.deleteUser();
    }
}
