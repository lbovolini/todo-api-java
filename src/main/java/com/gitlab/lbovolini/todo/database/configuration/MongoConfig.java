package com.gitlab.lbovolini.todo.database.configuration;

import com.gitlab.lbovolini.todo.database.converter.DateToZonedDateTimeReadConverter;
import com.gitlab.lbovolini.todo.database.converter.ZonedDateTimeReadConverter;
import com.gitlab.lbovolini.todo.database.converter.ZonedDateTimeWriteConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final String databaseName;

    private final List<Converter<?, ?>> converters = new ArrayList<>();

    private final ZonedDateTimeReadConverter zonedDateTimeReadConverter;
    private final ZonedDateTimeWriteConverter zonedDateTimeWriteConverter;
    private final DateToZonedDateTimeReadConverter dateToZonedDateTimeReadConverter;

    @Autowired
    public MongoConfig(@Value("${spring.data.mongodb.database:todo}") String databaseName,
                       ZonedDateTimeReadConverter zonedDateTimeReadConverter,
                       ZonedDateTimeWriteConverter zonedDateTimeWriteConverter,
                       DateToZonedDateTimeReadConverter dateToZonedDateTimeReadConverter) {
        this.databaseName = databaseName;
        this.zonedDateTimeWriteConverter = zonedDateTimeWriteConverter;
        this.zonedDateTimeReadConverter = zonedDateTimeReadConverter;
        this.dateToZonedDateTimeReadConverter = dateToZonedDateTimeReadConverter;
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoCustomConversions customConversions() {
        converters.add(zonedDateTimeReadConverter);
        converters.add(zonedDateTimeWriteConverter);
        converters.add(dateToZonedDateTimeReadConverter);

        return new MongoCustomConversions(converters);
    }
}
