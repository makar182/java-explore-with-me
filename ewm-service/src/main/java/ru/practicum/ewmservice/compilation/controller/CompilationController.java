package ru.practicum.ewmservice.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
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
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) String pinned,
                                                @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                @RequestParam(name = "from", required = false, defaultValue = "0") int from) {
        log.info("Выполнен запрос GET /compilations с параметрами pinned = {}, size = {} и from = {}", pinned, size, from);
        return compilationService.getCompilations(pinned, size, from);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable("compId") Long compId) {
        log.info("Выполнен запрос GET /compilations/{}", compId);
        return compilationService.getCompilationById(compId);
    }
}
