package com.gso.concurrency.documents.dto;

import com.gso.concurrency.documents.model.DocumentModel;
import com.gso.concurrency.documents.utils.RestUtils;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@Builder
public class DocumentDto {

    private String documentId;
    private ZonedDateTime created;
    private ZonedDateTime updated;
    @NotNull
    private String title;
    @NotNull
    private String creator;
    private String editor;
    @NotNull
    private String body;

    public DocumentModel toModel() {
        return DocumentModel.builder()
                .id(this.documentId)
                .created(RestUtils.convertToLocalDateTime(created))
                .updated(RestUtils.convertToLocalDateTime(updated))
                .title(this.title)
                .creator(this.creator)
                .editor(this.editor)
                .body(this.body)
                .build();
    }
}
