package com.gso.concurrency.documents.repository;

import com.gso.concurrency.documents.model.DocumentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<DocumentModel, String> {

}
