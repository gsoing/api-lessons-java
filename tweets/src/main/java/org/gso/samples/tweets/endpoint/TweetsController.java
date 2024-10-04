package org.gso.samples.tweets.endpoint;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.samples.tweets.dto.PageData;
import org.gso.samples.tweets.dto.TweetDto;
import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.service.TweetService;
import org.gso.samples.tweets.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.concurrent.TimeUnit;

/**
 * Rest controller pour l'url /api/v1/tweets
 */
@Slf4j
@Validated
@RestController
@EnableWebMvc
@RequestMapping(TweetsController.PATH)
@SuppressWarnings("unused")
public class TweetsController {

    private final TweetService tweetService;

    public static final String PATH = "/api/v1/tweets";

    TweetsController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<TweetDto> createTweet(@NotNull @RequestBody TweetDto tweetDto) {
        Tweet tweet = tweetService.createTweet(tweetDto.toEntity());
        return ResponseEntity.created(RestUtils.buildLocation(PATH, tweet.getId()))
                .body(tweet.toDto());
    }

}
