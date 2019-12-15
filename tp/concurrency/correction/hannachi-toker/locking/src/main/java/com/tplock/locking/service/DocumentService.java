package com.tplock.locking.service;

import com.tplock.locking.model.Document;
import com.tplock.locking.model.DocumentSummary;
import com.tplock.locking.model.DocumentsList;
import com.tplock.locking.model.Lock;
import com.tplock.locking.exception.ConflictException;
import com.tplock.locking.exception.NoContentException;
import com.tplock.locking.exception.NotFoundException;
import com.tplock.locking.dto.DocumentDto;
import com.tplock.locking.dto.LockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    public DocumentDto documentDto;
    @Autowired
    public LockDto lockDto;

    public Document saveDocument(Document document){
        UUID uuid = UUID.randomUUID();
        document.setDocumentId(uuid.toString());
        this.documentDto.insert(document);
        return document;
    }

    public DocumentsList findAll() {
        List<Document> l1 = this.documentDto.findAll();
        List<DocumentSummary> l2 = new ArrayList<>();
        for(Document doc : l1){
            l2.add(new DocumentSummary(doc));
        }
        DocumentsList documentsList = new DocumentsList();
        documentsList.data(l2);
        return documentsList;
    }

    public Document updateDocument(Document document, String documentId) throws ConflictException {
        Document test = this.documentDto.findDocumentByDocumentId(documentId);
        Lock currentLock = this.lockDto.findByLockId(documentId);
        if(currentLock == null && test != null){
            currentLock = new Lock();
            currentLock.setLockId(test.getDocumentId());
            currentLock.setOwner(test.getEditor());
            this.lockDto.save(currentLock);
            if(test.getEtag() <= document.getEtag()){
                document.setDocumentId(documentId);
                this.documentDto.save(document);
                try {
                    this.removeLock(documentId);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                return document;
            }else {
                try {
                    this.removeLock(documentId);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                throw new ConflictException("this is not the most recent version of this document",null);
            }
        }else if(currentLock.getOwner().equals(document.getEditor())){
            if(test.getEtag()>document.getEtag()){
                throw new ConflictException("this is not the most recent version of this document",null);
            }else {
                document.setDocumentId(documentId);
                this.documentDto.save(document);
                return document;
            }
        }else{
            throw new ConflictException();
        }
    }

    public Lock lockDocument(String documentId,Lock lock) throws ConflictException, NoContentException {
        if(this.documentDto.findDocumentByDocumentId(documentId)!=null){
            Lock currentLock =this.lockDto.findByLockId(documentId);
            if(currentLock!=null)
                throw new ConflictException("document already locked by ",currentLock);
            lock.created(new Date(System.currentTimeMillis()));
            lock.setLockId(documentId);
            currentLock =this.lockDto.save(lock);
            if(currentLock!=null){
                return currentLock;
            }else{
                throw new ConflictException();
            }
        }else {
            throw new NoContentException();
        }
    }

    public Lock getLock(String documentId)throws NoContentException{
        Lock lock = this.lockDto.findByLockId(documentId);
        if(lock!=null) {
            return lock;
        }
        throw new NoContentException();
    }

    public void removeLock(String documentId) throws NotFoundException {
        if(this.lockDto.existsById(documentId)){
            this.lockDto.deleteById(documentId);
        }else{
            throw new NotFoundException("the document is not locked");
        }
    }

    public Document findDocumentById(String documentId) throws NotFoundException{
        Document doc = this.documentDto.findDocumentByDocumentId(documentId);
        if(doc!=null){
            return doc;
        }else{
            throw new NotFoundException();
        }
    }
}
