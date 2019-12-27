package esipe.tp.com.gestionconcurence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import esipe.tp.com.gestionconcurence.model.Locks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LockDto {

    private Integer documentId;
    @CreatedDate
    private LocalDateTime created;

    public Locks toEntity() {
        return Locks.builder()
                .documentId(documentId)
                .build();
    }
}
