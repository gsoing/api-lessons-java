package com.modelisation.tp.tpmodelisation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.modelisation.tp.tpmodelisation.model.CustomLock;
import com.modelisation.tp.tpmodelisation.model.Doc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * DocDto
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocDto {

    public static final String ZONE_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public String customLock;
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private String documentId;
    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime created;
    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime updated;
    @NotBlank(message = "title must not be blank")
    @NotNull
    private String title;
    @NotBlank(message = "creator must not be blank")
    @NotNull
    private String creator;
    @NotBlank(message = "editor must not be blank")
    @NotNull
    private String editor;
    @NotBlank(message = "body must not be blank")
    @NotNull
    private String body;
    @Version
    private Long version;

    public Doc toEntity() {
        return Doc.builder()
                .documentId(documentId)
                .title(title)
                .creator(creator)
                .editor(editor)
                .body(body)
                .customLock(customLock)
                .version(version)
                .build();
    }
}
