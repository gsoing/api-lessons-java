package com.gso.concurrency.documents.endpoint;

import com.gso.concurrency.documents.dto.DocumentDto;
import com.gso.concurrency.documents.dto.LockDto;
import com.gso.concurrency.documents.dto.PageData;
import com.gso.concurrency.documents.model.DocumentModel;
import com.gso.concurrency.documents.model.LockModel;
import com.gso.concurrency.documents.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(DocumentController.PATH)
public class DocumentController {

    public static final String PATH = "/api/v1/documents";

    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<PageData<DocumentDto>> fetchDocuments(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        Page<DocumentModel> page = documentService.findAll(pageable);
        PageData<DocumentDto> result = PageData.fromPage(page.map(DocumentModel::toDto));
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(@Valid @RequestBody DocumentDto documentDto,
                                                      UriComponentsBuilder uriComponentsBuilder){

        DocumentModel created = documentService.createDocument(documentDto.toModel());

        UriComponents uriComponents = uriComponentsBuilder.path(DocumentController.PATH.concat("/{id}"))
                .buildAndExpand(created.getId());
        return ResponseEntity
                .created(uriComponents.toUri())
                .eTag(created.getEtag())
                .body(created.toDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> fetchDocument(@RequestHeader(value = "if-none-match", required = false) String etag,
                                                     @NotNull @PathVariable("id") String documentId) {

        DocumentModel documentModel = documentService.fetchDocument(documentId);

        if(documentModel.getEtag().equals(etag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        return ResponseEntity
                .ok()
                .eTag(documentModel.getEtag())
                .body(documentModel.toDto());
    }

    @PostMapping("/{id}")
    public ResponseEntity<DocumentDto> updateDocument(@Valid @RequestBody DocumentDto documentDto,
                                                      @NotNull @RequestHeader("if-match") String etag,
                                                      @NotNull @PathVariable("id") String documentId){
        documentDto.setDocumentId(documentId);
        DocumentModel updated = documentService.update(documentDto.toModel(), etag);

        return ResponseEntity
                .ok()
                .eTag(updated.getEtag())
                .body(updated.toDto());
    }

    @GetMapping("/{id}/lock")
    public ResponseEntity<LockDto> fetchLock(@NotNull @PathVariable("id") String documentId){
        LockModel lockModel = documentService.fetchLock(documentId);
        if(lockModel == null){
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(lockModel.toDto());
    }

    @DeleteMapping("/{id}/lock")
    public ResponseEntity unlock(@NotNull @PathVariable("id") String documentId){
        documentService.unlockDocument(documentId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<LockDto> lock(@NotNull @PathVariable("id") String documentId,
                                        @Valid @RequestBody LockDto lock){
        lock.setDocumentId(documentId);
        LockModel createdLock = documentService.lockDocument(lock.toModel());
        return ResponseEntity.ok(createdLock.toDto());
    }

}
