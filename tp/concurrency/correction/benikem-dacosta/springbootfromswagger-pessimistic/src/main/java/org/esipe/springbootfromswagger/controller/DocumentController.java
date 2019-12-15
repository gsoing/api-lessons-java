package org.esipe.springbootfromswagger.controller;

import lombok.extern.slf4j.Slf4j;
import org.esipe.springbootfromswagger.model.Document;
import org.esipe.springbootfromswagger.model.DocumentList;
import org.esipe.springbootfromswagger.model.Lock;
import org.esipe.springbootfromswagger.service.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private DocumentServiceImpl documentService;

    @Autowired
    public void setDocumentService(DocumentServiceImpl documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Document> save(@RequestBody Document document, UriComponentsBuilder uriComponentsBuilder) {
        this.documentService.save(document);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/documents/{documentId}").buildAndExpand(document.getDocumentId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uriComponents.toUri())
                .body(document);
    }

    @GetMapping
    public DocumentList findAll(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "20") int pageSize) {
        return this.documentService.findAll(pageNo, pageSize);
    }

    @GetMapping("/{documentId}")
    @ResponseBody
    public ResponseEntity<Document> findById(@PathVariable String documentId) {
        Document body = this.documentService.findById(documentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @PostMapping("/{documentId}")
    public ResponseEntity<Document> updateById(@RequestBody Document document, @PathVariable String documentId) {
        Lock lock = new Lock();
        this.putDocumentLock(lock, documentId);
        Document body = this.documentService.updateById(document, documentId);
        this.deleteDocumentLock(documentId);
        log.info("Process done");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @GetMapping("/{documentId}/lock")
    public ResponseEntity<Lock> getDocumentLock(@PathVariable String documentId) {
        Lock body = this.documentService.getDocumentLock(documentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @PutMapping("/{documentId}/lock")
    public ResponseEntity<Lock> putDocumentLock(@RequestBody Lock lock, @PathVariable String documentId) {
        Lock body = this.documentService.putDocumentLock(lock, documentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @DeleteMapping("/{documentId}/lock")
    public ResponseEntity<Void> deleteDocumentLock(@PathVariable String documentId) {
        this.documentService.deleteDocumentLock(documentId);
        return ResponseEntity
                .noContent()
                .build();
    }


}
