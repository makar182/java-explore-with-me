package ru.practicum.ewmservice.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CompilationDto {
    Long id;
    String title;
    Boolean pinned;
    List<CompilationEvent> events;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class CompilationEvent{
        private final Long id;
        private final String title;
        private final String annotation;
        private final String description;
        private final Boolean paid;
        private final LocalDateTime eventDate;
        private final LocalDateTime createdOn;
        private final LocalDateTime publishedOn;
        private final Long participantLimit;
        private final Boolean requestModeration;
        private final EventState state;
        private final User initiator;
        private final Category category;
        private final Location location;

        public CompilationEvent(Event event) {
            this.id = event.getId();
            this.title = event.getTitle();
            this.annotation = event.getAnnotation();
            this.description = event.getDescription();
            this.paid = event.getPaid();
            this.eventDate = event.getEventDate();
            this.createdOn = event.getCreatedOn();
            this.publishedOn = event.getPublishedOn();
            this.participantLimit = event.getParticipantLimit();
            this.requestModeration = event.getRequestModeration();
            this.state = event.getState();
            this.initiator = event.getInitiator();
            this.category = event.getCategory();
            this.location = event.getLocation();
        }
    }
}
