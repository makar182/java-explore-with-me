package ru.practicum.ewmservice.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.subscription.model.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByFollower_IdAndFollowedIdIn(Long followerId, List<Long> followedIds);

    List<Subscription> findAllByFollower_Id(Long followerId);

    List<Subscription> findAllByFollowed_Id(Long followedId);
}
