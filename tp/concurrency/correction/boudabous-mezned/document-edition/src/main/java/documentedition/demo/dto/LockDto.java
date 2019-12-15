package documentedition.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

import documentedition.demo.model.Lock;
import documentedition.demo.utils.RestUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LockDto {

    public static final String ZONE_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private String lockId;

    @NotBlank(message = "Owner must not be blank")
    @Size(max = 32, message = "Owner is max 32 characters")
    private String owner;

    @JsonFormat(pattern = ZONE_DATE_TIME_FORMAT)
    private ZonedDateTime created;

    public Lock toEntity() {
        return Lock.builder()
                .owner(owner)
                .created(RestUtils.convertToLocalDateTime(created))
                .build();
    }
}