package ru.practicum.ewmservice.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.enums.EventState;
import ru.practicum.ewmservice.event.model.Location;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class EventFullDto {
    private Long id;
    private String annotation;
    private UserEventResponseCategory category;
    private UserEventResponseInitiator initiator;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Long confirmedRequests;
    private Boolean requestModeration;
    private String title;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private EventState state;
    private Long views;

    @Getter
    @Setter
    public static class UserEventResponseCategory{
        private Long id;
        private String name;

        public UserEventResponseCategory(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    @Getter
    @Setter
    public static class UserEventResponseInitiator{
        private Long id;
        private String name;

        public UserEventResponseInitiator(User user) {
            this.id = user.getId();
            this.name = user.getName();
        }
    }
}
