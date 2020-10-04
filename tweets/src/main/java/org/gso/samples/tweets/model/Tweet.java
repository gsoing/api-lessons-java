package org.gso.samples.tweets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.samples.tweets.dto.Source;
import org.gso.samples.tweets.dto.TweetDto;
import org.gso.samples.tweets.utils.RestUtils;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Tweet {

    @Id
    private String id;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime modified;
    @NotBlank(message = "text must not be blank")
    @Size(max = 256, message = "tweet is max 256 characters")
    private String text;
    @NotNull
    private Source source;
    @NotBlank
    private User user;
    @Transient
    private String etag;

    /**
     * Constructeur utilisé par Spring data
     * On doit le définir car l'attribut etag ne fait pas partie du modèle
     * @param id
     * @param text
     * @param source
     * @param created
     * @param modified
     */
    @PersistenceConstructor
    public Tweet(String id, String text, User user,
                 Source source, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.text = text;
        this.source = source;
        this.created = created;
        this.user = user;
        this.modified = modified;
    }

    public TweetDto toDto() {
        return TweetDto.builder()
                .id(id)
                .text(text)
                .user(user.toDto())
                .created(RestUtils.convertToZoneDateTime(created))
                .modified(RestUtils.convertToZoneDateTime(modified))
                .source(source)
                .build();
    }
}
