package ru.practicum.ewmservice.exception;

public class InvalidEventStateActionException extends RuntimeException {
    public InvalidEventStateActionException(String message) {
        super(message);
    }
}
