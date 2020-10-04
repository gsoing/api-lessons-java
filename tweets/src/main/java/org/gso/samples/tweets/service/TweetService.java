package org.gso.samples.tweets.service;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.samples.tweets.exception.NotFoundException;
import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.repository.CustomTweetRepository;
import org.gso.samples.tweets.repository.TweetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
/**
 * Cette classe contient la logique métier à appliquer à nos tweets
 */
public class TweetService {

    private final TweetRepository tweetRepository;
    private final CustomTweetRepository customTweetRepository;

    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();


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

    public Page<Tweet> getTweets(String stringQuery, Pageable pageable) {
        Criteria criteria = convertQuery(stringQuery);
        Page<Tweet> results = customTweetRepository.findTweets(criteria, pageable);
        return results;
    }

    /**
     * Convertit une requête RSQL en un objet Criteria compréhensible par la base
     * @param stringQuery
     * @return
     */
    private Criteria convertQuery(String stringQuery){
        Criteria criteria;
        if(!StringUtils.isEmpty(stringQuery)) {
            Condition<GeneralQueryBuilder> condition = pipeline.apply(stringQuery, Tweet.class);
            criteria = condition.query(new MongoVisitor());
        } else {
            criteria = new Criteria();
        }
        return criteria;
    }

    public Page<Tweet> getTweetsByNickname(String nickname, Pageable pageable) {
        return tweetRepository.findByName(nickname, pageable);
    }
}
