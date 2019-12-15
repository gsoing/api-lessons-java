package tpmodeldereal.demo.Model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.time.LocalDateTime;
@Builder
@NoArgsConstructor
@Data
@Document
public class document {
    @Id
    @GeneratedValue
    String documentId ;
    @Version
    Long version ;
    @CreatedDate
    LocalDateTime created ;
    @LastModifiedDate
    LocalDateTime updated;
    String title ;
    String creator ;
    String editor;
    String body ;

@PersistenceConstructor
    public document(String documentId,Long version, LocalDateTime created, LocalDateTime updated, String title, String creator, String editor, String body) {
        this.documentId = documentId;
        this.version = version ;
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
    }



}
