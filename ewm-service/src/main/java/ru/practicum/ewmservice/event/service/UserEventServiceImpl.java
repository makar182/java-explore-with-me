package ru.practicum.ewmservice.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.event.dto.*;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.enums.EventStateAction;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.event.repository.UserEventRepository;
import ru.practicum.ewmservice.exception.*;
import ru.practicum.ewmservice.request.dto.PatchRequestDto;
import ru.practicum.ewmservice.request.dto.RequestDto;
import ru.practicum.ewmservice.request.enums.RequestStatus;
import ru.practicum.ewmservice.request.mapper.RequestMapper;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.request.repository.RequestRepository;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.UserRepository;
import ru.practicum.statsclient.StatsClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserEventServiceImpl implements UserEventService {
    private final UserEventRepository userEventRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final StatsClient statsClient;

    public UserEventServiceImpl(UserEventRepository userEventRepository, LocationRepository locationRepository, RequestRepository requestRepository, EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository, StatsClient statsClient) {
        this.userEventRepository = userEventRepository;
        this.locationRepository = locationRepository;
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.statsClient = statsClient;
    }

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId) {
        List<Event> events = userEventRepository.findAllByInitiator_Id(userId);

        if (events.isEmpty()) {
            return List.of();
        }

        List<EventShortDto> eventsDto = EventMapper.toEventShortDtoList(events);

        List<Long> uris = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        Map<Long, Long> stats = statsClient.getStats(uris, false, null, null);
        Map<Long, Long> confirmedRequests = requestRepository.getConfirmedRequestsBatch(uris);

        for (EventShortDto event : eventsDto) {
            event.setViews(stats.getOrDefault(event.getId(), 0L));
            event.setConfirmedRequests(confirmedRequests.getOrDefault(event.getId(), 0L));
        }

        return eventsDto;
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            log.info(String.format("Некорректная дата для события %s не существует!", newEventDto.getTitle()));
            throw new InvalidEventDateException(String.format("Некорректная дата для события %s не существует!", newEventDto.getTitle()));
        }

        Location location = locationRepository.findByLatAndLon(
                newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon()).orElseGet(() -> locationRepository.saveAndFlush(newEventDto.getLocation()));

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> {
            log.info(String.format("Категории %d не существует!", newEventDto.getCategory()));
            throw new CategoryNotExistsException(String.format("Категории %d не существует!", newEventDto.getCategory()));
        });

        User initiator = userRepository.findById(userId).orElseThrow(() -> {
            log.info(String.format("Пользователя %d не существует!", userId));
            throw new UserNotExistsException(String.format("Пользователя %d не существует!", userId));
        });

        Event event = EventMapper.newEventDtoToEntity(newEventDto);
        event.setState(EventState.PENDING);
        event.setInitiator(User.builder().id(userId).build());
        event.setLocation(location);
        event.setCreatedOn(LocalDateTime.now());
        event.setPublishedOn(LocalDateTime.now());
        event.setCategory(category);
        event.setInitiator(initiator);
        event = userEventRepository.saveAndFlush(event);

        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        eventFullDto.setConfirmedRequests(0L);

        return eventFullDto;
    }

    @Override
    public EventFullDto getEventById(Long eventId, Long userId) {
        Event event = userEventRepository.findByIdAndInitiator_Id(eventId, userId).orElseThrow(() -> {
            log.info(String.format("События %d не существует!", eventId));
            throw new EventNotExistsException(String.format("События %d не существует!", eventId));
        });

        Map<Long, Long> stats = statsClient.getStats(List.of(eventId), false, null, null);
        Map<Long, Long> confirmedRequests = requestRepository.getConfirmedRequestsBatch(List.of(eventId));

        EventFullDto eventDto = EventMapper.toEventFullDto(event);

        eventDto.setViews(stats.getOrDefault(event.getId(), 0L));
        eventDto.setConfirmedRequests(confirmedRequests.getOrDefault(event.getId(), 0L));

        return eventDto;
    }

    @Override
    public EventFullDto patchEventById(Long eventId, Long userId, PatchEventDto patchEventDto) {
        Event oldEvent = userEventRepository.findByIdAndInitiator_Id(eventId, userId).orElseThrow(() -> {
            log.info(String.format("События %d не существует!", eventId));
            throw new EventNotExistsException(String.format("События %d не существует!", eventId));
        });

        if (patchEventDto.getEventDate() != null && patchEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            log.info(String.format("Некорректная дата для события %s не существует!", patchEventDto.getTitle()));
            throw new InvalidEventDateException(String.format("Некорректная дата для события %s не существует!",
                    patchEventDto.getTitle()));
        }

        if (!oldEvent.getState().equals(EventState.PENDING) && !oldEvent.getState().equals(EventState.REJECTED)) {
            log.info("Изменять можно только неопубликованные или отмененные события!");
            throw new InvalidEventDateException("Изменять можно только неопубликованные или отмененные события!");
        }

        if (patchEventDto.getAnnotation() != null) {
            oldEvent.setAnnotation(patchEventDto.getAnnotation());
        }
        if (patchEventDto.getCategory() != null) {
            oldEvent.setCategory(new Category(patchEventDto.getCategory(), null));
        }
        if (patchEventDto.getDescription() != null) {
            oldEvent.setDescription(patchEventDto.getDescription());
        }
        if (patchEventDto.getEventDate() != null) {
            oldEvent.setEventDate(patchEventDto.getEventDate());
        }
        if (patchEventDto.getAnnotation() != null) {
            oldEvent.setAnnotation(patchEventDto.getAnnotation());
        }
        if (patchEventDto.getLocation() != null) {
            Location location = locationRepository.findByLatAndLon(
                    patchEventDto.getLocation().getLat(),
                    patchEventDto.getLocation().getLon()).orElseGet(() -> locationRepository.saveAndFlush(patchEventDto.getLocation()));
            oldEvent.setLocation(location);
        }
        if (patchEventDto.getPaid() != null) {
            oldEvent.setPaid(patchEventDto.getPaid());
        }
        if (patchEventDto.getParticipantLimit() != null) {
            oldEvent.setParticipantLimit(patchEventDto.getParticipantLimit());
        }
        if (patchEventDto.getRequestModeration() != null) {
            oldEvent.setRequestModeration(patchEventDto.getRequestModeration());
        }
        if (patchEventDto.getStateAction() != null) {
            if (patchEventDto.getStateAction().equals(EventStateAction.SEND_TO_REVIEW)) {
                if (oldEvent.getState().equals(EventState.REJECTED)) {
                    oldEvent.setState(EventState.PENDING);
                } else {
                    log.info("Установка некорректного статуса!");
                    throw new InvalidStateActionException("Установка некорректного статуса!");
                }
            } else if (patchEventDto.getStateAction().equals(EventStateAction.CANCEL_REVIEW)) {
                if (oldEvent.getState().equals(EventState.PENDING)) {
                    oldEvent.setState(EventState.CANCELED);
                } else {
                    log.info("Установка некорректного статуса!");
                    throw new InvalidStateActionException("Установка некорректного статуса!");
                }
            } else {
                log.info("Установка некорректного статуса!");
                throw new InvalidStateActionException("Установка некорректного статуса!");
            }
        }
        if (patchEventDto.getTitle() != null) {
            oldEvent.setTitle(patchEventDto.getTitle());
        }

        Event result = userEventRepository.saveAndFlush(oldEvent);

        Map<Long, Long> stats = statsClient.getStats(List.of(eventId), false, null, null);
        Map<Long, Long> confirmedRequests = requestRepository.getConfirmedRequestsBatch(List.of(eventId));

        EventFullDto eventDto = EventMapper.toEventFullDto(result);

        eventDto.setViews(stats.getOrDefault(eventId, 0L));
        eventDto.setConfirmedRequests(confirmedRequests.getOrDefault(eventId, 0L));

        return eventDto;
    }

    @Override
    public List<RequestDto> getEventRequestsByUserId(Long eventId, Long userId) {
        return RequestMapper.toDtoList(requestRepository.findAllByEvent_IdAndEvent_Initiator_Id(eventId, userId));
    }

    @Override
    public UpdateRequestStatusResultDto patchEventRequests(Long eventId, Long userId, PatchRequestDto patchRequestDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.info(String.format("События %d не существует!", eventId));
            throw new EventNotExistsException(String.format("События %d не существует!", eventId));
        });

        if (!event.getInitiator().getId().equals(userId)) {
            log.info(String.format("Данное событие не принадлежит пользователю %d!", userId));
            throw new RequestException(String.format("Данное событие не принадлежит пользователю %d!", userId));
        }

        List<Request> requests = requestRepository.findAllByEvent_IdAndEvent_Initiator_IdAndIdIn(eventId, userId, patchRequestDto.getRequestIds());

        if (!requests.stream()
                .map(Request::getStatus)
                .allMatch(RequestStatus.PENDING::equals)) {
            log.info("Нельзя изменять статус если статус заявки не равен PENDING!");
            throw new RequestException("Нельзя изменять статус если статус заявки не равен PENDING!");
        }

        if (patchRequestDto.getStatus().equals(RequestStatus.REJECTED)) {
            requests.forEach(request -> request.setStatus(RequestStatus.REJECTED));
            requestRepository.saveAllAndFlush(requests);
        } else {
            int confirmedRequests = requestRepository.findAllByEvent_IdAndStatus(eventId, RequestStatus.CONFIRMED).size();
            if (confirmedRequests >= event.getParticipantLimit()) {
                log.info("На событие свободных мест больше нет!");
                throw new RequestException("На событие свободных мест больше нет!");
            }

            requests.forEach(request -> request.setStatus(patchRequestDto.getStatus()));
            requestRepository.saveAllAndFlush(requests);

            if (confirmedRequests == event.getParticipantLimit() - 1) {
                List<Request> pendingRequests = requestRepository.findAllByEvent_IdAndStatus(eventId, RequestStatus.PENDING);
                if (!pendingRequests.isEmpty()) {
                    pendingRequests.forEach(request -> request.setStatus(RequestStatus.REJECTED));
                    requestRepository.saveAllAndFlush(requests);
                }
            }
        }

        List<RequestDto> confirmedRequests = requests.stream()
                .filter(x -> x.getStatus().equals(RequestStatus.CONFIRMED))
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());

        List<RequestDto> rejectedRequests = requests.stream()
                .filter(x -> x.getStatus().equals(RequestStatus.REJECTED))
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());

        return new UpdateRequestStatusResultDto(confirmedRequests, rejectedRequests);
    }
}
