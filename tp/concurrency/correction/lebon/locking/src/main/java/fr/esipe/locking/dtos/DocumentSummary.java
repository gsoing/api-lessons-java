package fr.esipe.locking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static fr.esipe.locking.utils.CommonUtil.DATE_TIME_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentSummary {

    private String documentId;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private ZonedDateTime created;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private ZonedDateTime updated;

    private String title;

}
