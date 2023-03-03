package ru.practicum.ewmservice.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.service.AdminCompilationService;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    public AdminCompilationController(AdminCompilationService adminCompilationService) {
        this.adminCompilationService = adminCompilationService;
    }

    @PostMapping
    public CompilationDto addCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("");
        return adminCompilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public  void deleteCompilation(@PathVariable("compId") Long compId) {
        log.info("");
        adminCompilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(@PathVariable("compId") Long compId,
                                           @RequestBody NewCompilationDto newCompilationDto) {
        log.info("");
        return adminCompilationService.patchCompilation(compId, newCompilationDto);
    }
}