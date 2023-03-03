package ru.practicum.ewmservice.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NewCompilationDto {
    String title;
    Boolean pinned;
    List<Long> events;
}
