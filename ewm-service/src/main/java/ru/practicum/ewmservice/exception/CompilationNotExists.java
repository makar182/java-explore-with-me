package ru.practicum.ewmservice.exception;

public class CompilationNotExists extends RuntimeException{
    public CompilationNotExists(String message) {
        super(message);
    }
}
