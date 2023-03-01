package ru.practicum.ewmservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.event.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEventRepository extends JpaRepository<Event, Long> {
    //Подумать как оптимизировать чтобы не запрашивал по отдельности event, category и location
    //@Query(value = "SELECT e c l FROM Event e JOIN Category c ON e JOIN Location l ON e")
    List<Event> findAllByInitiator_Id(Long userId);

    Optional<Event> findByIdAndInitiator_Id(Long eventId, Long userId);
}
