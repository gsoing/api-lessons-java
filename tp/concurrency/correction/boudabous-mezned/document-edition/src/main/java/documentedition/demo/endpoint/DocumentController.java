package documentedition.demo.endpoint;


import documentedition.demo.dto.DocumentDto;
import documentedition.demo.dto.DocumentSummaryDto;

import documentedition.demo.dto.LockDto;

import documentedition.demo.dto.PageData;
import documentedition.demo.exception.NotFoundException;
import documentedition.demo.model.Doc;
import documentedition.demo.model.Lock;
import documentedition.demo.service.DocumentService;
import documentedition.demo.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@EnableWebMvc
@RequiredArgsConstructor
@RequestMapping(DocumentController.PATH)
public class DocumentController {

    public static final String PATH = "/v1/documents";

    @Autowired
    private DocumentService documentService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<DocumentDto>
    createDocument(@Valid @RequestBody DocumentDto document, UriComponentsBuilder uriComponentsBuilder) {

        Doc createdDocument = documentService.createDocument(document.toEntity());
        UriComponents uriComponents = uriComponentsBuilder.path(DocumentController.PATH.concat("/{documentId}"))
                .buildAndExpand(createdDocument.getDocumentId());

        DocumentDto createdDocumentDto = createdDocument.toDto();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .lastModified(createdDocumentDto.getUpdated())
                .eTag(createdDocument.getEtag())
                .location(uriComponents.toUri())
                .body(createdDocumentDto);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    @ResponseBody
    public ResponseEntity<PageData<DocumentSummaryDto>> getDocuments( @PageableDefault(page = 0, size = 20) Pageable pageable,
                                                              UriComponentsBuilder uriComponentsBuilder) {

        Page<Doc> results = documentService.getDocuments(pageable);
        PageData<DocumentSummaryDto> pageResult = PageData.fromPage(results.map(Doc::toSummaryDto));

        if (RestUtils.hasNext(results, pageable)) {
            pageResult.setNext(RestUtils.buildNextUri(uriComponentsBuilder.path(PATH), pageable));
        }

        if (pageResult.getTotalElements() <= 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(pageResult);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, path = "/{id}")
    @ResponseBody
    public ResponseEntity<DocumentDto> getDocument(@PathVariable("id") String documentId) {

        Doc document = documentService.getDocument(documentId);

        DocumentDto documentDto = document.toDto();

        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(document.getEtag())
                .body(documentDto);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<DocumentDto>
    updateDocument(@PathVariable("id") String documentId, @Valid @RequestBody DocumentDto documentDto, @RequestHeader(name = "If-Match", required = true) String ifMatch) {

        Doc actualDocument = documentService.getDocument(documentId);

        // Ca n'arrivera jamais puisque vous levez une NotFoundException dans la méthode getDocument
        if(actualDocument == null) {
            return ResponseEntity.notFound().build();
        }

        if (!ifMatch.equals(actualDocument.getEtag())) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }

        Doc document = documentDto.toEntity();
        document.setDocumentId(documentId);
        Doc updatedDocument = documentService.updateDocument(document);

        // heu il ne peut pas être nul à ce stade, ce serait pas plutot updatedDocument que vous vouliez tester ?
        // si c'est le cas c'est qu'un verrou a déjà été posé donc plutot une erreur 409
        if(actualDocument == null) {
            return ResponseEntity.badRequest().build();
        }

        DocumentDto updatedDocumentDto = updatedDocument.toDto();

        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(updatedDocumentDto.getUpdated())
                .eTag(updatedDocument.getEtag())
                .body(updatedDocumentDto);
    }

    @GetMapping("/{id}/lock")
    public ResponseEntity<?> getLock(@PathVariable("id") String documentId) {

        Lock lock = documentService.getLock(documentId);

        if (lock == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity
                .ok()
                .body(lock.toDto());
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<LockDto> lockDoc(@PathVariable("id") String documentId, @RequestBody @Valid LockDto lockDto) {

        Doc document = documentService.getDocument(documentId);

        // Même remarque ca ne peut pas arriver car la méthode getDocument lance une exception si
        //le document n'existe pas
        if(document == null) {
            return ResponseEntity.notFound().build();
        }

        Lock lock = documentService.getLock(documentId);

        if(lock != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(lock.toDto());
        }

         Lock newLock = documentService.lockDoc(lockDto.toEntity(), documentId);

        return ResponseEntity
                .ok()
                .body(newLock.toDto());
    }

    @DeleteMapping("/{id}/lock")
    public ResponseEntity<?> unlockDocument(@PathVariable("id") String documentId) {

        documentService.unlockDoc(documentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }}
