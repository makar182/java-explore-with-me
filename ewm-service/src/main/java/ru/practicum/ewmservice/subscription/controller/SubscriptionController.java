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
@RequestMapping("/users/{userId}/subscription")
@Slf4j
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubscription(@PathVariable("userId") Long userId,
                                @RequestParam(name = "followed") List<Long> followedIds) {
        log.info("Выполнен запрос POST /users/{}/subscription с данными followedIds = {}", userId, followedIds);
        service.addSubscription(userId, followedIds);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable("userId") Long userId,
                                   @RequestParam(name = "followed") List<Long> followedIds) {
        log.info("Выполнен запрос DELETE /users/{}/subscription с данными followedIds = {}", userId, followedIds);
        service.deleteSubscription(userId, followedIds);
    }

    @GetMapping
    public List<SubscriptionEventDto> getEventsFromSubscriptions(@PathVariable("userId") Long userId,
                                                                 @RequestParam(name = "subscriptionIds",
                                                                  required = false) List<Long> subscriptionIds) {
        log.info("Выполнен запрос GET /users/{}/subscription с subscriptionIds = {}", userId, subscriptionIds);
        return service.getEventsFromSubscriptions(userId, subscriptionIds);
    }

    @GetMapping("/followers")
    public List<UserDto> getFollowersByUserId(@PathVariable("userId") Long userId) {
        log.info("Выполнен запрос GET /users/{}/subscription/followers", userId);
        return service.getFollowersByUserId(userId);
    }

    @GetMapping("/followed")
    public List<UserDto> getFollowedByUserId(@PathVariable("userId") Long userId) {
        log.info("Выполнен запрос GET /users/{}/subscription/followed", userId);
        return service.getFollowedByUserId(userId);
    }
}
