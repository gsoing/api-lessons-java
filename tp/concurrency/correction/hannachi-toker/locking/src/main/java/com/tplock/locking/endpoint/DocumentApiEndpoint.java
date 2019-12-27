package com.tplock.locking.endpoint;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tplock.locking.dto.DocumentDto;
import com.tplock.locking.exception.BadRequestException;
import com.tplock.locking.exception.ConflictException;
import com.tplock.locking.exception.NoContentException;
import com.tplock.locking.exception.NotFoundException;
import com.tplock.locking.model.Document;
import com.tplock.locking.model.DocumentsList;
import com.tplock.locking.model.Lock;
import com.tplock.locking.service.DocumentApi;
import com.tplock.locking.service.DocumentService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@CrossOrigin(origins = "*")
public class DocumentApiEndpoint implements DocumentApi {

    @Autowired
    // pourquoi un dto injecté ?
    private DocumentDto documentDto;

    @Autowired
    private DocumentService documentService;

    // pareil ca ne sert à rien il faut le virer
    private HashMap<String, Lock> lockList;

    private static final Logger log = LoggerFactory.getLogger(DocumentApiEndpoint.class);

    // idem
    private final ObjectMapper objectMapper;

    // Alors je ne sais pas si vous en êtes conscient mais mettre la request en variable de class
    // est dangereux, un controller est un singleton donc une fois la requête setté c'est toujours la même
    // spring s'occupe d'injecter la requête courante
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DocumentApiEndpoint(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.lockList = new HashMap<>();
    }

    public ResponseEntity<Document> documentsDocumentIdGet(
            @ApiParam(value = "Document Id",required=true) @PathVariable("documentId") String documentId)
            throws NotFoundException, BadRequestException {
        // une annotation étit largement suffisante
        // @Consume("application/json")
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<>( this.documentService.findDocumentById(documentId), HttpStatus.OK);
        }
        throw new BadRequestException();
    }

    public ResponseEntity<String> documentsDocumentIdLockDelete(@ApiParam(value = "Document Id",required=true) @PathVariable("documentId") String documentId) throws NotFoundException,BadRequestException{
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            this.documentService.removeLock(documentId);
            return new ResponseEntity<String>("{\"result\":\"Lock_removed\"}",HttpStatus.ACCEPTED);
        }
        throw new BadRequestException();
    }

    public ResponseEntity<Lock> documentsDocumentIdLockGet(@ApiParam(value = "Document Id",required=true) @PathVariable("documentId") String documentId) throws NoContentException,BadRequestException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<>( this.documentService.getLock(documentId),HttpStatus.OK);
        }
        throw new BadRequestException();
    }

    public ResponseEntity<Lock> documentsDocumentIdLockPut(@ApiParam(value = "Document Id",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "the lock object is appointed")  @RequestBody Lock lock) throws ConflictException, NoContentException ,BadRequestException{
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Lock>(this.documentService.lockDocument(documentId,lock),HttpStatus.OK);
        }
        throw new BadRequestException();
    }

    public ResponseEntity<Document> documentsDocumentIdPost(@ApiParam(value = "id du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "Upload the fields : text, editor, upload date")  @RequestBody Document document) throws ConflictException,BadRequestException {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Document>(this.documentService.updateDocument(document,documentId), HttpStatus.OK);
        }
        throw new BadRequestException();
    }

    public ResponseEntity<DocumentsList> documentsGet(
            @ApiParam(value = "page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "number of pages in the document") @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<DocumentsList>(this.documentService.findAll(), HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<DocumentsList>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<DocumentsList>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Document> documentsPost(@ApiParam(value = "Document" ,required=true ) @RequestBody Document document) throws BadRequestException{
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Document>(this.documentService.saveDocument(document), HttpStatus.OK);
        }else{
            throw new BadRequestException();
        }
    }

}
