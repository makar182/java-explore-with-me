package ru.practicum.ewmservice.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.category.repository.CategoryRepository;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.PatchEventDto;
import ru.practicum.ewmservice.event.enums.EventStateAction;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.event.repository.LocationRepository;
import ru.practicum.ewmservice.exception.CategoryNotExistsException;
import ru.practicum.ewmservice.exception.EventNotExistsException;
import ru.practicum.ewmservice.exception.InvalidEventDateException;
import ru.practicum.ewmservice.exception.InvalidStateActionException;
import ru.practicum.ewmservice.request.repository.RequestRepository;
import ru.practicum.statsclient.StatsClient;

@Service
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {
    @PersistenceContext
    private EntityManager entityManager;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;
    private final StatsClient statsClient;

    public AdminEventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository, RequestRepository requestRepository, CategoryRepository categoryRepository, StatsClient statsClient) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;
        this.statsClient = statsClient;
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<EventState> states,
                                        List<Long> categories, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Integer from, Integer size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        Predicate criteria = builder.conjunction();

        if (users != null) {
            Predicate inUsers = event.get("initiator").in(users);
            criteria = builder.and(criteria, inUsers);
        }
        if (states != null) {
            Predicate inStates = event.get("state").in(states);
            criteria = builder.and(criteria, inStates);
        }
        if (categories != null) {
            Predicate inCategories = event.get("category").in(categories);
            criteria = builder.and(criteria, inCategories);
        }
        if (rangeStart != null) {
            Predicate start = builder.greaterThan(event.get("eventDate"), rangeStart);
            criteria = builder.and(criteria, start);
        }
        if (rangeEnd != null) {
            Predicate end = builder.lessThan(event.get("eventDate"), rangeEnd);
            criteria = builder.and(criteria, end);
        }

        query.select(event).where(criteria);
        List<Event> events = entityManager.createQuery(query).setFirstResult(from).setMaxResults(size).getResultList();
        List<Long> eventsId = events.stream().map(Event::getId).collect(Collectors.toList());

        Map<Long, Long> stats = statsClient.getStats(eventsId, false, null, null);
        Map<Long, Long> confirmedRequests = requestRepository.getConfirmedRequestsBatch(eventsId);

        List<EventFullDto> result = EventMapper.toEventFullDtoList(events);

        result.forEach(eventShortDto -> {
            eventShortDto.setViews(stats.getOrDefault(eventShortDto.getId(), 0L));
            eventShortDto.setConfirmedRequests(confirmedRequests.getOrDefault(eventShortDto.getId(), 0L));
        });

        return result;
    }

    @Override
    public EventFullDto patchEventById(Long eventId, PatchEventDto patchEventDto) {
        Event oldEvent = eventRepository.findById(eventId).orElseThrow(() -> {
            log.info(String.format("События %d не существует!", eventId));
            throw new EventNotExistsException(String.format("События %d не существует!", eventId));
        });

        if (patchEventDto.getEventDate() != null && patchEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            log.info("Нельзя событию установить дату ранее +1 час от текущего времени!");
            throw new InvalidEventDateException("Нельзя событию установить дату ранее +1 час от текущего времени!");
        }

        if (patchEventDto.getStateAction() != null) {
            if (patchEventDto.getStateAction().equals(EventStateAction.PUBLISH_EVENT)) {
                if (oldEvent.getState().equals(EventState.PENDING)) {
                    oldEvent.setState(EventState.PUBLISHED);
                } else {
                    log.info("Установка некорректного статуса!");
                    throw new InvalidStateActionException("Установка некорректного статуса!");
                }
            } else if (patchEventDto.getStateAction().equals(EventStateAction.REJECT_EVENT)) {
                if (oldEvent.getState().equals(EventState.PENDING)) {
                    oldEvent.setState(EventState.REJECTED);
                } else {
                    log.info("Установка некорректного статуса!");
                    throw new InvalidStateActionException("Установка некорректного статуса!");
                }
            } else {
                log.info("Установка некорректного статуса!");
                throw new InvalidStateActionException("Установка некорректного статуса!");
            }
        }

        if (patchEventDto.getLocation() != null) {
            Location location = locationRepository.findByLatAndLon(
                    patchEventDto.getLocation().getLat(),
                    patchEventDto.getLocation().getLon()).orElseGet(() -> locationRepository.saveAndFlush(patchEventDto.getLocation()));
            oldEvent.setLocation(location);
        }

        Event event = eventRepository.saveAndFlush(EventMapper.patchEventDtoToEntityByAdmin(oldEvent, patchEventDto));

        if (patchEventDto.getCategory() != null) {
            Category category = categoryRepository.findById(patchEventDto.getCategory())
                    .orElseThrow(() -> {
                        log.info(String.format("Категории %d не существует!", patchEventDto.getCategory()));
                        throw new CategoryNotExistsException(String.format("Категории %d не существует!", patchEventDto.getCategory()));
                    });
            event.setCategory(category);
        }
        return EventMapper.toEventFullDto(event);
    }

}
