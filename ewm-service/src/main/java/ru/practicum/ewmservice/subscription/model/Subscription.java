package ru.practicum.ewmservice.subscription.model;

import lombok.*;
import ru.practicum.ewmservice.user.model.User;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    private User follower;
    @ManyToOne
    @JoinColumn(name = "followed_id", referencedColumnName = "id")
    private User followed;
}
