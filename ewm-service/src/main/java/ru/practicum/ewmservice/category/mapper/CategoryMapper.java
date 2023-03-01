package ru.practicum.ewmservice.category.mapper;

import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static Category toEntity(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .name(newCategoryDto.getName())
                .build();
    }

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryDto> toDtoList(List<Category> categories) {
        List<CategoryDto> result = new ArrayList<>();
        for (Category category : categories) {
            result.add(CategoryMapper.toDto(category));
        }
        return result;
    }
}
