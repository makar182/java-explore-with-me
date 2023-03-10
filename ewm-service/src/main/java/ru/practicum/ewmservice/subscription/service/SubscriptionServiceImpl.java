package ru.practicum.ewmservice.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.model.Event;
import ru.practicum.ewmservice.event.repository.EventRepository;
import ru.practicum.ewmservice.exception.SubscriptionAlreadyExistsException;
import ru.practicum.ewmservice.exception.SubscriptionNotExistsException;
import ru.practicum.ewmservice.subscription.dto.SubscriptionEventDto;
import ru.practicum.ewmservice.subscription.mapper.SubscriptionMapper;
import ru.practicum.ewmservice.subscription.model.Subscription;
import ru.practicum.ewmservice.subscription.repository.SubscriptionRepository;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.mapper.UserMapper;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    @Override
    public void addSubscription(Long followerId, List<Long> followedIds) {

        List<Subscription> savedSubscriptions = subscriptionRepository.findAllByFollower_IdAndFollowedIdIn(followerId, followedIds);
        if(!savedSubscriptions.isEmpty()) {
            log.info("Попытка создания дубликата подписки с данными followerId = {} и followedIds = {}", followerId, followedIds);
            throw new SubscriptionAlreadyExistsException("Попытка создания дубликата подписки");
        }
        List<Subscription> subscriptions = new ArrayList<>();
        for (Long followedId : followedIds) {
            subscriptions.add(new Subscription(null, User.builder().id(followerId).build(),
                    User.builder().id(followedId).build()));
        }
        subscriptionRepository.saveAllAndFlush(subscriptions);
    }

    @Override
    public void deleteSubscription(Long followerId, List<Long> followedIds) {
        List<Subscription> saved = subscriptionRepository.findAllByFollower_IdAndFollowedIdIn(followerId, followedIds);
        if (saved.size() == followedIds.size()) {
            List<Long> savedIds = saved.stream().map(Subscription::getId).collect(Collectors.toList());
            subscriptionRepository.deleteAllById(savedIds);
        } else {
            log.info("Попытка удалить несуществующие подписки с данными followerId = {} и followedIds = {}", followerId, followedIds);
            throw new SubscriptionNotExistsException("Попытка удалить несуществующие подписки");
        }
    }

    @Override
    public List<UserDto> getFollowersByUserId(Long userId) {
        List<Long> users = subscriptionRepository.findAllByFollowed_Id(userId).stream()
                .map(Subscription::getFollower)
                .map(User::getId)
                .collect(Collectors.toList());
        return UserMapper.toDtoList(userRepository.findAllById(users));
    }

    @Override
    public List<UserDto> getFollowedByUserId(Long userId) {
        List<Long> users = subscriptionRepository.findAllByFollower_Id(userId).stream()
                .map(Subscription::getFollowed)
                .map(User::getId)
                .collect(Collectors.toList());
        return UserMapper.toDtoList(userRepository.findAllById(users));
    }

    @Override
    public List<SubscriptionEventDto> getEventsFromSubscriptions(Long userId, @Nullable List<Long> subscriptionIds) {
        List<Long> followedIds;
        if (subscriptionIds != null && !subscriptionIds.isEmpty()) {
            followedIds = subscriptionRepository.findAllByFollower_Id(userId).stream()
                    .filter(x -> subscriptionIds.contains(x.getId()))
                    .map(Subscription::getFollowed)
                    .map(User::getId)
                    .collect(Collectors.toList());
        } else {
            followedIds = subscriptionRepository.findAllByFollower_Id(userId).stream()
                    .map(Subscription::getFollowed)
                    .map(User::getId)
                    .collect(Collectors.toList());
        }
        List<Event> events = eventRepository.findAllByInitiator_Ids(followedIds).stream()
                .sorted(Comparator.comparing(Event::getEventDate).reversed())
                .collect(Collectors.toList());
        return SubscriptionMapper.toDtoList(events);
    }
}
