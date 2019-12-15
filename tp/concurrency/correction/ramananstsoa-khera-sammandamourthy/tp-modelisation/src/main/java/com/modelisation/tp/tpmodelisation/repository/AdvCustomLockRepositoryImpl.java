package com.modelisation.tp.tpmodelisation.repository;

import com.modelisation.tp.tpmodelisation.model.CustomLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

public class AdvCustomLockRepositoryImpl implements AdvCustomLockRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<CustomLock> findByDocId(Criteria query, Pageable pageable) {
        return null;
    }
}
