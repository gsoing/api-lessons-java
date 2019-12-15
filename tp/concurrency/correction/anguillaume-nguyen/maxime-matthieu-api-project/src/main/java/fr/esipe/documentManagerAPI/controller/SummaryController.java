package fr.esipe.documentManagerAPI.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.esipe.documentManagerAPI.model.DocumentModel;
import fr.esipe.documentManagerAPI.model.DocumentSummary;
import fr.esipe.documentManagerAPI.model.Lock;
import fr.esipe.documentManagerAPI.service.DocumentService;
import fr.esipe.documentManagerAPI.service.DocumentSummaryService;
import fr.esipe.documentManagerAPI.service.LockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    @Autowired
    private DocumentSummaryService serv;
    @Autowired
    private DocumentService ds;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable int id, @Valid @RequestBody DocumentSummary document){
        serv.updateDocument(ds.findDocumentById(id).get());
    }





}
