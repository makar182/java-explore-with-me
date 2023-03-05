package ru.practicum.ewmservice.exception;

public class EventNotExists extends RuntimeException{
    public EventNotExists(String message) {
        super(message);
    }
}
