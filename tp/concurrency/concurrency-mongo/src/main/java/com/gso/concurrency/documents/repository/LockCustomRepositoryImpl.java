package com.gso.concurrency.documents.repository;

import com.gso.concurrency.documents.exception.ConflictException;
import com.gso.concurrency.documents.model.LockModel;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class LockCustomRepositoryImpl implements LockCustomRepository {

    protected MongoTemplate mongoTemplate;

    public LockCustomRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public LockModel lock(LockModel lockModel) {

        LockModel updated = mongoTemplate.findAndModify(
                Query.query(Criteria.where("documentId").is(lockModel.getDocumentId())),
                new Update()
                        .setOnInsert("documentId", lockModel.getDocumentId())
                        .setOnInsert("owner", lockModel.getOwner())
                        .setOnInsert("created", LocalDateTime.now()),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                LockModel.class);

        // As we set the owner only on insert if the owner is different that mean we do not have the lock
        if(!updated.getOwner().equals(lockModel.getOwner())) {
            throw new ConflictException(updated);
        }

        return updated;
    }
}
