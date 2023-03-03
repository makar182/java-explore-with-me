package ru.practicum.ewmservice.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;

@Getter
@Setter
@Builder
public class CompilationDto {
    Long id;
    String title;
    Boolean pinned;
    List<Event> events;
}
