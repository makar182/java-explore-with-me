package ru.practicum.statsserver.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatsSummary {
    String app;
    String uri;
    Long hits;
}
