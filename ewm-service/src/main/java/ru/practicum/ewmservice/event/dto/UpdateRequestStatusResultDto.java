package ru.practicum.ewmservice.event.dto;

import lombok.*;
import ru.practicum.ewmservice.request.enums.RequestStatus;
import ru.practicum.ewmservice.request.model.Request;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class UpdateRequestStatusResultDto {
    List<InnerRequestDto> confirmedRequests;
    List<InnerRequestDto> rejectedRequests;

    @Getter
    @Setter
    public static class InnerRequestDto{
        private final Long id;
        private final Long event;
        private final Long requester;
        private final RequestStatus status;
        private final LocalDateTime created;

        public InnerRequestDto(Request request) {
            this.id = request.getId();
            this.event = request.getEvent().getId();
            this.requester = request.getRequester().getId();
            this.status = request.getStatus();
            this.created = request.getCreated();
        }
    }
}
