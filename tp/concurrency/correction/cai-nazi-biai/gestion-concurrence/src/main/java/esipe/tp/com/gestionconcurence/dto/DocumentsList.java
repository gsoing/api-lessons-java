package esipe.tp.com.gestionconcurence.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Builder
@Data
public class DocumentsList {
    private int page;
    private int nbElements;
    private ArrayList<DocumentSummary> data;

    public DocumentsList(int page, int nbElements, ArrayList<DocumentSummary> data) {
        this.page = page;
        this.nbElements = nbElements;
        this.data = data;
    }
}
