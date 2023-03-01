package ru.practicum.ewmservice.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.request.dto.RequestDto;
import ru.practicum.ewmservice.request.service.RequestService;
import ru.practicum.statsclient.StatsClient;
import ru.practicum.statsdto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;
    private final StatsClient statsClient;
    private final String app = "ewm-service";

    @GetMapping
    public List<RequestDto> getRequestsByUserId(@PathVariable("userId") Long userId,
                                                HttpServletRequest request) {
        //log.info(String.format("Выполнен запрос GET /admin/users с данными ids = %s, from = %d и size = %d", ids, from, size));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return requestService.getRequestsByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto addRequest(@PathVariable("userId") Long userId,
                                 @RequestParam(name = "eventId") Long eventId,
                                 HttpServletRequest request) {
        //log.info(String.format("Выполнен запрос POST /admin/users с данными %s", user));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable("userId") Long userId,
                                   @PathVariable("requestId") Long requestId,
                                   HttpServletRequest request) {
        //log.info(String.format("Выполнен запрос DELETE /admin/users/{userId} с данными %d", userId));
        statsClient.hit(new HitRequestDto(app, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().toString()));
        return requestService.cancelRequest(userId, requestId);
    }
}
