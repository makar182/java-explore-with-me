package ru.practicum.ewmservice.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
}
