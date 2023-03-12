package ru.practicum.ewmservice.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0") int from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        log.info("Выполнен запрос GET /categories с параметрами from = {} и size = {}", from, size);
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable("catId") Long catId) {
        log.info("Выполнен запрос GET /categories/{}", catId);
        return categoryService.getCategoryById(catId);
    }
}
