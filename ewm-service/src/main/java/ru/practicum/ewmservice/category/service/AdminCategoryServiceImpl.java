package ru.practicum.ewmservice.category.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService{
    private final CategoryRepository categoryRepository;

    public AdminCategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toEntity(newCategoryDto);
        return CategoryMapper.toDto(categoryRepository.saveAndFlush(category));
    }

    @Override
    public void deleteCategoryById(Long catId) {
        categoryRepository.delete(Category.builder().id(catId).build());
    }

    @Override
    public CategoryDto patchCategory(Long catId, NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toEntity(newCategoryDto);
        category.setId(catId);
        return CategoryMapper.toDto(categoryRepository.saveAndFlush(category));
    }
}
