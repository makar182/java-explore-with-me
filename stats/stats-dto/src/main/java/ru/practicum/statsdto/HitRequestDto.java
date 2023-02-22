package ru.practicum.statsdto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class HitRequestDto {
    @NotBlank
    String app;
    @NotBlank
    String uri;
    @NotBlank
    String ip;
    @NotBlank
    String timestamp;
}