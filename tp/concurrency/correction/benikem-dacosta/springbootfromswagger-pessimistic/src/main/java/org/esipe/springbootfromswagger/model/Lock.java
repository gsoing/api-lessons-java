package org.esipe.springbootfromswagger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lock {
    @Id
    private String id;
    private String owner;
    private LocalDateTime created;
}
