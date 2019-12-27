package fr.esipe.documentManagerAPI.controller;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import fr.esipe.documentManagerAPI.dao.DocumentSummaryDAO;
import fr.esipe.documentManagerAPI.exceptions.BadRequest;
import fr.esipe.documentManagerAPI.exceptions.NotFound;
import fr.esipe.documentManagerAPI.model.DocumentModel;
import fr.esipe.documentManagerAPI.model.DocumentSummary;
import fr.esipe.documentManagerAPI.model.Lock;
import fr.esipe.documentManagerAPI.service.DocumentService;
import fr.esipe.documentManagerAPI.service.DocumentSummaryService;
import fr.esipe.documentManagerAPI.service.LockService;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentService serv;
    @Autowired
    private LockService ls;
    @Autowired
    private DocumentSummaryService dss;



    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/create")
    public String create(@RequestParam int id, @RequestParam String title, @RequestParam String creator, @RequestParam String editor, @RequestParam String body) {
        System.out.println("------> : create");
        logger.debug("create document.");
        Date currentDate = new Date();
        DocumentModel doc = new DocumentModel(id,currentDate, currentDate, title, creator, editor, body);

        try{
            serv.createDocument(doc);
            dss.updateDocument(doc);
        }catch (Exception e){
            throw new BadRequest();
        }


        return doc.toString();
    }

    @RequestMapping("/createList")
    public String createAll() {
        System.out.println("------> : createAll");
        logger.debug("create documents.");
        Date currentDate = new Date();

        for(int i = 0; i<20;i++) {
            DocumentModel doc = new DocumentModel(i, currentDate, currentDate, "Le titre numero: "+i, "Maxime", "Maxime", "lopsum erratum numberum "+i);
            try {
                serv.createDocument(doc);
                dss.updateDocument(doc);
            }catch(Exception e){
                throw new BadRequest();
            }
        }
        return "20 documents created";
    }

    @RequestMapping(value="/documents", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentModel> getAll(){
        System.out.println("------> : getAllDocuments");
        logger.debug("Getting all documents.");
        return serv.getAllDocuments();
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getById(@PathVariable int id){
        logger.debug("Getting document with id = {}", id);
        if (serv.findDocumentById(id) == null){
            throw new NotFound();
        } else {
            return serv.findDocumentById(id).toString();
        }
    }

    @PutMapping("/documents/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@PathVariable int id, @Valid @RequestBody DocumentModel document){
        System.out.println("------> : Update: "+id);
        String result ="";
        try{

                if (ls.findLockByDocId(id).isPresent()) {
                    System.out.println("------> : Lock existing");
                    result = "error the document is locked";
                } else {
                    Date currentDate = new Date();
                    Lock loc = new Lock("Maxime", currentDate, id);
                    logger.debug("Create lock on doc = {}", id);
                    System.out.println("Creating lock");
                    ls.createLock(loc);

                    System.out.println("Updating doc");
                    logger.debug("Update document with id = {}", id);
                    if (serv.findDocumentById(id) == null){
                       throw new NotFound();
                    }else {
                        serv.updateDocument(document, id);
                        dss.updateDocumentById(id);
                        result = "document with id:" + id + " is updated";
                    }
                    logger.debug("Deleting lock on doc = {}", id);
                    System.out.println("Deleting lock");
                    ls.deleteLockByDocId(id);
                }

        }catch(Exception e){
            // on peut faire mieux, genre une erreur HTTP 500 plutot que ça
            e.printStackTrace();
        }


        return result;
    }

    @DeleteMapping("/documents/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable(value = "id") int id){
        logger.debug("Deleting document with id:{}", id);
        dss.deleteDocumentById(id);
        serv.deleteDocumentById(id);
        return "doc with id"+id+" deleted";
    }

    @DeleteMapping("/deleteall")
    public String deleteAll(){
        logger.debug("Delete all doc");
        serv.deleteAllDocument();
        return "all doc deleted";
    }



}
