package ru.practicum.ewmservice.compilation.service;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getCompilations(String pinned, int size, int from);

    CompilationDto getCompilationById(Long compId);
}
