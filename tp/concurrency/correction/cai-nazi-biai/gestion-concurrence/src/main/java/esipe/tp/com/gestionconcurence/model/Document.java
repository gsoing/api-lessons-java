package esipe.tp.com.gestionconcurence.model;

import esipe.tp.com.gestionconcurence.dto.DocumentDto;
import esipe.tp.com.gestionconcurence.utils.RestUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Data
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer documentId;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime updated;
    private String title;
    private String creator;
    private String editor;
    private String body;
    @Version
    private Long version;

    @PersistenceConstructor
    public Document(Integer documentId, LocalDateTime created, LocalDateTime updated, String title, String creator, String editor, String body,Long version) {
        this.documentId = documentId;
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
        this.version=version;
    }

    public DocumentDto toDto() {
        return DocumentDto.builder()
                .documentId(documentId)
                .created(RestUtils.convertToZoneDateTime(created))
                .updated(RestUtils.convertToZoneDateTime(updated))
                .title(title)
                .creator(creator)
                .editor(editor)
                .body(body)
                .build();
    }
}
