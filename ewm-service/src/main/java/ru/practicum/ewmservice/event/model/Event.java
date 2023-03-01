package ru.practicum.ewmservice.event.model;

import lombok.*;
import ru.practicum.ewmservice.event.EventState;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.compilation.model.Compilation;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "annotation")
    private String annotation;
    @Column(name = "description")
    private String description;
    @Column(name = "is_paid")
    private Boolean paid;
    @Column(name = "views")
    private Long views;
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "participant_limit")
    private Long participantLimit;
    @Column(name = "is_request_moderation")
    private Boolean requestModeration;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EventState state;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User initiator;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @ManyToMany(mappedBy = "events")
    private Set<Compilation> compilations;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
}
