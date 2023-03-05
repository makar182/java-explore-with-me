package ru.practicum.ewmservice.request.model;

import lombok.*;
import ru.practicum.ewmservice.request.enums.RequestStatus;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.event.model.Event;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User requester;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
