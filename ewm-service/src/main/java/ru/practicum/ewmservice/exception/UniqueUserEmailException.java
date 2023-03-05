package ru.practicum.ewmservice.exception;

public class UniqueUserEmailException extends RuntimeException{
    public UniqueUserEmailException(String message) {
        super(message);
    }
}
