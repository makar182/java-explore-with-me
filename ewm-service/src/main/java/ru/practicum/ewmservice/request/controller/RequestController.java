package ru.practicum.ewmservice.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.request.dto.RequestDto;
import ru.practicum.ewmservice.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @GetMapping
    public List<RequestDto> getRequestsByUserId(@PathVariable("userId") Long userId) {
        log.info("Выполнен запрос /users/{}/requests", userId);
        return requestService.getRequestsByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto addRequest(@PathVariable("userId") Long userId,
                                 @RequestParam(name = "eventId") Long eventId) {
        log.info("Выполнен запрос POST /users/{}/requests и параметром eventId = {}", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable("userId") Long userId,
                                   @PathVariable("requestId") Long requestId) {
        log.info("Выполнен запрос DELETE /users/{}/requests/{}/cancel", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }
}
