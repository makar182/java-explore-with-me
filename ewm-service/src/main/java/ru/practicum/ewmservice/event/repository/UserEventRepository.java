package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiator_Id(Long userId);

    Optional<Event> findByIdAndInitiator_Id(Long eventId, Long userId);
}
