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
    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();


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

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    @ResponseBody
    public ResponseEntity<PageData<TweetDto>> getTweets(@RequestParam(required = false) String query,
                                                        @PageableDefault(page = 0, size = 20) Pageable pageable,
                                                        UriComponentsBuilder uriComponentsBuilder) {

        Criteria criteria = convertQuery(query);

        Page<Tweet> results = tweetService.getTweets(criteria, pageable);
        PageData<TweetDto> pageResult = PageData.fromPage(results.map(Tweet::toDto));
        if (RestUtils.hasNext(results, pageable)) {
            pageResult.setNext(RestUtils.buildNextUri(uriComponentsBuilder, pageable));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.noCache())
                .body(pageResult);
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

}
