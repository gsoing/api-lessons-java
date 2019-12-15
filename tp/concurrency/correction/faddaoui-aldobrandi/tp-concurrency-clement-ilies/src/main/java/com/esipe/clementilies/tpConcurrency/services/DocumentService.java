package com.esipe.clementilies.tpConcurrency.services;

import com.esipe.clementilies.tpConcurrency.Exception.NotFoundException;
import com.esipe.clementilies.tpConcurrency.models.Document;
import com.esipe.clementilies.tpConcurrency.repository.DocumentRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.LockModeType;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Lock(LockModeType.PESSIMISTIC_READ)
    public Page<Document> getDocuments(int page, int size){
        Pageable pageableQuery = PageRequest.of(page, size);
        return documentRepository.findAll(pageableQuery);
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    public Document getDocumentById(String id) throws NotFoundException {
        return documentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Document createDocument(Document document){
        return documentRepository.save(document);
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteDocumentById(String id) throws NotFoundException{
        Document document = documentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        documentRepository.delete(document);
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Document updateDocument(String id, Document updatedDocument) throws NotFoundException {
        Document document = documentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        document.setTitle(updatedDocument.getTitle());
        document.setBody(updatedDocument.getBody());
        document.setCreated(updatedDocument.getCreated());
        document.setUpdated(updatedDocument.getUpdated());
        document.setCreator(updatedDocument.getCreator());
        document.setEditor(updatedDocument.getEditor());

        return document;

    }
}
