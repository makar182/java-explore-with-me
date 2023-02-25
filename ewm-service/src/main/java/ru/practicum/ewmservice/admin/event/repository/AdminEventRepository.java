package ru.practicum.ewmservice.admin.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.event.model.Event;

@Repository
public interface AdminEventRepository extends JpaRepository<Event, Long> {
}
