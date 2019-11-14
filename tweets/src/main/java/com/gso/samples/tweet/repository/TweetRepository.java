package com.gso.samples.tweet.repository;

import com.gso.samples.tweet.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TweetRepository extends MongoRepository<Tweet, String> {

    @Query("{ 'user.nickname' : ?0 }")
    /**
     * déclare une méthode avec la query, le reste est généré automatiquement par Spring Data
     */
    Page<Tweet> findByName(String nickname, Pageable pageable);

}
