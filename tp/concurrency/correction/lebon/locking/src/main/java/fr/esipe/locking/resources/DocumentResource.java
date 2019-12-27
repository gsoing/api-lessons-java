package fr.esipe.locking.resources;

import fr.esipe.locking.dtos.DocumentDto;
import fr.esipe.locking.dtos.DocumentSummary;
import fr.esipe.locking.dtos.LockDto;
import fr.esipe.locking.dtos.PageData;
import fr.esipe.locking.entities.DocumentEntity;
import fr.esipe.locking.entities.LockEntity;
import fr.esipe.locking.exception.BadRequestException;
import fr.esipe.locking.exception.NotFoundException;
import fr.esipe.locking.services.DocumentService;
import fr.esipe.locking.utils.CommonUtil;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Slf4j
@Validated
@RestController
@EnableWebMvc
@RequestMapping(DocumentResource.PATH)
@SuppressWarnings("unused")
public class DocumentResource {

    public static final String PATH = "api/v1/documents";

    private final DocumentService documentService;

    @Autowired
    public DocumentResource(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<DocumentDto>
    createTweet(@Valid @RequestBody DocumentDto documentDto, UriComponentsBuilder uriComponentsBuilder) throws Exception {

        DocumentEntity documentEntity = documentService.createDocument(documentDto.toEntity());
        UriComponents uriComponents = uriComponentsBuilder.path(PATH.concat("/{id}"))
                .buildAndExpand(documentEntity.getDocumentId());

        DocumentDto createdDocumentDto = documentEntity.toDto();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .lastModified(createdDocumentDto.getUpdated())
                .eTag(documentEntity.getEtag())
                .location(uriComponents.toUri())
                .body(createdDocumentDto);
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllDocuments(@PageableDefault(size = 20) Pageable pageable, UriComponentsBuilder uriComponentsBuilder) {

        Page<DocumentEntity> results = documentService.getAllDocuments(pageable);
        PageData<DocumentSummary> pageResult = PageData.fromPage(results.map(DocumentEntity::toSummary));

        if (CommonUtil.hasNext(results, pageable)) {
            pageResult.setNext(CommonUtil.buildNextUri(uriComponentsBuilder.path(PATH), pageable));
        }

        if (pageResult.getNbElements() <= 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(pageResult);
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<?> detailDocument(@PathVariable("id") String documentId) throws Exception {

        Optional<DocumentEntity> optDocument = documentService.findDocument(documentId);

        // Pour le coup la gestion fonctionnelle est plutôt à faire dans le service qui lui devrait lever l'exception
        if (!optDocument.isPresent()) {
            return NotFoundException.getError();
        }

        DocumentEntity documentEntity = optDocument.get();
        DocumentDto documentDto = documentEntity.toDto();

        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(documentDto.getUpdated())
                .eTag(documentEntity.getEtag())
                .cacheControl(CacheControl.maxAge(20, TimeUnit.MINUTES))
                .body(documentDto);
    }

    @ResponseBody
    @PostMapping("/{id}")
    public ResponseEntity<?> updateDocument(@RequestHeader("If-Match") String ifMatchValue,
                                            @PathVariable("id") String documentId,
                                            @Valid @RequestBody DocumentDto documentDto) throws Exception {

        Optional<DocumentEntity> optDocument = documentService.findDocument(documentId);

        if (!optDocument.isPresent()) {
            return NotFoundException.getError();
        }

        if (null == ifMatchValue || ifMatchValue.isEmpty()) {
            return BadRequestException.getError("If-Match header must not be blank");
        }

        DocumentEntity oldDocument = optDocument.get();
        if (!ifMatchValue.equals(oldDocument.getEtag())) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }

        DocumentEntity documentEntity = documentDto.toEntity();

        Optional<DocumentEntity> optUpdatedDocument = documentService.updateDocument(documentId, documentEntity);

        if (!optUpdatedDocument.isPresent()) {
            return BadRequestException.getError("Unable to update the document");
        }

        DocumentEntity updatedDocumentEntity = optUpdatedDocument.get();

        DocumentDto updatedDocumentDto = updatedDocumentEntity.toDto();

        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(updatedDocumentDto.getUpdated())
                .eTag(updatedDocumentEntity.getEtag())
                .body(updatedDocumentDto);
    }

    @GetMapping("/{id}/lock")
    public ResponseEntity<?> getDocumentLock(@PathVariable("id") String documentId) {

        Optional<LockEntity> optLock = documentService.getLock(documentId);

        if (!optLock.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        LockDto lockDto = optLock.get().toDto();

        return ResponseEntity.ok(lockDto);
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<?> lockDocument(@PathVariable("id") String documentId, @RequestBody @Valid LockDto lockDto) throws Exception {

        Optional<DocumentEntity> optDocument = documentService.findDocument(documentId);

        if (!optDocument.isPresent()) {
            return NotFoundException.getError();
        }

        LockEntity lock = optDocument.get().getLock();
        if (lock != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(lock.toDto());
        }

        Optional<LockEntity> optLockEntity = documentService.lockDocument(documentId, lockDto.toEntity());

        if (!optLockEntity.isPresent()) {
            return BadRequestException.getError("Unable to lock this document");
        }

        LockDto createdLock = optLockEntity.get().toDto();

        return ResponseEntity.ok(createdLock);
    }

    @DeleteMapping("/{id}/lock")
    public ResponseEntity<?> deleteDocumentLock(@PathVariable("id") String documentId) {

        documentService.unlockDocument(documentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
