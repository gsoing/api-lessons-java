package fr.esipe.documentManagerAPI.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;



@Document(collection="document")
public class DocumentModel {

    @Id
    private int documentId;
    private Date created;
    private Date updated;
    private String title;
    private String creator;
    private String editor;
    private String body;
    @Version
    private Integer version;


    public DocumentModel(int id,Date created, Date updated, String title, String creator, String editor, String body) {
        this.documentId = id;
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
    }

    public DocumentModel() {
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString(){
        return "Document ( Title="+ title + ", Author="+ creator+")";
    }


}
