package documentedition.demo.model;

import documentedition.demo.dto.LockDto;
import documentedition.demo.utils.RestUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Lock {

    @Id
    private String lockId;

    @NotBlank(message = "Owner cannot be empty")
    private String owner;

    @CreatedDate
    private LocalDateTime created;

    public LockDto toDto() {
        return LockDto.builder()
                .lockId(lockId)
                .owner(owner)
                .created(RestUtils.convertToZoneDateTime(created))
                .build();
    }
}
