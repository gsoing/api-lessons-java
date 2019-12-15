package com.modelisation.tp.tpmodelisation.repository;

import com.modelisation.tp.tpmodelisation.model.Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Custom Repository pour générer nos requêtes métiers plus évoluées que le simple CRUD
 */
@Repository
public class CustomDocRepositoryImpl implements CustomDocRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<Doc> findDocs(Criteria criteria, Pageable pageable) {
        Query query = new Query().addCriteria(criteria).with(pageable);
        List<Doc> results = mongoTemplate.find(query, Doc.class);
        Page<Doc> tweetPage = PageableExecutionUtils.getPage(
                results,
                pageable,
                () -> mongoTemplate.count(query, Doc.class));
        return tweetPage;
    }
}
