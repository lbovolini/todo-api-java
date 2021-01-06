package com.gitlab.lbovolini.todo.configuration.mongo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ZonedDateTimeReadConverter implements Converter<String, ZonedDateTime> {

    private final String dateTimeFormat;

    @Autowired
    public ZonedDateTimeReadConverter(@Value("${date.time.format:yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSxxx}") String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    @Override
    public ZonedDateTime convert(String date) {
        return ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(dateTimeFormat));
    }
}