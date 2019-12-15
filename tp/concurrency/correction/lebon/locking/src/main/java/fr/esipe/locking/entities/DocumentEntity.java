package fr.esipe.locking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.esipe.locking.dtos.DocumentDto;
import fr.esipe.locking.dtos.DocumentSummary;
import fr.esipe.locking.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documents")
public class DocumentEntity {

    @Id
    private String documentId;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 256, message = "Title is max 256 characters")
    private String title;

    @NotBlank(message = "Creator must not be blank")
    @Size(max = 32, message = "Creator is max 32 characters")
    private String creator;

    @NotBlank(message = "Editor must not be blank")
    @Size(max = 32, message = "Editor is max 32 characters")
    private String editor;

    @NotBlank(message = "Body must not be blank")
    @Size(max = 8192, message = "Body is max 8192 characters")
    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private LockEntity lock;

    @Transient
    @JsonIgnore
    private String etag;

    @PersistenceConstructor
    public DocumentEntity(String documentId, LocalDateTime created, LocalDateTime updated, String title, String creator, String editor, String body, LockEntity lock) {

        this.documentId = documentId;
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
        this.lock = lock;

    }

    public DocumentDto toDto() {

        return DocumentDto.builder()
                .documentId(documentId)
                .created(CommonUtil.convertToZoneDateTime(created))
                .updated(CommonUtil.convertToZoneDateTime(updated))
                .title(title)
                .creator(creator)
                .editor(editor)
                .body(body)
                .build();
    }

    public DocumentSummary toSummary() {

        return DocumentSummary.builder()
                .documentId(documentId)
                .created(CommonUtil.convertToZoneDateTime(created))
                .updated(CommonUtil.convertToZoneDateTime(updated))
                .title(title)
                .build();
    }

}

