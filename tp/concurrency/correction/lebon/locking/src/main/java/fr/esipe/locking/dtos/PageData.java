package fr.esipe.locking.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageData<T> {

    private URI next;
    private int page;
    private long nbElements;
    @NotNull
    private List<T> data;

    public static <T> PageData<T> fromPage(Page<T> page) {
        return PageData.<T>builder()
                .data(page.getContent())
                .page(page.getNumber())
                .nbElements(page.getTotalElements())
                .build();
    }
}
