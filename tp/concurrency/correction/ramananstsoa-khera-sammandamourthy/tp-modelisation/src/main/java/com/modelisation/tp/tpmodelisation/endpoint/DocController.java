package com.modelisation.tp.tpmodelisation.endpoint;

import com.modelisation.tp.tpmodelisation.dto.CustomLockDto;
import com.modelisation.tp.tpmodelisation.dto.DocDto;
import com.modelisation.tp.tpmodelisation.dto.PageData;
import com.modelisation.tp.tpmodelisation.model.CustomLock;
import com.modelisation.tp.tpmodelisation.model.Doc;
import com.modelisation.tp.tpmodelisation.service.DocService;
import com.modelisation.tp.tpmodelisation.service.LockService;
import com.modelisation.tp.tpmodelisation.utils.RestUtils;
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
import java.util.concurrent.TimeUnit;

@Slf4j
@Validated
@RestController
@EnableWebMvc
@RequiredArgsConstructor
@RequestMapping(DocController.PATH)
@SuppressWarnings("unused")
public class DocController {

    public static final String PATH = "/api/docs";

    @Autowired
    private DocService docService;

    @Autowired
    private LockService lockService;


    /**
     * Get all documents
     *
     * @param query
     * @param pageable
     * @param uriComponentsBuilder
     * @return ResponseEntity
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    @ResponseBody
    public ResponseEntity<PageData<DocDto>> getDocuments(@RequestParam(required = false) String query,
                                                         @PageableDefault(page = 0, size = 3) Pageable pageable,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        Page<Doc> results = docService.getDocuments(query, pageable);
        PageData<DocDto> pageResult = PageData.fromPage(results.map(Doc::toDto));
        if (RestUtils.hasNext(results, pageable)) {
            pageResult.setNext(RestUtils.buildNextUri(uriComponentsBuilder, pageable));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(pageResult);
    }

    /**
     * Get document
     *
     * @param documentId
     * @return document
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, path = "/{id}")
    @ResponseBody
    public ResponseEntity<DocDto> getDocument(@PathVariable("id") String documentId) {
        Doc document = docService.getDocument(documentId);
        DocDto docDto = document.toDto();
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(document.getEtag())
                .cacheControl(CacheControl.maxAge(10, TimeUnit.HOURS))
                .lastModified(docDto.getUpdated())
                .body(docDto);
    }

    /**
     * Create a document
     *
     * @param doc
     * @param uriComponentsBuilder
     * @return ResponseEntity
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<DocDto>
    createDoc(@Valid @RequestBody DocDto doc, UriComponentsBuilder uriComponentsBuilder) {
        doc.setCustomLock("unlocked");
        Doc createdDoc = docService.createDoc(doc.toEntity());
        UriComponents uriComponents = uriComponentsBuilder.path(DocController.PATH.concat("/{id}"))
                .buildAndExpand(createdDoc.getDocumentId());
        DocDto createdDocDto = createdDoc.toDto();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag(createdDoc.getEtag())
                .lastModified(createdDocDto.getUpdated())
                .location(uriComponents.toUri())
                .body(createdDocDto);
    }

    /**
     * Update a document
     *
     * @param documentId
     * @param docDto
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<DocDto>
    updateDoc(@PathVariable("id") String documentId, @Valid @RequestBody DocDto docDto) {
        String lock = docService.getDocument(documentId).getCustomLock();
        if (!lock.equals("unlocked")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        } else {
            Long basicVersion = docService.getDocument(documentId).getVersion();
            docDto.setCustomLock(lock);
            Doc doc = docDto.toEntity();
            doc.setDocumentId(documentId);
            Doc updatedDoc = docService.updateDoc(doc);
            DocDto updatedDocDto = updatedDoc.toDto();
            Long afterVersion = docService.getDocument(documentId).getVersion();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .lastModified(updatedDocDto.getUpdated())
                    .eTag(updatedDoc.getEtag())
                    .body(updatedDocDto);
        }
    }

    /**
     * Get lock by document id
     *
     * @param documentId
     * @return ResponseEntity
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, path = "/{documentId}/lock")
    @ResponseBody
    public ResponseEntity<CustomLockDto>
    getLockByDocumentId(@PathVariable("documentId") String documentId) {
        Doc document = docService.getDocument(documentId);
        CustomLock lock = lockService.getLock(document.getCustomLock());
        if (lock != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lock.toDto());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
    }

    /**
     * Get all locks
     *
     * @return locks
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, path = "/locks")
    @ResponseBody
    public Iterable<CustomLock> getLocks() {
        return lockService.findAll();
    }

    /**
     * Create lock in document
     *
     * @param documentId
     * @param lockDto
     * @param uriComponentsBuilder
     * @return ResponseEntity
     * @throws InterruptedException
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD}, path = "/{documentId}/lock")
    @ResponseBody
    public ResponseEntity<CustomLockDto> createLock(@PathVariable("documentId") String documentId, @Valid @RequestBody CustomLockDto lockDto, UriComponentsBuilder uriComponentsBuilder) throws InterruptedException {
        Doc doc = docService.getDocument(documentId);
        if (!doc.getCustomLock().equals("unlocked")) {
            String customLockId = doc.getCustomLock();
            CustomLock lock = lockService.getLock(customLockId);
            CustomLockDto dtoLock = lock.toDto();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(dtoLock);
        } else {
            CustomLock createdLock = lockDto.toEntity();
            Doc docWithLock = docService.addLock(doc, createdLock.getOwner());
            CustomLockDto lock = lockService.getLock(docWithLock.getCustomLock()).toDto();
            UriComponents uriComponents = uriComponentsBuilder.path(DocController.PATH.concat("/{documentId}/lock"))
                    .buildAndExpand(createdLock.getId());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .location(uriComponents.toUri())
                    .body(lock);
        }
    }

    /**
     * Unlock document
     *
     * @param documentId
     * @return ResponseEntity
     * @throws InterruptedException
     */
    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.HEAD}, path = "/{documentId}/lock")
    @ResponseBody
    public ResponseEntity<Object> deleteLock(@PathVariable("documentId") String documentId) throws InterruptedException {
        Doc doc = docService.getDocument(documentId);
        lockService.deleteLock(doc.getCustomLock());
        doc.setCustomLock("unlocked");
        docService.updateDoc(doc);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);

    }


}
