package ru.practicum.ewmservice.subscription.mapper;

import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.subscription.dto.SubscriptionEventDto;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionMapper {
    public static SubscriptionEventDto toDto(Event event) {
        return SubscriptionEventDto.builder()
                .id(event.getId())
                .paid(event.getPaid())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(new SubscriptionEventDto.SubscriptionResponseCategory(event.getCategory()))
                .eventDate(event.getEventDate())
                .initiator(new SubscriptionEventDto.SubscriptionResponseInitiator(event.getInitiator()))
                .build();
    }

    public static List<SubscriptionEventDto> toDtoList(List<Event> events) {
        List<SubscriptionEventDto> result = new ArrayList<>();
        for (Event event : events) {
            result.add(SubscriptionMapper.toDto(event));
        }
        return result;
    }
}
