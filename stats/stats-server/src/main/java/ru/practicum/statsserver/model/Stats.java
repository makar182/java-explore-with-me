package ru.practicum.statsserver.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
