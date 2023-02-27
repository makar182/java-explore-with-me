package ru.practicum.ewmservice.admin.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.admin.compilation.dto.AdminCompilationResponseDto;
import ru.practicum.ewmservice.admin.compilation.service.AdminCompilationService;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    public AdminCompilationController(AdminCompilationService adminCompilationService) {
        this.adminCompilationService = adminCompilationService;
    }

    @PostMapping
    public AdminCompilationResponseDto addCompilation() {
        log.info("");
        return adminCompilationService.addCompilation();
    }

    @DeleteMapping("/{compId}")
    public  void deleteCompilation(@PathVariable("compId") Long compId) {
        log.info("");
        adminCompilationService.deleteCompilation();
    }

    @PatchMapping
    public AdminCompilationResponseDto patchCompilation() {
        log.info("");
        return adminCompilationService.patchCompilation();
    }
}
