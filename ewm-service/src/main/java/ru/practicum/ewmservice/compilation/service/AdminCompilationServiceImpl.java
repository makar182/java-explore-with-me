package ru.practicum.ewmservice.compilation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.repository.CompilationRepository;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.CompilationNotExists;
import ru.practicum.ewmservice.exception.UserNotExists;

import java.util.List;

@Service
@Slf4j
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
        compilationRepository.findById(compId).orElseThrow(()->{
            log.info(String.format("Подборки %d не существует!", compId));
            throw new CompilationNotExists(String.format("Подборки %d не существует!", compId));
        });
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto patchCompilation(Long compId, NewCompilationDto newCompilationDto) {
        compilationRepository.findById(compId).orElseThrow(()->{
            log.info(String.format("Подборки %d не существует!", compId));
            throw new CompilationNotExists(String.format("Подборки %d не существует!", compId));
        });

        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toEntity(newCompilationDto, events);
        compilation.setId(compId);
        return CompilationMapper.toDto(compilationRepository.saveAndFlush(compilation));
    }
}
