package ru.practicum.ewmservice.admin.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.category.model.Category;

@Repository
public interface AdminCategoryRepository extends JpaRepository<Category, Long> {
}
