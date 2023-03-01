package ru.practicum.ewmservice.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.EventState;
import ru.practicum.ewmservice.event.dto.*;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.repository.UserEventRepository;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserEventServiceImpl implements UserEventService {
    private final UserEventRepository userEventRepository;
    private final LocationRepository locationRepository;

    public UserEventServiceImpl(UserEventRepository userEventRepository, LocationRepository locationRepository) {
        this.userEventRepository = userEventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId) {
        return EventMapper.toEventShortDtoList(userEventRepository.findAllByInitiator_Id(userId));
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        Location location = locationRepository.findByLatAndLon(
                newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon()).orElseGet(() -> locationRepository.saveAndFlush(newEventDto.getLocation()));

        Event event = EventMapper.newEventDtoToEntity(newEventDto);
        event.setState(EventState.PUBLISHED);
        event.setInitiator(User.builder().id(userId).build());
        event.setLocation(location);
        event.setCreatedOn(LocalDateTime.now());
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toEventFullDto(userEventRepository.saveAndFlush(event));
    }

    @Override
    public EventFullDto getEventById(Long eventId, Long userId) {
        Optional<Event> event = userEventRepository.findByIdAndInitiator_Id(eventId, userId);
        return event.map(EventMapper::toEventFullDto).orElse(null);
    }

    //ДОРАБОТАТЬ!!!
    @Override
    public EventFullDto patchEventById(Long eventId, Long userId, PatchEventDto patchEventDto) {
        Location location = locationRepository.findByLatAndLon(
                patchEventDto.getLocation().getLat(),
                patchEventDto.getLocation().getLon()).orElseGet(() -> locationRepository.saveAndFlush(patchEventDto.getLocation()));

        Optional<Event> savedEvent = userEventRepository.findByIdAndInitiator_Id(eventId, userId); //Потом начать выдавать ошибку если не найдет
        //перечислить поля которые из БД и в запросе не приходили, также не забыть про проверки из сваггера на конфликты
        Event event = EventMapper.patchEventDtoToEntity(patchEventDto);
        event.setId(eventId);
        event.setInitiator(User.builder().id(userId).build());
        event.setLocation(location);
        event.setState(savedEvent.get().getState()); //Надо брать из существующей записи!!
        event.setCreatedOn(LocalDateTime.now());
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toEventFullDto(userEventRepository.saveAndFlush(event));
    }

    @Override
    public List<UpdateRequestStatusResultDto> getEventRequests() {
        return null;
    }

    @Override
    public List<UpdateRequestStatusResultDto> patchEventRequests() {
        return null;
    }
}
