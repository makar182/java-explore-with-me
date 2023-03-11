package ru.practicum.ewmservice.request.mapper;

import ru.practicum.ewmservice.event.dto.UpdateRequestStatusResultDto;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.request.dto.RequestDto;
import ru.practicum.ewmservice.request.model.Request;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static RequestDto toDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .created(request.getCreated())
                .status(request.getStatus())
                .build();
    }

    public static List<RequestDto> toDtoList(List<Request> requests) {
        List<RequestDto> result = new ArrayList<>();
        for (Request request : requests) {
            result.add(RequestMapper.toDto(request));
        }
        return result;
    }

    public static UpdateRequestStatusResultDto toUpdateRequestStatusResultDto(List<Request> confirmedRequests,
                                                                              List<Request> rejectedRequests) {

        List<UpdateRequestStatusResultDto.InnerRequest> confirmedOnes = new ArrayList<>();
        List<UpdateRequestStatusResultDto.InnerRequest> rejectedOnes = new ArrayList<>();

        for (Request confirmedRequest : confirmedRequests) {
            confirmedOnes.add(new UpdateRequestStatusResultDto.InnerRequest(confirmedRequest));
        }

        for (Request rejectedRequest : rejectedRequests) {
            rejectedOnes.add(new UpdateRequestStatusResultDto.InnerRequest(rejectedRequest));
        }

        return UpdateRequestStatusResultDto.builder()
                .confirmedRequests(confirmedOnes)
                .rejectedRequests(rejectedOnes)
                .build();
    }

    public static Request toEntity(Long userId, Long eventId) {
        return Request.builder()
                .event(Event.builder().id(eventId).build())
                .requester(User.builder().id(userId).build())
                .created(LocalDateTime.now())
                .build();
    }
}
