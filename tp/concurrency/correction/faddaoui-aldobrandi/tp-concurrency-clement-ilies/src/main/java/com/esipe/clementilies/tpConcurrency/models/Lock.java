package com.esipe.clementilies.tpConcurrency.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Lock")
public class Lock {
    @Id
    @GeneratedValue
    private String lockId;
    @NotBlank
    private String owner;
    @NotBlank
    private String created;
    @NotBlank
    private String documentId;

    public Lock(@NotBlank String owner, @NotBlank String created, @NotBlank String documentId) {
        this.owner = owner;
        this.created = created;
        this.documentId = documentId;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
