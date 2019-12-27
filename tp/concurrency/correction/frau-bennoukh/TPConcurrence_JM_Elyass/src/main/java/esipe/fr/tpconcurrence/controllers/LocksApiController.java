package esipe.fr.tpconcurrence.controllers;


import esipe.fr.tpconcurrence.entities.Lock;
import esipe.fr.tpconcurrence.exceptions.ApiException;
import esipe.fr.tpconcurrence.services.DocumentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

@Api(tags = "locks")
@Controller
public class LocksApiController{

    private static final Logger log = LoggerFactory.getLogger(DocumentsApiController.class);
    @Autowired
    DocumentService documentService;


    @ApiOperation(value = "pose un verrou sur le document", nickname = "documentsDocumentIdLockPut",
            notes = "l'utilisateur peut poser un verrou si aucun n'autre n'est posé ", response = Lock.class, tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le verrou est posé", response = Lock.class),
            @ApiResponse(code = 409, message = "un verrou est déjà posé, retourne le verrou déjà posé", response = Lock.class) })
    @RequestMapping(value = "/documents/{documentId}/lock", method = RequestMethod.PUT)
    public ResponseEntity<Lock> documentsDocumentIdLockPut(
            @ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId,
            @ApiParam(value = "l'objet verrou posé"  )  @Valid @RequestBody Lock lock) throws ApiException {
        Lock responseLock = documentService.setLock(lock,documentId);
        if(responseLock.getOwner().equals(lock.getOwner()) && responseLock.getCreated().equals(lock.getCreated())){
            return ResponseEntity
                    .ok()
                    .body(responseLock);
        }else{
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(responseLock);
        }
    }


    @ApiOperation(value = "supprime le verrou posé", nickname = "documentsDocumentIdLockDelete", notes = "", tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "le verrou est supprimé") })
    @RequestMapping(value = "/documents/{documentId}/lock",method = RequestMethod.DELETE)
    public ResponseEntity<?> documentsDocumentIdLockDelete(
            @ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) throws ApiException {
        documentService.deleteLock(documentId);
        return new ResponseEntity("Le verrou est supprimé",HttpStatus.NO_CONTENT);
    }



    @ApiOperation(value = "retourne le verrou posé sur le document si présent", nickname = "documentsDocumentIdLockGet", notes = "", response = Lock.class, tags={ "locks", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le verrou posé", response = Lock.class),
            @ApiResponse(code = 204, message = "Aucun verrou posé") })
    @RequestMapping(value = "/documents/{documentId}/lock", method = RequestMethod.GET)
    public ResponseEntity<Lock> documentsDocumentIdLockGet(
            @ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) throws ApiException {
        Lock responseLock = documentService.findLock(documentId);
        if(responseLock != null){
            return ResponseEntity
                    .ok()
                    .body(responseLock);
        }else{
            return new ResponseEntity("Aucun verrou posé",HttpStatus.NO_CONTENT);
        }
    }
}
