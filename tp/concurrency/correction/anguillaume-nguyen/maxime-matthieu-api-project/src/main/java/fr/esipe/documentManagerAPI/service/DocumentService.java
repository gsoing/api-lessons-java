package fr.esipe.documentManagerAPI.service;


import fr.esipe.documentManagerAPI.dao.DocumentDAO;
import fr.esipe.documentManagerAPI.dao.DocumentSummaryDAO;
import fr.esipe.documentManagerAPI.model.DocumentModel;
import fr.esipe.documentManagerAPI.model.DocumentSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.*;

@Service
public class DocumentService {


    @Autowired
    DocumentDAO dao;

    @Autowired
    DocumentService serv;

    public void createDocument(DocumentModel document) {
        dao.save(document);
    }


    public List<DocumentModel> getAllDocuments() {
        return dao.findAll();
    }

    public Optional<DocumentModel> findDocumentById(int id) {
        return dao.findById(id);
    }


    public void deleteDocumentById(int id) {
        dao.deleteById(id);
    }

    public void updateDocument(DocumentModel document, int id) {
        Date currentDate = new Date();
        DocumentModel updatedDoc = new DocumentModel();
        updatedDoc = serv.findDocumentById(id).get();
        updatedDoc.setUpdated(currentDate);
        if(!document.getTitle().isEmpty()){
            updatedDoc.setTitle(document.getTitle());
        }
        if(!document.getEditor().isEmpty()){
            updatedDoc.setEditor(document.getEditor());
        }
        if(!document.getBody().isEmpty()){
            updatedDoc.setBody(document.getBody());
        }

        dao.save(updatedDoc);
    }

    public void deleteAllDocument() {
        dao.deleteAll();
    }
}
