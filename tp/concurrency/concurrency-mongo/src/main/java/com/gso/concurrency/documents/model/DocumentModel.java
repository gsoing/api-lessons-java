package com.gso.concurrency.documents.model;

import com.gso.concurrency.documents.dto.DocumentDto;
import com.gso.concurrency.documents.utils.RestUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Document
public class DocumentModel {

    @Id
    private String id;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime updated;
    @NotNull
    private String title;
    @NotNull
    private String creator;
    private String editor;
    @NotNull
    private String body;
    private String etag;


    public DocumentDto toDto(){
        return DocumentDto
                .builder()
                .documentId(this.id)
                .body(this.body)
                .created(RestUtils.convertToZoneDateTime(created))
                .updated(RestUtils.convertToZoneDateTime(updated))
                .title(this.title)
                .creator(this.creator)
                .editor(this.editor)
                .build();
    }
}
