package ru.practicum.ewmservice.compilation.model;

import lombok.*;
import ru.practicum.ewmservice.user.model.User;
import ru.practicum.ewmservice.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "compilations")
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "is_pinned")
    private Boolean pinned;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User creator;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "compilation_event",
            joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private Set<Event> events;
}
