package documentedition.demo.config;

import documentedition.demo.repository.EtagEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfiguration {

    @Bean
    public EtagEventListener createEtagEventListener(){
        return new EtagEventListener();
    }
}
