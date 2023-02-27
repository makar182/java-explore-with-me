package ru.practicum.ewmservice.category.model;

import lombok.*;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User creator;
}
