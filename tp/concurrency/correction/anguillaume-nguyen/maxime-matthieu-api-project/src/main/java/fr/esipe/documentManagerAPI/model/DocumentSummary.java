package fr.esipe.documentManagerAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection="documentSummary")
public class DocumentSummary {

    @Id
    private int sumId;
    private Date created;
    private Date updated;
    private String title;


    public DocumentSummary(DocumentModel doc) {
        this.sumId = doc.getDocumentId();
        this.created = doc.getCreated();
        this.updated = doc.getUpdated();
        this.title = doc.getTitle();
    }

    public DocumentSummary() {
        this.sumId=0;
        this.created = new Date();
        this.updated = new Date();
        this.title = "";
    }


    public int getSumId() {
        return sumId;
    }

    public void setSumId(int documentId) {
        this.sumId = documentId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}