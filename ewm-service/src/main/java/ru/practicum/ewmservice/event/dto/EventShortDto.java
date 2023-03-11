package ru.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    private Long id;
    private String annotation;
    private InnerCategory category;
    private Long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private InnerInitiator initiator;
    private Boolean paid;
    private String title;
    private Long views;

    public EventShortDto(Event event) {
        this.id = event.getId();
        this.annotation = event.getAnnotation();
        this.category = new InnerCategory(event.getCategory());
        this.eventDate = event.getEventDate();
        this.initiator = new InnerInitiator(event.getInitiator());
        this.paid = event.getPaid();
        this.title = event.getTitle();
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
}
