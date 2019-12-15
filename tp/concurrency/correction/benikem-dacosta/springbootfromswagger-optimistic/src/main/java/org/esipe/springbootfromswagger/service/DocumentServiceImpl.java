package org.esipe.springbootfromswagger.service;

import lombok.extern.slf4j.Slf4j;
import org.esipe.springbootfromswagger.exception.NotFoundException;
import org.esipe.springbootfromswagger.model.Document;
import org.esipe.springbootfromswagger.model.DocumentList;
import org.esipe.springbootfromswagger.model.DocumentSummary;
import org.esipe.springbootfromswagger.model.Lock;
import org.esipe.springbootfromswagger.persistance.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DocumentServiceImpl {

    private DocumentRepository documentRepository;

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document save(Document document) {
        document.setCreator(System.getProperty("user.name"));
        return this.documentRepository.save(document);
    }

    public Document findById(String id) {
        return this.documentRepository.findById(id).orElseThrow(() -> NotFoundException.DEFAULT);
    }

    public DocumentList findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Document> pages = this.documentRepository.findAll(pageable);
        List<Document> documents = pages.getContent();
        List<DocumentSummary> documentSummaries = new ArrayList<>();
        for (Document document : documents) {
            documentSummaries.add(new DocumentSummary(document.getDocumentId(), document.getCreated(), document.getUpdated(), document.getTitle()));
        }
        return new DocumentList(page, documents.size(), documentSummaries);
    }

    public Document updateById(Document document, String id) {
        Document doc = this.findById(id);
        doc.setTitle(document.getTitle());
        doc.setBody(document.getBody());
        doc.setEditor(System.getProperty("user.name"));
        doc.setUpdated(document.getUpdated());
        return this.documentRepository.save(doc);
    }

    public Lock getDocumentLock(String documentId) {
        Document document = findById(documentId);
        if (document.getLock() != null) {
            return document.getLock();
        }
        return null;
    }

    public Lock putDocumentLock(Lock lock, String documentId) {
        Document document = findById(documentId);
        Lock result = getDocumentLock(documentId);
        if (result != null)
            return result;
        else {
            if (lock.getId() == null)
                lock.setId(UUID.randomUUID().toString());
            if (lock.getCreated() == null)
                lock.setCreated(LocalDateTime.now());
            if (lock.getOwner() == null)
                lock.setOwner(System.getProperty("user.name"));
            document.setLock(lock);
            this.documentRepository.save(document);
            return lock;
        }
    }

    public void deleteDocumentLock(String documentId) {
        Document document = findById(documentId);
        if (document.getLock() != null) {
            document.setLock(null);
            documentRepository.save(document);
        }
    }
}
