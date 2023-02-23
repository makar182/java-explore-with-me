package ru.practicum.ewmservice.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.compilation.dto.CompilationResponseDto;
import ru.practicum.ewmservice.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
public class CompilationController {
    private final CompilationService compilationService;

    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping
    public List<CompilationResponseDto> getCompilations() {
        log.info("");
        return compilationService.getCompilations();
    }

    @GetMapping("/{compId}")
    public CompilationResponseDto getCompilationById(@PathVariable("compId") Long compId) {
        log.info("");
        return compilationService.getCompilationById();
    }
}
