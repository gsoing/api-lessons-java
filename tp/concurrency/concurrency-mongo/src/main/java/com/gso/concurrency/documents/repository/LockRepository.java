package com.gso.concurrency.documents.repository;

import com.gso.concurrency.documents.model.LockModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockRepository extends MongoRepository<LockModel, String> {

    LockModel getLockModelByDocumentId(String documentId);

    void deleteLockModelByDocumentId(String documentId);
}
