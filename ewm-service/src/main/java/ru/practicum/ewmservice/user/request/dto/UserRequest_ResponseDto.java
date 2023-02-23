package ru.practicum.ewmservice.user.request.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.dto.EventResponseDto;
import ru.practicum.ewmservice.event.service.EventService;
import ru.practicum.ewmservice.user.request.service.UserRequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@Slf4j
public class UserRequest_ResponseDto {
    private final UserRequestService userRequestService;

    public UserRequest_ResponseDto(UserRequestService userRequestService) {
        this.userRequestService = userRequestService;
    }

    @GetMapping
    public List<UserRequest_ResponseDto> getUserRequests(@PathVariable("userId") Long userId) {
        log.info("");
        return userRequestService.getUserRequests();
    }

    @PostMapping
    public UserRequest_ResponseDto addUserRequest(@PathVariable("userId") Long userId) {
        log.info("");
        return userRequestService.addUserRequest();
    }

    @PatchMapping("/{requestId}/cancel")
    public UserRequest_ResponseDto cancelUserRequest(@PathVariable("userId") Long userId,
                                                     @PathVariable("requestId") Long requestId) {
        log.info("");
        return userRequestService.cancelUserRequest();
    }
}
