package ru.practicum.ewmservice.compilation.service;

import ru.practicum.ewmservice.compilation.dto.CompilationResponseDto;

import java.util.List;

public interface CompilationService {
    List<CompilationResponseDto> getCompilations();

    CompilationResponseDto getCompilationById();
}
