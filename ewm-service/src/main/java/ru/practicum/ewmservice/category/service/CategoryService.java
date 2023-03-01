package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(Long catId);
}
