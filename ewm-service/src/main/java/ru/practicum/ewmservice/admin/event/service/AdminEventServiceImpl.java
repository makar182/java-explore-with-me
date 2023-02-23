package ru.practicum.ewmservice.admin.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.admin.category.dto.AdminCategoryResponseDto;

import java.util.List;

@Service
public class AdminEventServiceImpl implements AdminEventService{
    @Override
    public List<AdminCategoryResponseDto> getEvents() {
        return null;
    }

    @Override
    public AdminCategoryResponseDto patchEventById() {
        return null;
    }
}
