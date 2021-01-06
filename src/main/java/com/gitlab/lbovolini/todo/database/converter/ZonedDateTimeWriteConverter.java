package com.gitlab.lbovolini.todo.database.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, String> {

    private final String dateTimeFormat;

    @Autowired
    public ZonedDateTimeWriteConverter(@Value("${date.time.format:yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSxxx}") String dateTimeFormat){
        this.dateTimeFormat = dateTimeFormat;
    }

    @Override
    public String convert(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormat));
    }
}