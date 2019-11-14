package com.gso.samples.tweet.config;

import com.gso.samples.tweet.repository.EtagEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Classe de configuration que va créer un bean pour ajouter notre etag listener
 */
@Configuration
@EnableMongoAuditing
public class MongoConfiguration {

    @Bean
    public EtagEventListener createEtagEventListener(){
        return new EtagEventListener();
    }
}
