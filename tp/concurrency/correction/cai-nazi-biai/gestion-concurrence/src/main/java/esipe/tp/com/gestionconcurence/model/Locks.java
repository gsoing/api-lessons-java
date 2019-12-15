package esipe.tp.com.gestionconcurence.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Data
@Entity
public class Locks {

    @Id
    private Integer documentId;
    @CreatedDate
    private LocalDateTime created;

    @PersistenceConstructor
    public Locks(Integer documentId, LocalDateTime created) {
        this.documentId = documentId;
        this.created = created;
    }
}
