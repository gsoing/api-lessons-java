package org.gso.samples.tweets.endpoint;

import org.gso.samples.tweets.dto.Source;
import org.gso.samples.tweets.exception.NotFoundException;
import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.model.User;
import org.gso.samples.tweets.service.TweetService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@WebMvcTest(TweetsController.class)
public class TweetControllerTest {

    @Autowired
    private TweetsController tweetsController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetService tweetService;

    @Test
    public void whenTweetControllerInjected_thenNotNull() throws Exception {
        assertThat(tweetsController).isNotNull();
    }

    @Test
    public void whenNoTweet_thenResponseEmpty() throws Exception {
        Page<Tweet> page = Page.empty();
        given(tweetService.getTweets(new Criteria(), PageRequest.of(0, 20))).willReturn(page);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tweets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenTweetNotFound_thenReturnNotFound() throws Exception {
        given(tweetService.getTweet("123456")).willThrow(NotFoundException.DEFAULT);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tweets/123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void whenPageSize1_ThenReturnFirstItem() throws Exception {
        Page<Tweet> page = new PageImpl(List.of(
                Tweet.builder()
                        .text("toto")
                        .source(Source.WEB)
                        .created(LocalDateTime.now())
                        .modified(LocalDateTime.now())
                        .user(User.builder().nickname("toto").mail("toto@yopmail.com").build()).build(),
                Tweet.builder()
                        .text("titi")
                        .source(Source.WEB)
                        .created(LocalDateTime.now())
                        .modified(LocalDateTime.now())
                        .user(User.builder().nickname("titi").mail("titi@yopmail.com").build()).build()
        ));
        given(tweetService.getTweets(new Criteria(), PageRequest.of(0, 2))).willReturn(page);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tweets?size=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
