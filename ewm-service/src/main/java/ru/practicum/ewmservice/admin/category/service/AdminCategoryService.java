package ru.practicum.ewmservice.admin.category.service;

import ru.practicum.ewmservice.admin.category.dto.AdminCategoryResponseDto;

public interface AdminCategoryService {
    AdminCategoryResponseDto addCategory();

    void deleteCategoryById();

    AdminCategoryResponseDto patchCategory();
}
