package org.gso.samples.tweets.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.samples.tweets.dto.TweetDto;
import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.service.TweetService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
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

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, path = "/{id}")
    @ResponseBody
    public ResponseEntity<TweetDto> getTweet(@PathVariable("id") String tweetId) {

        Tweet tweet = tweetService.getTweet(tweetId);

        TweetDto tweetDto = tweet.toDto();

        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(tweet.getEtag())
                .cacheControl(CacheControl.maxAge(10, TimeUnit.HOURS))
                .lastModified(tweetDto.getModified())
                .body(tweetDto);
    }

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
                .lastModified(createdTweetDto.getModified())
                .location(uriComponents.toUri())
                .body(createdTweetDto);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<TweetDto>
    updateTweet(@PathVariable("id") String tweetId, @Valid @RequestBody TweetDto tweetDto) {

        Tweet tweet = tweetDto.toEntity();
        tweet.setId(tweetId);
        Tweet updatedTweet = tweetService.updateTweet(tweet);

        TweetDto updatedTweetDto = updatedTweet.toDto();

        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(updatedTweetDto.getModified())
                .eTag(updatedTweet.getEtag())
                .body(updatedTweetDto);
    }
}
