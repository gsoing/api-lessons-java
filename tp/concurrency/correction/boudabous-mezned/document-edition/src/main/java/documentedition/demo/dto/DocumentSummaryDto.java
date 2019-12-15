package documentedition.demo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentSummaryDto {

    public static final String ZONE_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private String documentId;

    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime created;

    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime updated;

    @NotBlank(message = "Title cannot be empty")
    private String title;
}
