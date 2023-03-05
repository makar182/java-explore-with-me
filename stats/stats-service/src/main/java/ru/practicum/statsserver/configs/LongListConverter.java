package ru.practicum.statsserver.configs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LongListConverter implements Converter<String, List<Long>> {

    @Override
    public List<Long> convert(String source) {
        return Arrays.stream(StringUtils.strip(source, "[]").split(","))
                .map(StringUtils::strip)
                .map(Long::new)
                .collect(Collectors.toList());
    }
}
