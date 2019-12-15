package tpmodeldereal.demo.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tpmodeldereal.demo.Model.document;
import tpmodeldereal.demo.Repository.DocRepository;
import tpmodeldereal.demo.Service.DocService;

import java.net.URI;
import java.util.*;


@Slf4j
@Validated
@RestController
@EnableWebMvc
@RequiredArgsConstructor
@RequestMapping(DocumentController.PATH)
public class DocumentController {

    public static final String PATH = "/api/v1/documents";

    @Autowired
    DocRepository docRepository ;
    @Autowired
    DocService docService ;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<document>getAll() {
        return docService.getAllDocs() ;
    }


    @GetMapping("/(id)")
    public document getbyid (@PathVariable(value= "documentId") String documentId ) {
        return docService.findDocById( documentId ) ;
    }

    @PostMapping(value = "/id")
    public ResponseEntity<Void> ajouterProduit(@RequestBody document doc) {
        document dd = docRepository.save(doc);
        if (dd == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dd.getBody())
                .toUri();
        return ResponseEntity.created(location).build();
    }



}
