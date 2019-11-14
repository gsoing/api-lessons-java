package com.gso.samples.tweet.endpoint;

import com.gso.samples.tweet.service.TweetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class TweetControllerTest {

    @MockBean
    private TweetService tweetService;

    @Autowired
    private TweetController tweetController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenTweetControlletInjected_thenNotNull() throws Exception {
        assertThat(tweetController).isNotNull();
    }

//    @Test
//    public void whenNoTweet_thenResponseEmpty() throws Exception {
//        Page<TweetDto> page = Page.<TweetDto>empty();
//        page.getContent().add(TweetDto.builder().text("toto").build());
//        given(tweetService.getTweets(null, Pageable.unpaged())).willReturn(page);
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tweets")
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
//    }
}
