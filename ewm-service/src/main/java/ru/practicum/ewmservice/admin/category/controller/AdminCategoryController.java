package ru.practicum.ewmservice.admin.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.admin.category.dto.AdminCategoryResponseDto;
import ru.practicum.ewmservice.admin.category.service.AdminCategoryService;
import ru.practicum.ewmservice.compilation.dto.CompilationResponseDto;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @PostMapping
    public AdminCategoryResponseDto addCategory() {
        log.info("");
        return adminCategoryService.addCategory();
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable("catId") Long catId) {
        log.info("");
        adminCategoryService.deleteCategoryById();
    }

    @PatchMapping("/{catId}")
    public AdminCategoryResponseDto patchCategory(@PathVariable("catId") Long catId) {
        log.info("");
        return adminCategoryService.patchCategory();
    }
}