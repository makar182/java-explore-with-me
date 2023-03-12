package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;

public interface AdminCategoryService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(Long catId);

    CategoryDto patchCategory(Long catId, NewCategoryDto newCategoryDto);
}
