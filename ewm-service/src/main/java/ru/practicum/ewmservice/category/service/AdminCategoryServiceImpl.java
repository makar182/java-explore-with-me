package ru.practicum.ewmservice.category.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.exception.CategoryNotExistsException;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
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
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto patchCategory(Long catId, NewCategoryDto newCategoryDto) {
        categoryRepository.findById(catId).orElseThrow(() -> {
            log.info(String.format("Категории %d не существует!", catId));
            throw new CategoryNotExistsException(String.format("Категории %d не существует!", catId));
        });
        Category category = CategoryMapper.toEntity(newCategoryDto);
        category.setId(catId);
        return CategoryMapper.toDto(categoryRepository.saveAndFlush(category));
    }
}
