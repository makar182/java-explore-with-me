package ru.practicum.ewmservice.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.dto.CategoryDto;
import ru.practicum.ewmservice.category.service.CategoryService;
import ru.practicum.statsclient.StatsClient;
import ru.practicum.statsdto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private final StatsClient statsClient;
    private final String app = "ewm-service";

    public CategoryController(CategoryService categoryService, StatsClient statsClient) {
        this.categoryService = categoryService;
        this.statsClient = statsClient;
    }

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0") int from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                           HttpServletRequest request) {
        log.info("");//ДОДЕЛАТЬ
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable("catId") Long catId,
                                       HttpServletRequest request) {
        log.info("");//ДОДЕЛАТЬ
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return categoryService.getCategoryById(catId);
    }
}
