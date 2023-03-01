package ru.practicum.ewmservice.event.mapper;

import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.dto.EventShortDto;
import ru.practicum.ewmservice.event.dto.NewEventDto;
import ru.practicum.ewmservice.event.dto.EventFullDto;
import ru.practicum.ewmservice.event.dto.PatchEventDto;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
//    private final CategoryRepository categoryRepository;
//
//    public UserEventMapper(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

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
                .category(new EventFullDto.UserEventResponseCategory(event.getCategory()))
                .location(event.getLocation())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .initiator(new EventFullDto.UserEventResponseInitiator(event.getInitiator()))
                .publishedOn(event.getPublishedOn())
                .state(event.getState())
                .views(event.getViews())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .paid(event.getPaid())
                .eventDate(event.getEventDate())
                .category(new EventShortDto.UserEventResponseCategory(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(new EventShortDto.UserEventResponseInitiator(event.getInitiator()))
                .views(event.getViews())
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
                .location(newEventDto.getLocation())
                .build();
    }

    public static Event patchEventDtoToEntity(PatchEventDto patchEventDto) {
        return Event.builder()
                .title(patchEventDto.getTitle())
                .annotation(patchEventDto.getAnnotation())
                .description(patchEventDto.getDescription())
                .paid(patchEventDto.getPaid())
                .eventDate(patchEventDto.getEventDate())
                .participantLimit(patchEventDto.getParticipantLimit())
                .requestModeration(patchEventDto.getRequestModeration())
                .category(Category.builder().id(patchEventDto.getCategory()).build())
                .location(patchEventDto.getLocation())
                .build();
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
