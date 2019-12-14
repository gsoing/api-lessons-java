package com.gso.concurrency.documents.repository;

import com.gso.concurrency.documents.exception.PreconditionFailedException;
import com.gso.concurrency.documents.model.DocumentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DocumentCustomRepositoryImpl implements DocumentCustomRepository {

    MongoTemplate mongoTemplate;

    /**
     * Update the document only if the incoming etag match the one in the database
     * Read well it's tricky
     */
    @Override
    public DocumentModel findAndUpdate(DocumentModel document, String etag) {
        // We will use a find an update to update the document only if we find id with the same etag
        // As mongo do not have select for update option
        // an update can be done during the time
        Optional<DocumentModel> updated = mongoTemplate.update(DocumentModel.class)
                .matching(Query.query(Criteria.where("id").is(document.getId()))
                        .addCriteria(Criteria.where("etag").is(etag)))
                .replaceWith(DocumentModel.builder()
                        .body(document.getBody())
                        .title(document.getTitle())
                        .editor(document.getEditor())
                        .build())
                .withOptions(FindAndReplaceOptions.options().returnNew())
                .findAndReplace();
        return updated.orElseThrow(PreconditionFailedException::new);
    }
}
