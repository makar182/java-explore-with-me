package ru.practicum.ewmservice.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.service.AdminCompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    public AdminCompilationController(AdminCompilationService adminCompilationService) {
        this.adminCompilationService = adminCompilationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Выполнен запрос POST /admin/compilations с телом {}", newCompilationDto);
        return adminCompilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        log.info("Выполнен запрос DELETE /admin/compilations/{}", compId);
        adminCompilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(@PathVariable("compId") Long compId,
                                           @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Выполнен запрос PATCH /admin/compilations/{} и телом {}", compId, newCompilationDto);
        return adminCompilationService.patchCompilation(compId, newCompilationDto);
    }
}
