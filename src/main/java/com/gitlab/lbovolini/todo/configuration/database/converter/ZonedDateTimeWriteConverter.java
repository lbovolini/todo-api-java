package com.gitlab.lbovolini.todo.configuration.mongo.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, String> {

    @Value("${date.time.format:yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSxxx}")
    private String dateTimeFormat;

    public ZonedDateTimeWriteConverter(String a){}

    @Override
    public String convert(ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormat));
    }
}