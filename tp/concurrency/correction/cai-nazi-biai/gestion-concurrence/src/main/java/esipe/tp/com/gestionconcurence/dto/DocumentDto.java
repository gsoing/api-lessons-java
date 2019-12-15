package esipe.tp.com.gestionconcurence.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import esipe.tp.com.gestionconcurence.model.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentDto {
    public static final String ZONE_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private Integer documentId;
    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime created;
    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime updated;
    private String title;
    private String creator;
    private String editor;
    private String body;

    public Document toEntity() {
        return Document.builder()
                .documentId(documentId)
                .title(title)
                .creator(creator)
                .editor(editor)
                .body(body)
                .build();
    }


}
