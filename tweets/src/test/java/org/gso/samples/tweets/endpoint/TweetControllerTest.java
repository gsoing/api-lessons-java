package org.gso.samples.tweets.endpoint;

import java.util.List;

import org.gso.samples.tweets.model.Tweet;
import org.gso.samples.tweets.model.User;
import org.gso.samples.tweets.service.TweetService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    public void whenTweetControlletInjected_thenNotNull() throws Exception {
        assertThat(tweetsController).isNotNull();
    }

    @Test
    public void whenNoTweet_thenResponseEmpty() throws Exception {
        Page<Tweet> page = Page.empty();
        given(tweetService.getTweets(new Criteria(), PageRequest.of(0, 20))).willReturn(page);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tweets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
