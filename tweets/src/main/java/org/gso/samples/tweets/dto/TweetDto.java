package org.gso.samples.tweets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.samples.tweets.model.Tweet;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetDto {

    public static final String ZONE_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private String id;
    @NotBlank(message = "text must not be blank")
    @Size(max = 256, message = "tweet is max 256")
    private String text;
    private Source source;
    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime created;
    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime modified;
    @NotBlank
    private String user;


    public Tweet toEntity() {
        return Tweet.builder()
                .id(id)
                .text(text)
                .source(source)
                .build();
    }
}
