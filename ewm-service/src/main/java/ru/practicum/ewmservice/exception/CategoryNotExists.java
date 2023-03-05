package ru.practicum.ewmservice.exception;

public class CategoryNotExists extends RuntimeException{
    public CategoryNotExists(String message) {
        super(message);
    }
}
