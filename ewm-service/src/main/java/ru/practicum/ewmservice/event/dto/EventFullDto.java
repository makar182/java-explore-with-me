package ru.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    private String annotation;
    private InnerCategory category;
    private InnerInitiator initiator;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private InnerLocation location;
    private Boolean paid;
    private Long participantLimit;
    private Long confirmedRequests;
    private Boolean requestModeration;
    private String title;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private EventState state;
    private Long views;

    public EventFullDto(Event event) {
        this.id = event.getId();
        this.annotation = event.getAnnotation();
        this.category = new InnerCategory(event.getCategory());
        this.initiator = new InnerInitiator(event.getInitiator());
        this.description = event.getDescription();
        this.eventDate = event.getEventDate();
        this.location = new InnerLocation(event.getLocation());
        this.paid = event.getPaid();
        this.participantLimit = event.getParticipantLimit();
        this.requestModeration = event.getRequestModeration();
        this.title = event.getTitle();
        this.createdOn = event.getCreatedOn();
        this.publishedOn = event.getPublishedOn();
        this.state = event.getState();
    }

    @Getter
    @Setter
    public static class InnerCategory {
        private Long id;
        private String name;

        public InnerCategory(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    @Getter
    @Setter
    public static class InnerInitiator {
        private Long id;
        private String name;

        public InnerInitiator(User user) {
            this.id = user.getId();
            this.name = user.getName();
        }
    }

    @Getter
    @Setter
    public static class InnerLocation {
        private Double lat;
        private Double lon;

        public InnerLocation(Location location) {
            this.lat = location.getLat();
            this.lon = location.getLon();
        }
    }
}
