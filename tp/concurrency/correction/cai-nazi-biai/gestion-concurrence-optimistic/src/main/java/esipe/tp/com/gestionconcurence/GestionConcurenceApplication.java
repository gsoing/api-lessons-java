package esipe.tp.com.gestionconcurence;

import esipe.tp.com.gestionconcurence.model.Document;
import esipe.tp.com.gestionconcurence.repository.DocumentRepository;
import esipe.tp.com.gestionconcurence.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableSwagger2
public class GestionConcurenceApplication {


    public static void main(String[] args) {
        SpringApplication.run(GestionConcurenceApplication.class, args);

    }



}
