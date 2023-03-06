package ru.practicum.ewmservice.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.ewmservice.category.service.AdminCategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("");//ДОДЕЛАТЬ
        return adminCategoryService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable("catId") Long catId) {
        log.info("");//ДОДЕЛАТЬ
        adminCategoryService.deleteCategoryById(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable("catId") Long catId,
                                     @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("");//ДОДЕЛАТЬ
        return adminCategoryService.patchCategory(catId, newCategoryDto);
    }
}