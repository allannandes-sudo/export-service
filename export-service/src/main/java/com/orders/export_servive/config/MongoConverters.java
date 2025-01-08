package com.orders.export_servive.config;


import com.orders.export_servive.converters.ZonedDateTimeReadConverter;
import com.orders.export_servive.converters.ZonedDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConverters {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {

        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }

}
