package com.modelisation.tp.tpmodelisation.model;

import com.modelisation.tp.tpmodelisation.dto.DocDto;
import com.modelisation.tp.tpmodelisation.repository.CascadeSave;
import com.modelisation.tp.tpmodelisation.utils.RestUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Document Doc
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "doc")
public class Doc {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private String documentId;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime updated;
    @NotNull
    @NotBlank(message = "title must not be blank")
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
    @Transient
    private String etag;
    @Version
    private Long version;
    @NotBlank
    public String customLock;

    /**
     * Persistence Constructor
     *
     * @param documentId
     * @param created
     * @param updated
     * @param title
     * @param creator
     * @param editor
     * @param body
     * @param version
     */
    @PersistenceConstructor
    public Doc(String documentId, LocalDateTime created, LocalDateTime updated, String title, String creator, String editor, String body, String customLock, Long version) {
        this.documentId = documentId;
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
        this.customLock=customLock;
        this.version = version;
    }

    /**
     * DocDto
     *
     * @return DocDto
     */
    public DocDto toDto() {
        return DocDto.builder()
                .documentId(documentId)
                .created(RestUtils.convertToZoneDateTime(created))
                .updated(RestUtils.convertToZoneDateTime(updated))
                .title(title)
                .creator(creator)
                .editor(editor)
                .body(body)
                .customLock(customLock)
                .version(version)
                .build();
    }
}