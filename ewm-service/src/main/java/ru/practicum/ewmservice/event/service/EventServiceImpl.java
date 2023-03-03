package ru.practicum.ewmservice.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.enums.EventSortType;
import ru.practicum.ewmservice.event.mapper.EventMapper;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
//        if (onlyAvailable != null) {
//            //boolean parsePaid = paid;
//            Predicate onlyAvailableStat = event.get("onlyAvailable").in(onlyAvailable);
//            criteria = builder.and(onlyAvailableStat);
//        }

        //Predicate startPage = builder.greaterThanOrEqualTo(event.get("id"), from);
        Predicate statsEvent = event.get("state").in(EventState.PUBLISHED);
        //criteria = builder.and(criteria, startPage);
        criteria = builder.and(criteria, statsEvent);
        if (sort != null && sort.equals(EventSortType.EVENT_DATE)) {
            query.select(event).where(criteria).orderBy(builder.desc(event.get("eventDate")));
        } else {
            query.select(event).where(criteria);
        }
        return EventMapper.toEventShortDtoList(entityManager.createQuery(query)
                .setFirstResult(from).setMaxResults(size).getResultList());
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);

        return event.map(EventMapper::toEventFullDto).orElse(null);
    }
}
