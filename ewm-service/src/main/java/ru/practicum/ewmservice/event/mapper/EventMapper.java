package ru.practicum.ewmservice.event.mapper;

import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.enums.EventStateAction;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.PatchEventDto;
import ru.practicum.ewmservice.exception.InvalidEventStateActionException;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .paid(event.getPaid())
                .eventDate(event.getEventDate())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .category(new EventFullDto.InnerCategory(event.getCategory()))
                .location(new EventFullDto.InnerLocation(event.getLocation()))
                .createdOn(event.getCreatedOn())
                .initiator(new EventFullDto.InnerInitiator(event.getInitiator()))
                .publishedOn(event.getPublishedOn())
                .state(event.getState())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .paid(event.getPaid())
                .eventDate(event.getEventDate())
                .category(new EventShortDto.InnerCategory(event.getCategory()))
                .initiator(new EventShortDto.InnerInitiator(event.getInitiator()))
                .build();
    }

    public static Event newEventDtoToEntity(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .paid(newEventDto.getPaid())
                .eventDate(newEventDto.getEventDate())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .category(Category.builder().id(newEventDto.getCategory()).build())
                .location(LocationMapper.toEntity(newEventDto.getLocation()))
                .build();
    }

    public static Event patchEventDtoToEntityByAdmin(Event event, PatchEventDto patchEventDto) {
        if (patchEventDto.getAnnotation() != null) {
            event.setAnnotation(patchEventDto.getAnnotation());
        }
        if (patchEventDto.getTitle() != null) {
            event.setTitle(patchEventDto.getTitle());
        }
        if (patchEventDto.getDescription() != null) {
            event.setDescription(patchEventDto.getDescription());
        }
        if (patchEventDto.getPaid() != null) {
            event.setPaid(patchEventDto.getPaid());
        }
        if (patchEventDto.getEventDate() != null) {
            event.setEventDate(patchEventDto.getEventDate());
        }
        if (patchEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(patchEventDto.getParticipantLimit());
        }
        if (patchEventDto.getRequestModeration() != null) {
            event.setRequestModeration(patchEventDto.getRequestModeration());
        }
        if (patchEventDto.getCategory() != null) {
            event.setCategory(Category.builder().id(patchEventDto.getCategory()).build());
        }
        try {
            EventStateAction state = patchEventDto.getStateAction();
            if (state.equals(EventStateAction.PUBLISH_EVENT)) {
                event.setState(EventState.PUBLISHED);
            } else if (state.equals(EventStateAction.REJECT_EVENT)) {
                event.setState(EventState.REJECTED);
            } else {
                throw new InvalidEventStateActionException("Указан некорректный статус!");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidEventStateActionException("Указан некорректный статус!");
        }
        return event;
    }

    public static List<EventFullDto> toEventFullDtoList(List<Event> events) {
        List<EventFullDto> result = new ArrayList<>();
        for (Event event : events) {
            result.add(EventMapper.toEventFullDto(event));
        }
        return result;
    }

    public static List<EventShortDto> toEventShortDtoList(List<Event> events) {
        List<EventShortDto> result = new ArrayList<>();
        for (Event event : events) {
            result.add(EventMapper.toEventShortDto(event));
        }
        return result;
    }
}
