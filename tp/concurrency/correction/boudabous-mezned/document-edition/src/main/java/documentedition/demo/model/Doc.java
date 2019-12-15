package documentedition.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import documentedition.demo.dto.DocumentDto;
import documentedition.demo.dto.DocumentSummaryDto;
import documentedition.demo.utils.RestUtils;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;


@Builder
@NoArgsConstructor
@Data
@Document
@AllArgsConstructor
public class Doc {
    @Id
    private String documentId;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    @NotBlank(message = "Title cannot be empty")
    private String title;

   @NotBlank(message = "Creator cannot be empty")
    private String creator;

    private String editor;

    @NotBlank(message = "Body cannot be empty")
    private String body;

    @Transient
    @JsonIgnore
    private String etag;

    @PersistenceConstructor
    public Doc(String documentId, LocalDateTime created, LocalDateTime updated, String title, String creator, String editor, String body) {
        this.documentId = documentId;
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
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

    public DocumentSummaryDto toSummaryDto() {

        return DocumentSummaryDto.builder()
                .documentId(documentId)
                .created(RestUtils.convertToZoneDateTime(created))
                .updated(RestUtils.convertToZoneDateTime(updated))
                .title(title)
                .build();
    }
}
