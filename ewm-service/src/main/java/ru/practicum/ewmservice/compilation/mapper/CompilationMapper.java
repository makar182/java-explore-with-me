package ru.practicum.ewmservice.compilation.mapper;

import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.event.model.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CompilationMapper {
    public static Compilation toEntity(NewCompilationDto newCompilationDto, List<Event> events) {
        return Compilation.builder()
                .events(new HashSet<>(events))
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .build();
    }

    public static CompilationDto toDto(Compilation compilation) {
        List<CompilationDto.CompilationEvent> parsedEvents = new ArrayList<>();

        for (Event event : compilation.getEvents()) {
            parsedEvents.add(new CompilationDto.CompilationEvent(event));
        }

        return CompilationDto.builder()
                .id(compilation.getId())
                .events(parsedEvents)
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .build();
    }

    public static List<CompilationDto> toDtoList(List<Compilation> compilations) {
        List<CompilationDto> result = new ArrayList<>();
        for (Compilation compilation : compilations) {
            result.add(CompilationMapper.toDto(compilation));
        }
        return result;
    }
}
