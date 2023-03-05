package ru.practicum.ewmservice.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.enums.EventSortType;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.EventNotExists;
import ru.practicum.ewmservice.request.repository.RequestRepository;
import ru.practicum.statsclient.StatsClient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final StatsClient statsClient;
    @PersistenceContext
    private EntityManager entityManager;

    public EventServiceImpl(EventRepository eventRepository, RequestRepository requestRepository, StatsClient statsClient) {
        this.eventRepository = eventRepository;
        this.requestRepository = requestRepository;
        this.statsClient = statsClient;
    }

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categoryIds, String paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, String onlyAvailable,
                                         EventSortType sort, int size, int from) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        Predicate criteria = builder.conjunction();
        if (!text.isEmpty()) {
            Predicate annotation = builder.like(builder.lower(event.get("annotation")),
                    "%" + text.toLowerCase() + "%");
            Predicate description = builder.like(builder.lower(event.get("description")),
                    "%" + text.toLowerCase() + "%");
            Predicate hasText = builder.or(annotation, description);
            criteria = builder.and(criteria, hasText);
        }
        if (categoryIds != null) {
            Predicate inCategories = event.get("category").in(categoryIds);
            criteria = builder.and(criteria, inCategories);
        }
        if (paid != null) {
            boolean parsePaid = Boolean.parseBoolean(paid);
            Predicate paidStat = event.get("paid").in(parsePaid);
            criteria = builder.and(criteria, paidStat);
        }
        if (rangeEnd != null) {
            //LocalDateTime rangeEndTime = LocalDateTime.parse(rangeEnd, dateTimeFormatter);
            Predicate end = builder.lessThanOrEqualTo(event.get("eventDate"), rangeEnd);
            criteria = builder.and(criteria, end);
        }
        if (rangeStart != null) {
            //LocalDateTime rangeStartTime = LocalDateTime.parse(rangeStart, dateTimeFormatter);
            Predicate start = builder.greaterThanOrEqualTo(event.get("eventDate"), rangeStart);
            criteria = builder.and(criteria, start);
        }

        Predicate statsEvent = event.get("state").in(EventState.PUBLISHED);
        criteria = builder.and(criteria, statsEvent);
        if (sort != null && sort.equals(EventSortType.EVENT_DATE)) {
            query.select(event).where(criteria).orderBy(builder.desc(event.get("eventDate")));
        } else {
            query.select(event).where(criteria);
        }

        List<EventShortDto> result = EventMapper.toEventShortDtoList(entityManager.createQuery(query)
                .setFirstResult(from).setMaxResults(size).getResultList());

        List<Long> eventIds = result.stream()
                .map(EventShortDto::getId).collect(Collectors.toList());

        Map<Long, Long> stats = statsClient.getStats(eventIds, false);
        Map<Long, Long> confirmedRequests = requestRepository.getConfirmedRequestsBatch(eventIds);

        result.forEach(eventShortDto -> {
            eventShortDto.setViews(stats.getOrDefault(eventShortDto.getId(), 0L));
            eventShortDto.setConfirmedRequests(confirmedRequests.getOrDefault(eventShortDto.getId(), 0L));
        });

//        if (sort != null && sort.equals(EventSortType.VIEWS)) {
//            Comparator.comparing(result.)
//        }

        return result;
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(()->{
            log.info(String.format("События %d не существует!", eventId));
            throw new EventNotExists(String.format("События %d не существует!", eventId));
        });

        if(!event.getState().equals(EventState.PUBLISHED)) {
            log.info(String.format("События %d не существует!", eventId));
            throw new EventNotExists(String.format("События %d не существует!", eventId));
        }

        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);

        Map<Long, Long> stats = statsClient.getStats(List.of(eventFullDto.getId()), false);
        Map<Long, Long> confirmedRequests = requestRepository.getConfirmedRequestsBatch(List.of(eventFullDto.getId()));

        eventFullDto.setViews(stats.getOrDefault(eventFullDto.getId(), 0L));
        eventFullDto.setConfirmedRequests(confirmedRequests.getOrDefault(eventFullDto.getId(), 0L));

        return eventFullDto;
    }
}
