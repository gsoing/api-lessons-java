package fr.esipe.locking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import fr.esipe.locking.entities.DocumentEntity;
import fr.esipe.locking.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

import static fr.esipe.locking.utils.CommonUtil.DATE_TIME_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentDto {

    private String documentId;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private ZonedDateTime created;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private ZonedDateTime updated;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 256, message = "Title is max 256 characters")
    private String title;

    @Size(max = 32, message = "Creator is max 32 characters")
    private String creator;

    @NotBlank(message = "Editor must not be blank")
    @Size(max = 32, message = "Editor is max 32 characters")
    private String editor;

    @NotBlank(message = "Body must not be blank")
    @Size(max = 8192, message = "Body is max 8192 characters")
    private String body;

    public DocumentEntity toEntity() {

        return DocumentEntity.builder()
                .documentId(documentId)
                .created(CommonUtil.convertToLocalDateTime(created))
                .updated(CommonUtil.convertToLocalDateTime(updated))
                .title(title)
                .creator(creator)
                .editor(editor)
                .body(body)
                .build();
    }

}
