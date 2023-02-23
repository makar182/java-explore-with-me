package ru.practicum.ewmservice.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.dto.CategoryResponseDto;
import ru.practicum.ewmservice.category.service.CategoryService;
import ru.practicum.ewmservice.user.event.dto.UserEventResponseDto;
import ru.practicum.ewmservice.user.event.service.UserEventService;

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
    public List<CategoryResponseDto> getCategories() {
        log.info("");
        return categoryService.getCategories();
    }

    @GetMapping("/{catId}")
    public CategoryResponseDto getCategoryById(@PathVariable("catId") Long catId) {
        log.info("");
        return categoryService.getCategoryById();
    }
}
