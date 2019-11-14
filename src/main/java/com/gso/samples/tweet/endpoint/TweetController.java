package com.gso.samples.tweet.endpoint;

import com.gso.samples.tweet.dto.PageData;
import com.gso.samples.tweet.dto.TweetDto;
import com.gso.samples.tweet.model.Tweet;
import com.gso.samples.tweet.service.TweetService;
import com.gso.samples.tweet.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping(TweetController.PATH)
@SuppressWarnings("unused")
public class TweetController {

    public static final String PATH = "/api/v1/tweets";

    @Autowired
    private TweetService tweetService;

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

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    @ResponseBody
    public ResponseEntity<PageData<TweetDto>> getTweets(@RequestParam(required = false) String query,
                                                        @PageableDefault(page = 0, size = 20) Pageable pageable,
                                                        UriComponentsBuilder uriComponentsBuilder) {

        Page<Tweet> results = tweetService.getTweets(query, pageable);
        PageData<TweetDto> pageResult = PageData.fromPage(results.map(Tweet::toDto));
        if (RestUtils.hasNext(results, pageable)) {
            pageResult.setNext(RestUtils.buildNextUri(uriComponentsBuilder, pageable));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(pageResult);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TweetDto>
    createTweet(@Valid @RequestBody TweetDto tweet, UriComponentsBuilder uriComponentsBuilder) {

        Tweet createdTweet = tweetService.createTweet(tweet.toEntity());
        UriComponents uriComponents = uriComponentsBuilder.path(TweetController.PATH.concat("/{id}"))
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

    @ExceptionHandler()
    public ResponseEntity<> handleException() {

    }
}
