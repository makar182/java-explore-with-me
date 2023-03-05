package ru.practicum.ewmservice.exception;

public class UserNotExists extends RuntimeException{
    public UserNotExists(String message) {
        super(message);
    }
}
