package ru.practicum.ewmservice.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
public class UserResponseDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
}
