package ru.practicum.ewmservice.user.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.user.request.model.UserRequest;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {
}
