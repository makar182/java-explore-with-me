package ru.practicum.ewmservice.category.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.exception.CategoryNotExists;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        int page = from == 0 ? 0 : (from / size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return CategoryMapper.toDtoList(categoryRepository.findAll(pageable).getContent());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(()->{
            log.info(String.format("Категории %d не существует!", catId));
            throw new CategoryNotExists(String.format("Категории %d не существует!", catId));
        });
        return CategoryMapper.toDto(category);
    }
}
