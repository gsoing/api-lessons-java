package com.gso.concurrency.documents.service;

import com.gso.concurrency.documents.exception.ConflictException;
import com.gso.concurrency.documents.exception.NotFoundException;
import com.gso.concurrency.documents.exception.PreconditionFailedException;
import com.gso.concurrency.documents.model.DocumentModel;
import com.gso.concurrency.documents.model.LockModel;
import com.gso.concurrency.documents.repository.DocumentCustomRepository;
import com.gso.concurrency.documents.repository.DocumentRepository;
import com.gso.concurrency.documents.repository.LockCustomRepository;
import com.gso.concurrency.documents.repository.LockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentCustomRepository documentCustomRepository;
    private final LockRepository lockRepository;
    private final LockCustomRepository lockCustomRepository;
    private final ExpirableLockRegistry expirableLockRegistry;

    public Page<DocumentModel> findAll(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    public DocumentModel createDocument(DocumentModel document) {
        return documentRepository.insert(document);
    }

    public DocumentModel update(DocumentModel document, String etag) {
        //First try to find the document in the repository
        DocumentModel d = fetchDocument(document.getId());

        //then we check the etag
        if(d.getEtag().equals(etag)) {
            throw new PreconditionFailedException();
        }

        //if it's good we update the document
        DocumentModel updatedDocument = documentCustomRepository.findAndUpdate(document, etag);
        return updatedDocument;
    }

    public DocumentModel fetchDocument(String id){
        log.debug("try to fetch document {}", id);
        return documentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public LockModel fetchLock(String documentId) {
        DocumentModel documentModel = fetchDocument(documentId);
        return lockRepository.getLockModelByDocumentId(documentId);
    }

    private LockModel lock(LockModel lockModel){
        LockModel existingLock = lockRepository.getLockModelByDocumentId(lockModel.getDocumentId());

        if(existingLock != null) {
            throw new ConflictException(existingLock);
        }

        return lockCustomRepository.lock(lockModel);
    }

    private void unlock(String documentId) {
        lockRepository.deleteLockModelByDocumentId(documentId);
    }

    public void unlockDocument(String documentId){
        fetchDocument(documentId);
        unlock(documentId);
    }

    public LockModel lockDocument(LockModel lockModel) {
        fetchDocument(lockModel.getDocumentId());
        return lock(lockModel);
    }
}
