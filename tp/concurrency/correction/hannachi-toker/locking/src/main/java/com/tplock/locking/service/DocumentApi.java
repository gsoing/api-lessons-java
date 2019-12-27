package com.tplock.locking.service;

import com.tplock.locking.dto.ErrorDefinition;
import com.tplock.locking.exception.BadRequestException;
import com.tplock.locking.exception.ConflictException;
import com.tplock.locking.exception.NoContentException;
import com.tplock.locking.exception.NotFoundException;
import com.tplock.locking.model.Document;
import com.tplock.locking.model.DocumentsList;
import com.tplock.locking.model.Lock;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// ca aurait été mieux rangé dans endpoint non ?
public interface DocumentApi {
    @ApiOperation(value = "looking for the document", nickname = "documentsDocumentIdGet", notes = "return the looked on document ", response = Document.class, tags={ "documents", })
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "document found", response = Document.class),
                    @ApiResponse(code = 404, message = "the document doesn't exist", response = ErrorDefinition.class)
            })
    @RequestMapping(value = "/documents/{documentId}",
            method = RequestMethod.GET,
            produces = "application/json")
    ResponseEntity<Document> documentsDocumentIdGet(@ApiParam(value = "id du document",required=true) @PathVariable("documentId") String documentId) throws NotFoundException, BadRequestException;


    @ApiOperation(value = "delete the ", nickname = "documentsDocumentIdLockDelete", notes = "", tags={ "locks", })
    @ApiResponses(value =
            {
                    @ApiResponse(code = 202, message = "the lock has been deleted")
            })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.DELETE,
            produces = "application/json")
    ResponseEntity<String> documentsDocumentIdLockDelete(@ApiParam(value = "id du document",required=true) @PathVariable("documentId") String documentId) throws NotFoundException, BadRequestException;


    @ApiOperation(value = "the lock is applied in this document", nickname = "documentsDocumentIdLockGet", notes = "", response = Lock.class, tags={ "locks", })
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "Document locked", response = Lock.class),
                    @ApiResponse(code = 204, message = "no lock for this document")
            })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.GET,
            produces = "application/json")
    ResponseEntity<Lock> documentsDocumentIdLockGet(@ApiParam(value = "Document id",required=true) @PathVariable("documentId") String documentId) throws NoContentException,BadRequestException;


    @ApiOperation(value = "put a lock in the document", nickname = "documentsDocumentIdLockPut", notes = " validated if no other lock was appointed in this document", response = Lock.class, tags={ "locks", })
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "lock ok", response = Lock.class),
                    @ApiResponse(code = 409, message = " document already locked. Show the lock already present", response = Lock.class)
            })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.PUT,
            produces = "application/json")
    ResponseEntity<Lock> documentsDocumentIdLockPut(@ApiParam(value = "Document Id",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "the lock object is appointed")  @Valid @RequestBody Lock lock) throws ConflictException, NoContentException,BadRequestException;

    @ApiOperation(value = "update the document", nickname = "documentsDocumentIdPost", notes = "update the document if the user has the last version and the documents hasn't any lock ", response = Document.class, tags={ "documents", })
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "Document uploaded", response = Document.class)
            })
    @RequestMapping(value = "/documents/{documentId}",
            method = RequestMethod.POST,
            produces = "application/json")
    ResponseEntity<Document> documentsDocumentIdPost(@ApiParam(value = "id du document",required=true) @PathVariable("documentId") String documentId,@ApiParam(value = "upload of  : text, title, editor, date of update ")  @Valid @RequestBody Document document)throws ConflictException, BadRequestException;

    @ApiOperation(value = "Returns all the documents without filters", nickname = "documentsGet", notes = "", response = DocumentsList.class, tags={ "documents", })
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "Documents list", response = DocumentsList.class)
            })
    @RequestMapping(value = "/documents",
            method = RequestMethod.GET,
            produces = "application/json")
    ResponseEntity<DocumentsList> documentsGet (
            @ApiParam(value = "page number") @Valid @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Nombre de documents par page") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @ApiOperation(value = "Create a document", nickname = "documentsPost", notes = "", response = Document.class, tags={ "documents", })
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "Document created", response = Document.class),
                    @ApiResponse(code = 400, message = "Writing Error ", response = ErrorDefinition.class)
            })
    @RequestMapping(value = "/documents",
            method = RequestMethod.POST,
            produces = "application/json")
    ResponseEntity<Document> documentsPost(@ApiParam(value = "Document" ,required=true )  @Valid @RequestBody Document document) throws BadRequestException;

}
