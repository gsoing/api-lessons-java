package org.gso.samples.tweets.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.samples.tweets.exception.NotFoundException;
import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.repository.TweetRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
/**
 * Cette classe contient la logique métier à appliquer à nos tweets
 */
public class TweetService {

    private final TweetRepository tweetRepository;

    public Tweet getTweet(String id) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(()-> NotFoundException.DEFAULT);
        return tweet;
    }

    public Tweet createTweet(Tweet tweet){
        log.debug("tweedto {}", tweet.toString());
        Tweet insertedTweet = tweetRepository.insert(tweet);
        return insertedTweet;
    }

    /**
     * Mise à jour d'un tweet
     * @param updateTweet
     * @return
     */
    public Tweet updateTweet(Tweet updateTweet) {
        // on cherche d'abord le tweet si il est présent
        Tweet tweet = tweetRepository.findById(updateTweet.getId()).orElseThrow(() -> NotFoundException.DEFAULT);
        // on controle les champs que l'on veut mettre à jour
        tweet.setSource(updateTweet.getSource());
        tweet.setText(updateTweet.getText());
        tweet.setUser(updateTweet.getUser());
        // on le sauvegarde
        return tweetRepository.save(tweet);
    }

}
