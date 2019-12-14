package com.gso.concurrency.documents.repository;

import com.gso.concurrency.documents.model.DocumentModel;

public interface DocumentCustomRepository {

    DocumentModel findAndUpdate(DocumentModel document, String etag);
}
