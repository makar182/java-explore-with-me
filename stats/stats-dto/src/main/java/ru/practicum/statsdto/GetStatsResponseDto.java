package ru.practicum.statsdto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStatsResponseDto {
    String app;
    String uri;
    Long hits;
}