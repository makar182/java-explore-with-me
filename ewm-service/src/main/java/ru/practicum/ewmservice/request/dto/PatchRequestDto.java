package ru.practicum.ewmservice.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.request.enums.RequestStatus;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class PatchRequestDto {
    private List<Long> requestIds;
    private RequestStatus status;
}
