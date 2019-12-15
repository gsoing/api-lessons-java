package org.esipe.springbootfromswagger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@org.springframework.data.mongodb.core.mapping.Document(collection = "document")
public class Document {
    @Id
    private String documentId;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime updated;
    @NotBlank
    private String title;
    @CreatedBy
    private String creator;
    @LastModifiedBy
    private String editor;
    @NotBlank
    private String body;
    private Lock lock;

    @PersistenceConstructor
    public Document(LocalDateTime created, LocalDateTime updated, @NotBlank String title, String creator, String editor, @NotBlank String body, Lock lock) {
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
        this.lock = lock;
    }

}
