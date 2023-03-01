package ru.practicum.ewmservice.configs;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTime implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(source, dateFormatter);
    }
}
