package ru.practicum.ewmservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.request.enums.RequestStatus;
import ru.practicum.ewmservice.request.model.Request;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester_Id(Long userId);

    Optional<Request> findByIdAndRequester_Id(Long requestId, Long userId);

    List<Request> findAllByEvent_IdAndEvent_Initiator_IdAndIdIn(Long eventId, Long userId, List<Long> ids);

    List<Request> findAllByEvent_IdAndStatus(Long eventId, RequestStatus status);

    List<Request> findAllByEvent_IdAndEvent_Initiator_Id(Long eventId, Long userId);

    boolean existsByEvent_IdAndRequester_Id(Long eventId, Long userId);

    @Query("SELECT r.event.id, count(r.id) as cnt FROM Request r" +
            " WHERE r.event.id in ?1 AND r.status = 'CONFIRMED' GROUP BY r.event.id")
    Map<Long, Long> getConfirmedRequestsBatch(List<Long> eventsId);
}
