package org.gso.samples.tweets.repository;

import org.gso.samples.tweets.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;

public interface CustomTweetRepository {

    Page<Tweet> findTweets(Criteria query, Pageable pageable);
}
