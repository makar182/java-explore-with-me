package ru.practicum.ewmservice.category.service;

import ru.practicum.ewmservice.category.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getCategories();

    CategoryResponseDto getCategoryById();
}
