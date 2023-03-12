package ru.practicum.ewmservice.event.mapper;

import ru.practicum.ewmservice.event.dto.LocationDto;
import ru.practicum.ewmservice.event.model.Location;

public class LocationMapper {
    public static Location toEntity(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public static LocationDto toDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
