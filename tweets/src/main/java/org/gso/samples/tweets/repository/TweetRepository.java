package org.gso.samples.tweets.repository;

import org.gso.samples.tweets.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepository extends MongoRepository<Tweet, String> {
}
