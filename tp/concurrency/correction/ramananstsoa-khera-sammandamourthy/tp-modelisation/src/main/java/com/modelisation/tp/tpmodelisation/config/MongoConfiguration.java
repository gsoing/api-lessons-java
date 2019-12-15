package com.modelisation.tp.tpmodelisation.config;

import com.modelisation.tp.tpmodelisation.repository.EtagEventListener;
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
