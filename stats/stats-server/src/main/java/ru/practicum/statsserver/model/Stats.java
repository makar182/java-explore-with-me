package ru.practicum.statsserver.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Stats {
    Long id;
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}
