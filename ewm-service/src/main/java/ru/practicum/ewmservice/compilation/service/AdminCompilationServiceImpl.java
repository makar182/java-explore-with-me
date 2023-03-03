package ru.practicum.ewmservice.compilation.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.repository.CompilationRepository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;

import java.util.List;

@Service
public class AdminCompilationServiceImpl implements AdminCompilationService{
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public AdminCompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto, events);
        Compilation result = compilationRepository.saveAndFlush(compilation);
        return CompilationMapper.toDto(result);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto patchCompilation(Long compId, NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto, events);
        compilation.setId(compId);
        return CompilationMapper.toDto(compilationRepository.saveAndFlush(compilation));
    }
}
