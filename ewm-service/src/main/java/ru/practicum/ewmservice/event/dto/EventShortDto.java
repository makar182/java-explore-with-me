package ru.practicum.ewmservice.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class EventShortDto {
    private Long id;
    private String annotation;
    private UserEventResponseCategory category;
    private Long confirmedRequests;
    private LocalDateTime eventDate;
    private UserEventResponseInitiator initiator;
    private Boolean paid;
    private String title;
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
