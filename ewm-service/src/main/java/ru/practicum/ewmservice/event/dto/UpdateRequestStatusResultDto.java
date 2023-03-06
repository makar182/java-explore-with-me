package ru.practicum.ewmservice.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.request.dto.RequestDto;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UpdateRequestStatusResultDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;

    public UpdateRequestStatusResultDto(List<RequestDto> confirmedRequests, List<RequestDto> rejectedRequests) {
        this.confirmedRequests = confirmedRequests;
        this.rejectedRequests = rejectedRequests;
    }
}
