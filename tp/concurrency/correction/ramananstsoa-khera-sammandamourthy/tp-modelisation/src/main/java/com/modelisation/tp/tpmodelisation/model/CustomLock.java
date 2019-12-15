package com.modelisation.tp.tpmodelisation.model;

import com.modelisation.tp.tpmodelisation.dto.CustomLockDto;
import com.modelisation.tp.tpmodelisation.utils.RestUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Document CustomLock
 */
@Builder
@NoArgsConstructor
@Data
@Document
public class CustomLock {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    @Column(name = "id")
    private String id;
    @NotNull
    private String owner;
    @CreatedDate
    private LocalDateTime created;

    /**
     * Persistence Constructor
     *
     * @param id
     * @param owner
     * @param created
     */
    @PersistenceConstructor
    public CustomLock(String id, String owner, LocalDateTime created) {
        this.id = id;
        this.owner = owner;
        this.created = created;
    }

    /**
     * Constructor with owner param
     *
     * @param owner
     */
    public CustomLock(String owner) {
        this.owner = owner;
    }

    /**
     * CustomLockDto
     *
     * @return CustomLock Dto
     */
    public CustomLockDto toDto() {
        return CustomLockDto.builder()
                .id(id)
                .owner(owner)
                .created(RestUtils.convertToZoneDateTime(created))
                .build();
    }
}
