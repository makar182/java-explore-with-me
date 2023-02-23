package ru.practicum.ewmservice.category.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.dto.CategoryResponseDto;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Override
    public List<CategoryResponseDto> getCategories() {
        return null;
    }

    @Override
    public CategoryResponseDto getCategoryById() {
        return null;
    }
}
