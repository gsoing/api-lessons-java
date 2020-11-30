package org.gso.samples.tweets.endpoint;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.gso.samples.tweets.dto.PageData;
import org.gso.samples.tweets.dto.TweetDto;
import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.service.TweetService;
import org.gso.samples.tweets.utils.RestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
@RequestMapping(UsersController.PATH)
@RestController
public class UsersController {

    public final static String PATH = "/api/v1/users";

    private final TweetService tweetService;


    @GetMapping("/{nickname}/tweets")
    @ResponseBody
    public ResponseEntity<PageData<TweetDto>> getTweets(@PathVariable("nickname") @NotNull String nickname,
                                                        @PageableDefault(page = 0, size = 20) Pageable pageable,
                                                        UriComponentsBuilder uriComponentsBuilder) {

        Criteria criteria = where("user.nickname").is(nickname);

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
}
