package ru.practicum.ewmservice.admin.compilation.service;

import ru.practicum.ewmservice.admin.compilation.dto.AdminCompilationResponseDto;

public interface AdminCompilationService {
    AdminCompilationResponseDto addCompilation();

    void deleteCompilation();

    AdminCompilationResponseDto patchCompilation();
}
