package fr.esipe.locking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import fr.esipe.locking.entities.LockEntity;
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
public class LockDto {

    @NotBlank(message = "Owner must not be blank")
    @Size(max = 32, message = "Owner is max 32 characters")
    private String owner;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private ZonedDateTime created;

    public LockEntity toEntity() {

        return LockEntity.builder()
                .owner(owner)
                .created(CommonUtil.convertToLocalDateTime(created))
                .build();
    }

}
