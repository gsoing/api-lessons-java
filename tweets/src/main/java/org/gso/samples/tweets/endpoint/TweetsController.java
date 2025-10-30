package org.gso.samples.tweets.endpoint;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.samples.tweets.dto.PageData;
import org.gso.samples.tweets.dto.TweetDto;
import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.service.TweetService;
import org.gso.samples.tweets.utils.RestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Rest controller pour l'url /api/v1/tweets
 */
@Slf4j
@Validated
@RestController
@EnableWebMvc
@RequiredArgsConstructor
@RequestMapping(TweetsController.PATH)
@SuppressWarnings("unused")
public class TweetsController {

    public static final String PATH = "/api/v1/tweets";

    private final TweetService tweetService;
    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();


    @PostMapping
    @ResponseBody
    public ResponseEntity<TweetDto>
    createTweet(@Valid @RequestBody TweetDto tweet, UriComponentsBuilder uriComponentsBuilder) {

        Tweet createdTweet = tweetService.createTweet(tweet.toEntity());
        UriComponents uriComponents = uriComponentsBuilder.path(TweetsController.PATH.concat("/{id}"))
                .buildAndExpand(createdTweet.getId());

        TweetDto createdTweetDto = createdTweet.toDto();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag(createdTweet.getEtag())
                .lastModified(createdTweetDto.modified())
                .location(uriComponents.toUri())
                .body(createdTweetDto);
    }
    

    /**
     * Convertit une requête RSQL en un objet Criteria compréhensible par la base
     * @param stringQuery
     * @return
     */
    private Criteria convertQuery(String stringQuery){
        Criteria criteria;
        if(!StringUtils.hasText(stringQuery)) {
            Condition<GeneralQueryBuilder> condition = pipeline.apply(stringQuery, Tweet.class);
            criteria = condition.query(new MongoVisitor());
        } else {
            criteria = new Criteria();
        }
        return criteria;
    }

}
