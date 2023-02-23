package ru.practicum.ewmservice.user.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.user.event.model.UserEvent;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
}
