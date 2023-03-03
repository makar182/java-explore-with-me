package ru.practicum.ewmservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.request.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_Id(Long userId);

    Optional<Request> findByIdAndRequester_Id(Long requestId, Long userId);

    List<Request> findAllByEvent_IdAndRequester_Id(Long eventId, Long userId);

    List<Request> findAllByEvent_IdAndRequester_IdAndIdIn(Long eventId, Long userId, List<Long> ids);
}
