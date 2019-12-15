package com.tplock.locking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.util.Date;
import java.util.Objects;

public class DocumentSummary   {

    @Id
    @JsonProperty("documentId")
    protected String documentId = null;

    @CreatedDate
    @JsonProperty("created")
    protected Date created = null;

    @LastModifiedDate
    @JsonProperty("updated")
    protected Date updated = null;

    @JsonProperty("title")
    protected String title = null;

    @Version
    @JsonProperty("Etag")
    protected Long etag;

    public DocumentSummary(String documentId, Date created, Date updated, String title) {
        this.documentId = documentId;
        this.created = created;
        this.updated = updated;
        this.title = title;
    }
    public DocumentSummary(Document doc){
        this.documentId = doc.documentId;
        this.created = doc.created;
        this.updated = doc.updated;
        this.title = doc.title;
    }

    public DocumentSummary documentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    /**
     * identifiant du document
     * @return documentId
     **/
    @ApiModelProperty(value = "identifiant du document")

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public DocumentSummary created(Date created) {
        this.created = created;
        return this;
    }

    public DocumentSummary setEtag(Long etag){
        this.etag = etag;
        return this;
    }
    public Long getEtag(){
        return this.etag;
    }


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public DocumentSummary updated(Date updated) {
        this.updated = updated;
        return this;
    }



    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public DocumentSummary title(String title) {
        this.title = title;
        return this;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
