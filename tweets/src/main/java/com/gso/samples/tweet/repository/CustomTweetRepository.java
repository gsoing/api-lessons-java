package com.gso.samples.tweet.repository;

import com.gso.samples.tweet.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;

public interface CustomTweetRepository {

    Page<Tweet> findTweets(Criteria query, Pageable pageable);
}
