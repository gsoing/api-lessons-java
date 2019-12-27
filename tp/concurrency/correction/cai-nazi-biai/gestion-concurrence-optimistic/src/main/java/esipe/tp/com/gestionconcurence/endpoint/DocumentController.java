package esipe.tp.com.gestionconcurence.endpoint;

import esipe.tp.com.gestionconcurence.dto.DocumentsList;
import esipe.tp.com.gestionconcurence.dto.ErrorDefinition;
import esipe.tp.com.gestionconcurence.exception.NotFoundException;
import esipe.tp.com.gestionconcurence.model.Document;
import esipe.tp.com.gestionconcurence.model.Locks;
import esipe.tp.com.gestionconcurence.service.DocumentService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Api(value = "documents")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(DocumentController.PATH)
public class DocumentController {

    @Autowired
    DocumentService documentService;
    public static final String PATH = "";

    @ApiOperation(value = "lis le document", nickname = "documentsDocumentIdGet",
            notes = "retourne un document", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le document demandé", response = Document.class),
            @ApiResponse(code = 404, message = "le document n'existe pas", response = ErrorDefinition.class) })
    @RequestMapping(value = "/documents/{documentId}",
            method = RequestMethod.GET)
    ResponseEntity<Document>  getDocument(
            @ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") int documentId){
        Document doc = documentService.getDocument(documentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.HOURS))
                .body(doc);
    }


    @ApiOperation(value = "supprime le verrou posé", nickname = "documentsDocumentIdLockDelete",
            notes = "", tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "le verrou est supprimé") })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.DELETE)
    ResponseEntity<Void>  deleteLockDocument(
            @ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") Integer documentId){
        try {
            documentService.deleteLock(documentId);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @ApiOperation(value = "retourne le verrou posé sur le document si présent",
            nickname = "documentsDocumentIdLockGet", notes = "", response = Locks.class, tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le verrou posé", response = Locks.class),
            @ApiResponse(code = 204, message = "aucun verrou posé") })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.GET)
    ResponseEntity<Locks> getLockDocument
            (@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") int documentId){
        Locks locks = documentService.getLock(documentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.HOURS))
                .body(locks);
    }


    @ApiOperation(value = "mise à jour du document", nickname = "documentsDocumentIdPost",
            notes = "met à jour le document si l'utilisateur possède la dernière version et que personne n'a posé de verrou ", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "le document est mis à jour", response = Document.class) })
    @RequestMapping(value = "/documents/{documentId}",
            method = RequestMethod.POST)
    ResponseEntity<Document> updateDocument(
            @ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") int documentId,
            @ApiParam(value = "met à jour le texte, le titre, l'editeur et la date de mise à jour") @Valid @RequestBody Document document){
        // recheche doc with id

        // Quel est l'intérêt de passer l'identifiant du document dans l'url si vous ne vous en servez pas
        Document updateDocument = documentService.updateDocument(document);
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(updateDocument.getVersion().toString())
                .body(updateDocument);

    }


    @ApiOperation(value = "retourne tous les documents, pas de filtrage", nickname = "documentsGet", notes = "", response = DocumentsList.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "la liste des documents", response = DocumentsList.class) })
    @RequestMapping(value = "/documents",
            method = RequestMethod.GET)
    ResponseEntity<DocumentsList> getDocuments(
            @ApiParam(value = "numéro de la page à retourner") @Valid @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "nombre de documents par page") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize){
        DocumentsList documentsList = documentService.getDocuments(page,pageSize);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(documentsList);
    }

    @ApiOperation(value = "create a document", nickname = "documentsPost", notes = "", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "le document créé", response = Document.class),
            @ApiResponse(code = 400, message = "le contenu n'est pas correction", response = ErrorDefinition.class) })
    @RequestMapping(value = "/documents",
            method = RequestMethod.POST)
    ResponseEntity<Document> createDocument(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Document document, UriComponentsBuilder uriComponentsBuilder){
        Document createdDocument = documentService.createDocument(document);
        UriComponents uriComponents = uriComponentsBuilder.path(DocumentController.PATH.concat("/{id}"))
                .buildAndExpand(createdDocument.getDocumentId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(uriComponents.toUri())
                .body(document);

    }

    @ApiOperation(value = "pose un verrou sur le document", nickname = "documentsDocumentIdLockPut", notes = "l'utilisateur peut poser un verrou si aucun n'autre n'est posé ", response = Locks.class, tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le verrou est posé", response = Locks.class),
            @ApiResponse(code = 409, message = "un verrou est déjà posé, retourne le verrou déjà posé", response = Locks.class) })
    @RequestMapping(value = "/documents/{documentId}/lock",
            method = RequestMethod.PUT)
    ResponseEntity<Locks> putLockDocument(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") Integer documentId, @ApiParam(value = "l'objet verrou posé"  )  @Valid @RequestBody Locks locks){
        Locks createLock = documentService.createLock(locks);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locks);
    }
}
