package fr.esipe.documentManagerAPI.model;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


public class DocumentList {

    private int page;
    private int nbElements;
    private List<DocumentSummary> list;

    public DocumentList() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNbElements() {
        return nbElements;
    }

    public void setNbElements(int nbElements) {
        this.nbElements = nbElements;
    }

    public List<DocumentSummary> getList() {
        return list;
    }

    public void setList(List<DocumentSummary> list) {
        this.list = list;
    }
}
