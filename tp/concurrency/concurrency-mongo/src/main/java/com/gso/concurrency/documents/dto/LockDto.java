package com.gso.concurrency.documents.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gso.concurrency.documents.model.LockModel;
import com.gso.concurrency.documents.utils.RestUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.ZonedDateTime;

@Data
@Builder
public class LockDto {

    private String owner;
    @CreatedDate
    private ZonedDateTime created;
    @JsonIgnore
    private String documentId;

    public LockModel toModel() {
        return LockModel.builder()
                .documentId(this.documentId)
                .owner(this.owner)
                .created(RestUtils.convertToLocalDateTime(created))
                .build();
    }
}
