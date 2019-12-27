package esipe.tp.com.gestionconcurence.service;

import esipe.tp.com.gestionconcurence.dto.DocumentSummary;
import esipe.tp.com.gestionconcurence.dto.DocumentsList;
import esipe.tp.com.gestionconcurence.exception.NotFoundException;
import esipe.tp.com.gestionconcurence.model.Document;
import esipe.tp.com.gestionconcurence.model.Locks;
import esipe.tp.com.gestionconcurence.repository.DocumentRepository;
import esipe.tp.com.gestionconcurence.repository.LocksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Slf4j
@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private LocksRepository lockRepository;
    @PersistenceContext
    EntityManager entityManager;

    public Document createDocument(Document document){
        Document insertedDocument = documentRepository.save(document);
        return insertedDocument;
    }

    public Document getDocument(Integer id) {
        Document document = documentRepository.findById(id).orElseThrow(()-> NotFoundException.DEFAULT);;
        return document;
    }

    /**
     *
     * @param updateDocument
     * @return
     */
    public Document updateDocument(Document updateDocument) {
        Document document = documentRepository.findById(updateDocument.getDocumentId()).orElseThrow(() -> NotFoundException.DEFAULT);
        document.setBody(updateDocument.getBody());
        document.setCreator(updateDocument.getCreator());
        document.setEditor(updateDocument.getEditor());
        document.setTitle(updateDocument.getTitle());

        return documentRepository.save(document);
    }

    public DocumentsList getDocuments(int numPage, int nbDocument) {
        Pageable numberOfDocumentAndPage = PageRequest.of(numPage, nbDocument, Sort.by("documentId").ascending());
        Page<Document> page = documentRepository.findAll(numberOfDocumentAndPage);
        ArrayList<DocumentSummary> data = new ArrayList<DocumentSummary>();
        for(Document doc :page){
            DocumentSummary documentSummary= new DocumentSummary();
            documentSummary.setCreated(doc.getCreated());
            documentSummary.setDocumentId(doc.getDocumentId());
            documentSummary.setTitle(doc.getTitle());
            documentSummary.setUpdated(doc.getUpdated());
            data.add(documentSummary);
        }
        DocumentsList documentsList = new DocumentsList(numPage,nbDocument,data);

        return documentsList;

    }

    // poser un lock
    public Locks createLock(Locks locks){
        Locks insertedLock = lockRepository.save(locks);
        //Document doc = getDocument(locks.getDocumentId());
        //entityManager.lock(doc, LockModeType.PESSIMISTIC_READ);
        return insertedLock;
    }

    public Locks getLock(Integer documentId){
        Locks locks =lockRepository.findById(documentId).orElseThrow(()-> NotFoundException.DEFAULT);
        return locks;
    }


    public void deleteLock(Integer idDocument){

        lockRepository.deleteById(idDocument);
    }


}
