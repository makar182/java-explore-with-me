package ru.practicum.ewmservice.user.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
    @Override
    void deleteById(Long userId);

    List<User> findAllByIdIn(List<Long> userIds, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
