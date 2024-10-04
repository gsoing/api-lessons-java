package org.gso.samples.tweets.repository;

import org.gso.samples.tweets.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Custom Repository pour générer nos requêtes métiers plus évoluées que le simple CRUD
 */
@Repository
public class CustomTweetRepositoryImpl implements CustomTweetRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<Tweet> findTweets(Criteria criteria, Pageable pageable) {
        Query query = new Query().addCriteria(criteria).with(pageable);
        List<Tweet> results = mongoTemplate.find(query, Tweet.class);
        Page<Tweet> tweetPage = PageableExecutionUtils.getPage(
                results,
                pageable,
                () -> mongoTemplate.count(query, Tweet.class));
        return tweetPage;
    }
}
