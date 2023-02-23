package ru.practicum.ewmservice.admin.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.admin.category.model.AdminCategory;

@Repository
public interface AdminCategoryRepository extends JpaRepository<AdminCategory, Long> {
}
