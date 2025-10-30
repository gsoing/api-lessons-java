package org.gso.samples.tweets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.gso.samples.tweets.model.Tweet;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TweetDto (
        String id,
        @NotBlank(message = "text must not be blank") @Size(max = 256, message = "tweet is max 256") String text,
        Source source,
        ZonedDateTime created,
        ZonedDateTime modified,
        @NotNull UserDto user) {


    public Tweet toEntity() {
        return Tweet.builder()
                .id(id)
                .text(text)
                .source(source)
                .user(user.toEntity())
                .build();
    }
}
