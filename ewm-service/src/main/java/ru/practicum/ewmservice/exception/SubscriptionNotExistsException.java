package ru.practicum.ewmservice.exception;

public class SubscriptionNotExistsException extends RuntimeException {
    public SubscriptionNotExistsException(String message) {
        super(message);
    }
}
