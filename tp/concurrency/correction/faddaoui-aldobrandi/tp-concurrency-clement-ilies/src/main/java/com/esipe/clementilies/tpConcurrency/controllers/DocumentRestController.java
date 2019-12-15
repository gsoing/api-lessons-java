package com.esipe.clementilies.tpConcurrency.controllers;

import com.esipe.clementilies.tpConcurrency.Exception.NotFoundException;
import com.esipe.clementilies.tpConcurrency.models.Document;
import com.esipe.clementilies.tpConcurrency.models.Lock;
import com.esipe.clementilies.tpConcurrency.services.DocumentService;
import com.esipe.clementilies.tpConcurrency.services.LockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
@Slf4j
@Validated
@RestController
@EnableWebMvc
@RequestMapping(DocumentRestController.PATH)
public class DocumentRestController {

    public static final String PATH = "/v1/documents";
    @Autowired
    private DocumentService documentService;

    @Autowired
    private LockService lockService;

    @GetMapping(params = {"page", "size"})
    public Page<Document> getAllDocuments(@RequestParam("page") int page, @RequestParam("size") int size) {
        return documentService.getDocuments(page, size);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity getDocument(@PathVariable(value="documentId") String documentId) {
        try{
            return new ResponseEntity(documentService.getDocumentById(documentId), HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public Document postDocument(@Valid @RequestBody Document document){
        return documentService.createDocument(document);
    }

    @PostMapping("/{documentId}")
    public ResponseEntity updateDocument(@PathVariable(value = "documentId") String documentId, @RequestBody Document document){
        try{
            return new ResponseEntity(documentService.updateDocument(documentId, document), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity("Error" + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity deleteDocument(@PathVariable(value = "documentId") String documentId){
        try{
            documentService.deleteDocumentById(documentId);
            return new ResponseEntity(HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity("error :" +e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{documentId}/lock")
    public ResponseEntity getDocumentLock(@PathVariable(value="documentId") String documentId){
        try{
            return new ResponseEntity(lockService.getLockFromDocument(documentId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity("error: "+ e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{documentId}/lock")
    public ResponseEntity putDocumentLock(@PathVariable(value="documentId") String documentId, @RequestBody Lock lock){
        try{
            return new ResponseEntity(lockService.putLock(documentId, lock), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity("error: "+ e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{documentId}/lock")
    public ResponseEntity deleteDocumentLock(@PathVariable(value="documentId") String documentId){
        try{
            lockService.deleteLock(documentId);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity("error: "+ e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
