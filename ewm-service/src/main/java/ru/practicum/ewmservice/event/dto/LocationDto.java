package ru.practicum.ewmservice.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationDto {
    private Double lat;
    private Double lon;
}
