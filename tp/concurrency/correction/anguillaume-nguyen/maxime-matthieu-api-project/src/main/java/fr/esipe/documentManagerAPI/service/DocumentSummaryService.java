package fr.esipe.documentManagerAPI.service;


import fr.esipe.documentManagerAPI.dao.DocumentDAO;
import fr.esipe.documentManagerAPI.dao.DocumentSummaryDAO;
import fr.esipe.documentManagerAPI.model.DocumentModel;
import fr.esipe.documentManagerAPI.model.DocumentSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.*;

@Service
public class DocumentSummaryService {

    private static Map<Integer, DocumentModel> base = new HashMap<Integer, DocumentModel>();

    @Autowired
    DocumentSummaryDAO dao;

    @Autowired
    DocumentService ds;

    public void createDocument(DocumentModel document) {
        DocumentSummary updateSum = new DocumentSummary(document);
        dao.save(updateSum);
    }


   /*public List<DocumentSummary> getAllDocuments() {
        return dao.findAll();
    }*/

    public Optional<DocumentSummary> findDocumentById(int id) {
        return dao.findById(id);
    }

    public void deleteDocumentById(int id) {
        dao.deleteById(id);
    }

    public void updateDocument(DocumentModel document) {
        DocumentSummary updateSum = new DocumentSummary(document);
        if(dao.findById(document.getDocumentId()).isPresent()){
            dao.deleteById(document.getDocumentId());
        }

        dao.save(updateSum);
    }

    public void updateDocumentById(int id) {
        DocumentSummary updateSum = new DocumentSummary(ds.findDocumentById(id).get());
        dao.deleteById(id);
        dao.save(updateSum);
    }

    public void deleteAllDocument() {
        dao.deleteAll();
    }


    public List<DocumentSummary> getAllSummary(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging =PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<DocumentSummary> pagedResult = dao.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<DocumentSummary>();
        }
    }
}
