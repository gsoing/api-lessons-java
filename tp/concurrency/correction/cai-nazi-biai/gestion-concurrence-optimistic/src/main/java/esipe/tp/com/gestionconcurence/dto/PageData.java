package esipe.tp.com.gestionconcurence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Page;

import javax.persistence.Entity;

@Builder
@NoArgsConstructor
@Data
public class PageData {

    private int page;
    private int nbElements;

    @PersistenceConstructor
    public PageData(int page, int nbElements) {
        this.page = page;
        this.nbElements = nbElements;
    }

    public static PageData fromPage(Page page) {
        return PageData.builder()
                .page(page.getNumber())
                .nbElements((int) page.getTotalElements())
                .build();
    }
}
