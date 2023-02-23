package ru.practicum.ewmservice.admin.event.service;

import ru.practicum.ewmservice.admin.category.dto.AdminCategoryResponseDto;

import java.util.List;

public interface AdminEventService {
    List<AdminCategoryResponseDto> getEvents();

    AdminCategoryResponseDto patchEventById();
}
