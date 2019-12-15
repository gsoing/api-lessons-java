package esipe.fr.tpconcurrence.controllers;

import esipe.fr.tpconcurrence.entities.*;
import esipe.fr.tpconcurrence.exceptions.ApiException;
import esipe.fr.tpconcurrence.services.DocumentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import static org.springframework.util.ObjectUtils.isEmpty;

@Api(tags = "documents")
@Controller
public class DocumentsApiController {

    @Autowired
    DocumentService documentService;

    @ApiOperation(value = "lis le document", nickname = "documentsDocumentIdGet", notes = "retourne un document", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le document demandé", response = Document.class),
            @ApiResponse(code = 404, message = "le document n'existe pas", response = ErrorDefinition.class) })
    @GetMapping(value = "/documents/{documentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> documentsDocumentIdGet(@ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId) throws ApiException {
            Document doc = documentService.findDocumentById(documentId);
            return ResponseEntity
                    .ok()
                    .eTag("\"" + doc.getVersion() + "\"")
                    .body(doc);

    }



    @ApiOperation(value = "mise à jour du document", nickname = "documentsDocumentIdPost", notes = "met à jour le document si l'utilisateur possède la dernière version et que personne n'a posé de verrou ", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "le document est mis à jour", response = Document.class),
            @ApiResponse(code = 409, message = "le document est verouillé par un autre éditeur", response = Document.class),
            @ApiResponse(code = 412, message = "il existe une version plus récente du document", response = Document.class)})
    @RequestMapping(value = "/documents/{documentId}",method = RequestMethod.POST)
    public ResponseEntity<Document> documentsDocumentIdPost(@RequestHeader(name = "If-Match", required = true) String requestIfMatch, @ApiParam(value = "identifiant du document",required=true) @PathVariable("documentId") String documentId, @ApiParam(value = "met à jour le texte, le titre, l'editeur et la date de mise à jour"  )  @RequestBody Document document) throws ApiException {
        if (isEmpty(requestIfMatch)) {
            return ResponseEntity.badRequest().build();
        }else{
            document.setVersion(requestIfMatch);
            Document doc = documentService.updateDocument(document,documentId);
            return ResponseEntity
                    .ok()
                    .eTag("\"" + doc.getVersion() + "\"")
                    .body(doc);
        }
    }

    @ApiOperation(value = "retourne tous les documents, pas de filtrage", nickname = "documentsGet", notes = "", response = DocumentsList.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "la liste des documents", response = DocumentsList.class) })
    @GetMapping(value = "/documents",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentsList> documentsGet(@ApiParam(value = "numéro de la page à retourner") @Valid @RequestParam(value = "page", required = false) Integer page, @ApiParam(value = "nombre de documents par page") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<DocumentsList>(documentService.findAll(page,pageSize), HttpStatus.OK);
    }

    @ApiOperation(value = "create a document", nickname = "documentsPost", notes = "", response = Document.class, tags={ "documents", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "le document créé", response = Document.class),
            @ApiResponse(code = 400, message = "le contenu n'est pas correct", response = ErrorDefinition.class) })
    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    public ResponseEntity<Document> documentsPost(@ApiParam(value = "" ,required=true )  @RequestBody Document document) throws ApiException {
        Document doc = documentService.save(document);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag("\"" + doc.getVersion() + "\"")
                .body(doc);
    }

}
