package fr.esipe.locking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esipe.locking.entities.DocumentEntity;
import fr.esipe.locking.entities.LockEntity;
import fr.esipe.locking.repositories.DocumentRepository;
import fr.esipe.locking.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private ObjectMapper mapper;

    private final EntityManagerFactory emf;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, EntityManagerFactory emf) {
        this.documentRepository = documentRepository;
        mapper = new ObjectMapper();
        this.emf = emf;
    }

    public DocumentEntity createDocument(DocumentEntity documentEntity) throws Exception {

        LocalDateTime now = LocalDateTime.now();

        documentEntity.setDocumentId(UUID.randomUUID().toString());
        documentEntity.setCreated(now);
        documentEntity.setUpdated(now);

        DocumentEntity createdDocument = documentRepository.save(documentEntity);

        createdDocument.setEtag(CommonUtil.buildEtag(mapper.writeValueAsString(createdDocument)));

        return createdDocument;
    }

    public Page<DocumentEntity> getAllDocuments(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    public Optional<DocumentEntity> findDocument(String documentId) throws Exception {

        Optional<DocumentEntity> optDocument = documentRepository.findById(documentId);

        if (!optDocument.isPresent()) {
            return optDocument;
        }

        DocumentEntity document = optDocument.get();
        document.setEtag(CommonUtil.buildEtag(mapper.writeValueAsString(document)));

        return Optional.of(document);
    }

    @Transactional
    public Optional<DocumentEntity> updateDocument(String documentId, DocumentEntity updatedDocumentEntity) throws Exception {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        DocumentEntity document = em.find(DocumentEntity.class, documentId);

        if (document.getLock() != null) {
            return Optional.empty();
        }

        LocalDateTime now = LocalDateTime.now();

        document.setBody(updatedDocumentEntity.getBody());
        document.setTitle(updatedDocumentEntity.getTitle());
        document.setEditor(updatedDocumentEntity.getEditor());
        document.setUpdated(now);

        em.persist(document);
        em.getTransaction().commit();
        em.close();

        document.setEtag(CommonUtil.buildEtag(mapper.writeValueAsString(document)));

        return Optional.of(document);

    }

    @Transactional
    public Optional<LockEntity> lockDocument(String documentId, LockEntity lockEntity) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        DocumentEntity documentEntity;

        try {
            documentEntity = em.find(DocumentEntity.class, documentId, LockModeType.PESSIMISTIC_WRITE);
        } catch (PessimisticLockException | LockTimeoutException e) {
            return Optional.empty();
        }

        if (documentEntity.getLock() != null) {
            return Optional.empty();
        }

        lockEntity.setCreated(LocalDateTime.now());

        documentEntity.setLock(lockEntity);

        em.persist(documentEntity);

        em.getTransaction().commit();
        em.close();

        return Optional.of(lockEntity);

    }

    @Transactional(readOnly = true)
    public Optional<LockEntity> getLock(String documentId) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        DocumentEntity documentEntity = em.find(DocumentEntity.class, documentId);

        em.getTransaction().commit();

        return Optional.ofNullable(documentEntity.getLock());

    }

    @Transactional
    public void unlockDocument(String documentId) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        DocumentEntity documentEntity = em.find(DocumentEntity.class, documentId, LockModeType.PESSIMISTIC_WRITE);

        documentEntity.setLock(null);

        em.persist(documentEntity);
        em.getTransaction().commit();
        em.close();

    }

}
