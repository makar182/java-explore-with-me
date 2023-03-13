package ru.practicum.ewmservice.subscription.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SubscriptionEventDto {
    private Long id;
    private String annotation;
    private SubscriptionResponseCategory category;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private SubscriptionResponseInitiator initiator;
    private Boolean paid;
    private String title;

    @Getter
    @Setter
    public static class SubscriptionResponseCategory {
        private Long id;
        private String name;

        public SubscriptionResponseCategory(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    @Getter
    @Setter
    public static class SubscriptionResponseInitiator {
        private Long id;
        private String name;

        public SubscriptionResponseInitiator(User user) {
            this.id = user.getId();
            this.name = user.getName();
        }
    }
}
