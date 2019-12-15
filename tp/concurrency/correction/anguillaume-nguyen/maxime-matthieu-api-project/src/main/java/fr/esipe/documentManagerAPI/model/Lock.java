package fr.esipe.documentManagerAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="lock")
public class Lock {

    @Id
    private int lockId;
    //private boolean active;
    private String owner;
    private Date created;
    private int docId;

    public Lock( String owner, Date created, int docId) {
       // this.active = active;
        this.owner = owner;
        this.created = created;
        this.docId = docId;
    }

    public Lock() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getLockId() {
        return lockId;
    }

    public void setLockId(int lockId) {
        this.lockId = lockId;
    }
}
