package ru.practicum.ewmservice.subscription.service;

import org.springframework.lang.Nullable;
import ru.practicum.ewmservice.subscription.dto.SubscriptionEventDto;
import ru.practicum.ewmservice.user.dto.UserDto;

import java.util.List;

public interface SubscriptionService {
    void addSubscription(Long followerId, List<Long> followedIds);

    void deleteSubscription(Long followerId, List<Long> followedIds);

    List<UserDto> getFollowersByUserId(Long userId);

    List<UserDto> getFollowedByUserId(Long userId);

    List<SubscriptionEventDto> getEventsFromSubscriptions(Long userId, @Nullable List<Long> subscriptionsId);
}
