package fr.esipe.locking.entities;

import fr.esipe.locking.dtos.LockDto;
import fr.esipe.locking.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "locks")
public class LockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String owner;
    private LocalDateTime created;

    public LockDto toDto() {

        return LockDto.builder()
                .owner(owner)
                .created(CommonUtil.convertToZoneDateTime(created))
                .build();
    }

}
