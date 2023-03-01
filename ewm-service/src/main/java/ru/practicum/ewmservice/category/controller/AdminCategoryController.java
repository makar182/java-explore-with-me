package ru.practicum.ewmservice.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.service.AdminCategoryService;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.practicum.statsclient.StatsClient;
import ru.practicum.statsdto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;
    private final StatsClient statsClient;
    private final String app = "ewm-service";

    public AdminCategoryController(AdminCategoryService adminCategoryService, StatsClient statsClient) {
        this.adminCategoryService = adminCategoryService;
        this.statsClient = statsClient;
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("");//ДОДЕЛАТЬ
        //statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return adminCategoryService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable("catId") Long catId,
                                   HttpServletRequest request) {
        log.info("");//ДОДЕЛАТЬ
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        adminCategoryService.deleteCategoryById(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable("catId") Long catId,
                                     @RequestBody @Valid NewCategoryDto newCategoryDto,
                                     HttpServletRequest request) {
        log.info("");//ДОДЕЛАТЬ
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return adminCategoryService.patchCategory(catId, newCategoryDto);
    }
}