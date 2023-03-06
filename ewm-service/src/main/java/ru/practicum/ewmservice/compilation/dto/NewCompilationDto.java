package ru.practicum.ewmservice.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
public class NewCompilationDto {
    @NotBlank
    String title;
    Boolean pinned;
    List<Long> events;
}
