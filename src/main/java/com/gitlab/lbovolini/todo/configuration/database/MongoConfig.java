package com.gitlab.lbovolini.todo.configuration.mongo;

import com.gitlab.lbovolini.todo.configuration.mongo.converter.ZonedDateTimeReadConverter;
import com.gitlab.lbovolini.todo.configuration.mongo.converter.ZonedDateTimeWriteConverter;
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

    private final List<Converter<?, ?>> converters = new ArrayList<>();

    private final String databaseName;

    private final ZonedDateTimeReadConverter zonedDateTimeReadConverter;
    private final ZonedDateTimeWriteConverter zonedDateTimeWriteConverter;

    @Autowired
    public MongoConfig(@Value("${spring.data.mongodb.database:todo}") String databaseName,
                       ZonedDateTimeReadConverter zonedDateTimeReadConverter,
                       ZonedDateTimeWriteConverter zonedDateTimeWriteConverter) {
        this.databaseName = databaseName;
        this.zonedDateTimeWriteConverter = zonedDateTimeWriteConverter;
        this.zonedDateTimeReadConverter = zonedDateTimeReadConverter;
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoCustomConversions customConversions() {
        converters.add(zonedDateTimeReadConverter);
        converters.add(zonedDateTimeWriteConverter);

        return new MongoCustomConversions(converters);
    }
}
