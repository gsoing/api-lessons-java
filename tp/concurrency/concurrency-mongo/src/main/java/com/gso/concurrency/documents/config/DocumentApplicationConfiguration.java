package com.gso.concurrency.documents.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.gso.concurrency.documents.properties.LockProperties;
import com.gso.concurrency.documents.repository.EtagEventListener;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.ExpirableLockRegistry;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableMongoAuditing
public class DocumentApplicationConfiguration {

    private static final String dateFormat = "yyyy-MM-dd";
    public static final String zoneDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(zoneDateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(zoneDateTimeFormat)));
            builder.serializers(new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(zoneDateTimeFormat)));
        };
    }

    @Bean
    public EtagEventListener createEtagEventListener(){
        return new EtagEventListener();
    }

    @Bean
    public ExpirableLockRegistry createExpirableLockRegistry(RedisConnectionFactory connectionFactory,
                                                             LockProperties lockProperties) {
        return new RedisLockRegistry(connectionFactory, lockProperties.getRegistry());
    }
}
