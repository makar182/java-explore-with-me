package ru.practicum.ewmservice.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmservice.exception.*;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ApiErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUserNotExists(final UserNotExistsException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.NOT_FOUND, "Пользователь не существует", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCategoryNotExists(final CategoryNotExistsException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.NOT_FOUND, "Категория не существует", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEventNotExists(final EventNotExistsException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.NOT_FOUND, "Событие не существует", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCompilationNotExists(final CompilationNotExistsException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.NOT_FOUND, "Подборка не существует", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInvalidEventDateException(final InvalidEventDateException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.CONFLICT, "Некорректная дата события", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEmptyResultDataAccessException(final EmptyResultDataAccessException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.NOT_FOUND, "Ошибка выборки из БД", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.CONFLICT, e.getCause().getMessage(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.BAD_REQUEST, "Некорректные атрибуты", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidEventStateActionException(final InvalidEventStateActionException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.BAD_REQUEST, "Некорректный статус", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInvalidStateActionException(final InvalidStateActionException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.CONFLICT, "Некорректное действие stateAction!", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleRequestException(final RequestException e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.CONFLICT, "Ошибка работы с заявкой", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleThrowable(final Throwable e) {
        log.error("Ошибка: " + e.getMessage());
        return new ApiError(ApiErrorStatus.BAD_REQUEST, e.getCause().getMessage(), e.getMessage(), LocalDateTime.now());
    }
}
