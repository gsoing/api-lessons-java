package com.tplock.locking.dto;

import com.tplock.locking.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentDto extends MongoRepository<Document,String> {
    public Document findDocumentByDocumentId(String documentId);

}