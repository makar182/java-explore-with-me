package ru.practicum.ewmservice.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    private final ApiErrorStatus status;
    private final String reason;
    private final String message;
    private final LocalDateTime timestamp;

    public ApiError(ApiErrorStatus status, String reason, String message, LocalDateTime timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }
}
