package ru.practicum.ewmservice.exception;

public class CategoryNotExistsException extends RuntimeException {
    public CategoryNotExistsException(String message) {
        super(message);
    }
}
