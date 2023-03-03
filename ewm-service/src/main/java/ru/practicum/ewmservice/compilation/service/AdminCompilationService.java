package ru.practicum.ewmservice.compilation.service;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;

public interface AdminCompilationService {
    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    CompilationDto patchCompilation(Long compId, NewCompilationDto newCompilationDto);
}
