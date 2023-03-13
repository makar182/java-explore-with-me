package ru.practicum.ewmservice.subscription.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.subscription.dto.SubscriptionEventDto;
import ru.practicum.ewmservice.subscription.service.SubscriptionService;
import ru.practicum.ewmservice.user.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping("/users/{followerId}/subscription")
@Slf4j
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping("/{followedId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubscription(@PathVariable("followerId") Long followerId,
                                @PathVariable("followedId") Long followedId) {
        log.info("Выполнен запрос POST /users/{}/subscription с данными followedIds = {}", followerId, followedId);
        service.addSubscription(followerId, List.of(followedId));
    }

    @DeleteMapping("/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable("followerId") Long followerId,
                                   @PathVariable("followedId") Long followedId) {
        log.info("Выполнен запрос DELETE /users/{}/subscription с данными followedIds = {}", followerId, followedId);
        service.deleteSubscription(followerId, List.of(followedId));
    }

    @GetMapping
    public List<SubscriptionEventDto> getEventsFromSubscriptions(@PathVariable("followerId") Long userId,
                                                                 @RequestParam(name = "subscriptionIds",
                                                                  required = false) List<Long> subscriptionIds) {
        log.info("Выполнен запрос GET /users/{}/subscription с subscriptionIds = {}", userId, subscriptionIds);
        return service.getEventsFromSubscriptions(userId, subscriptionIds);
    }

    @GetMapping("/followers")
    public List<UserDto> getFollowersByUserId(@PathVariable("followerId") Long userId) {
        log.info("Выполнен запрос GET /users/{}/subscription/followers", userId);
        return service.getFollowersByUserId(userId);
    }

    @GetMapping("/followed")
    public List<UserDto> getFollowedByUserId(@PathVariable("followerId") Long userId) {
        log.info("Выполнен запрос GET /users/{}/subscription/followed", userId);
        return service.getFollowedByUserId(userId);
    }
}
