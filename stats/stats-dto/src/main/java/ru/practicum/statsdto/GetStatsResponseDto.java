package ru.practicum.statsdto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetStatsResponseDto {
    String app;
    String uri;
    Long hits;
}