package ru.practicum.ewmservice.admin.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.user.model.User;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
}
