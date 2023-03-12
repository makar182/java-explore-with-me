package ru.practicum.ewmservice.exception;

public class EventNotExistsException extends RuntimeException {
    public EventNotExistsException(String message) {
        super(message);
    }
}
