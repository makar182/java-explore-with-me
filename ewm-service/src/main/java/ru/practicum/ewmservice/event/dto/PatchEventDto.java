package ru.practicum.ewmservice.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.event.enums.EventStateAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class PatchEventDto {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
    private EventStateAction stateAction;
}
