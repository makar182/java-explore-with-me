package ru.practicum.ewmservice.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.dto.PatchEventDto;
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
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.event.repository.LocationRepository;

@Service
public class AdminEventServiceImpl implements AdminEventService{
    @PersistenceContext
    private EntityManager entityManager;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public AdminEventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> getEvents(List<Long> users, List<EventState> states,
                                          List<Long> categories, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Integer from, Integer size)

    {
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
        return entityManager.createQuery(query).setFirstResult(from).setMaxResults(size).getResultList();
    }

    @Override
    public Event patchEventById(Long eventId, PatchEventDto patchEventDto) {
        Event oldEvent = eventRepository.findById(eventId).orElseThrow(() -> {
            throw new RuntimeException();
        });

        Location location = locationRepository.findByLatAndLon(
                patchEventDto.getLocation().getLat(),
                patchEventDto.getLocation().getLon()).orElseGet(() -> locationRepository.saveAndFlush(patchEventDto.getLocation()));

        Event event = EventMapper.patchEventDtoToEntityByAdmin(oldEvent, patchEventDto);
        event.setLocation(location);

        return eventRepository.saveAndFlush(event);
    }

}
