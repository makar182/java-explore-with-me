package ru.practicum.ewmservice.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
public class UserRequestDto {
    @NotBlank
    private String name;
    @Email
    private String email;
}
