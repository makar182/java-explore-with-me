package ru.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewmservice.event.model.Location;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@ToString
public class NewEventDto {
    private String annotation;
    private Long category;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
    private Location location;

//    static class UserEventRequestCategory{
//        private Long id;
//        private String name;
//
//        public UserEventRequestCategory(Long id, String name) {
//            this.id = id;
//            this.name = name;
//        }
//    }
}
