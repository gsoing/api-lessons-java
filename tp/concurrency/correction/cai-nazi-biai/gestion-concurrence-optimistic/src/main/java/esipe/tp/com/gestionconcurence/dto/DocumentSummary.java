package esipe.tp.com.gestionconcurence.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Data
public class DocumentSummary {

    private Integer documentId;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime updated;
    private String title;

    @PersistenceConstructor
    public DocumentSummary(int documentId, LocalDateTime created, LocalDateTime updated, String title) {
        this.documentId = documentId;
        this.created = created;
        this.updated = updated;
        this.title = title;
    }
}
