package org.esipe.springbootfromswagger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageData {
    @Id
    private int page;
    private int nbElements;
}
