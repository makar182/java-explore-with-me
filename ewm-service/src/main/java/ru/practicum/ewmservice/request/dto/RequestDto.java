package ru.practicum.ewmservice.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.request.enums.RequestStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class RequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private RequestStatus status;
    private LocalDateTime created;
}
