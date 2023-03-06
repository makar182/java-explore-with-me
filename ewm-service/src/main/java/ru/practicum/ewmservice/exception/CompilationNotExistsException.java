package ru.practicum.ewmservice.exception;

public class CompilationNotExistsException extends RuntimeException {
    public CompilationNotExistsException(String message) {
        super(message);
    }
}
