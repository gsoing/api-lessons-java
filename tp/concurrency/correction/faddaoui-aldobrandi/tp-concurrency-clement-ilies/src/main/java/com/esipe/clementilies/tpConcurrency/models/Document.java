package com.esipe.clementilies.tpConcurrency.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Document")
public class Document {
    @Id
    @GeneratedValue
    private String documentId;
    @NotBlank
    private String created;
    @NotBlank
    private String updated;
    @NotBlank
    private String title;
    @NotBlank
    private String creator;
    @NotBlank
    private String editor;

    private String body;

    public Document(@NotBlank String created, @NotBlank String updated, @NotBlank String title, @NotBlank String creator, @NotBlank String editor, String body) {
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.creator = creator;
        this.editor = editor;
        this.body = body;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
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
}
