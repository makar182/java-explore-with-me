package ru.practicum.ewmservice.compilation.dto;

import lombok.*;
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
@NoArgsConstructor
public class CompilationDto {
    Long id;
    String title;
    Boolean pinned;
    List<CompilationEventDto> events;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompilationEventDto {
        private Long id;
        private String title;
        private String annotation;
        private String description;
        private Boolean paid;
        private LocalDateTime eventDate;
        private LocalDateTime createdOn;
        private LocalDateTime publishedOn;
        private Long participantLimit;
        private Boolean requestModeration;
        private EventState state;
        private InnerUser initiator;
        private InnerCategory category;
        private InnerLocation location;

        public CompilationEventDto(Event event) {
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
            this.initiator = new InnerUser(event.getInitiator());
            this.category = new InnerCategory(event.getCategory());
            this.location = new InnerLocation(event.getLocation());
        }

        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class InnerUser {
            private Long id;
            private String name;
            private String email;

            public InnerUser(User user) {
                this.id = user.getId();
                this.name = user.getName();
                this.email = user.getEmail();
            }
        }

        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class InnerCategory {
            private Long id;
            private String name;

            public InnerCategory(Category category) {
                this.id = category.getId();
                this.name = category.getName();
            }
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
