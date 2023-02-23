package ru.practicum.ewmservice.admin.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.admin.compilation.model.AdminCompilation;

@Repository
public interface AdminCompilationRepository extends JpaRepository<AdminCompilation,Long> {
}
