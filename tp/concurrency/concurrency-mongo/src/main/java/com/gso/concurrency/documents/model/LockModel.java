package com.gso.concurrency.documents.model;

import com.gso.concurrency.documents.dto.LockDto;
import com.gso.concurrency.documents.utils.RestUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Document
public class LockModel {

    @Id
    String id;
    @NotNull
    String documentId;
    @NotNull
    String owner;
    @CreatedDate
    LocalDateTime created;

    public LockDto toDto() {
        return LockDto.builder()
                .owner(this.owner)
                .created(RestUtils.convertToZoneDateTime(this.created))
                .documentId(this.documentId)
                .build();
    }

}
