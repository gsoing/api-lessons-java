package esipe.fr.tpconcurrence.services;

import esipe.fr.tpconcurrence.entities.Document;
import esipe.fr.tpconcurrence.entities.DocumentSummary;
import esipe.fr.tpconcurrence.entities.DocumentsList;
import esipe.fr.tpconcurrence.entities.Lock;
import esipe.fr.tpconcurrence.exceptions.ApiException;
import esipe.fr.tpconcurrence.repositories.DocumentRepository;
import esipe.fr.tpconcurrence.repositories.LockRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    LockRepository lockRepository;

    Logger loggerFindDoc = LogManager.getLogger("DocumentService findDoc");
    Logger loggerSaveDoc = LogManager.getLogger("DocumentService saveDoc");
    Logger loggerUpdateDoc = LogManager.getLogger("DocumentService updateDoc");
    Logger loggerLock = LogManager.getLogger("LockService");

    public Document findDocumentById(String id) throws ApiException {
        Optional<Document> document = documentRepository.findByDocumentId(id);
        if(document.isPresent()){
            loggerFindDoc.info("Récupération du document: " + document.get().getDocumentId());
            return document.get();
        }else{
            loggerFindDoc.warn("Aucun document trouvé");
            throw new ApiException(404,"Aucun document trouvé");
        }
    }

    public void checkIfDocExist(String documentId) throws ApiException {
        if(!documentRepository.existsByDocumentId(documentId)){
            loggerFindDoc.warn("Aucun document trouvé");
            throw new ApiException(404,"Aucun document trouvé");
        }
    }

    public DocumentsList findAll(int page, int pageSize){
        DocumentsList documentsList = new DocumentsList();
        documentsList.setPage(page);
        documentsList.setNbElements(pageSize);
        Pageable pageable = PageRequest.of(page,pageSize);
        int i = 0;
        for (Document d : documentRepository.findAll(pageable)) {
            documentsList.addDataItem(new DocumentSummary(d.getDocumentId(),d.getCreated(),d.getUpdated(),d.getTitle()));
            i++;
        }
        loggerFindDoc.info("Récupération de "+i+"/"+pageSize+" document(s) de la page "+page);
        return documentsList;
    }

    public Document save(Document doc) throws ApiException {
        if(doc.getDocumentId() != null && doc.getCreated() != null
                && doc.getUpdated() != null && doc.getTitle() != null
                && doc.getCreator() != null && doc.getEditor() != null
                && doc.getBody() != null){
            documentRepository.save(doc);
            loggerSaveDoc.info("Création du document : "+doc.getDocumentId());
            return findDocumentById(doc.getDocumentId());
        }else{
            loggerSaveDoc.warn("Impossible d'ajouter le document : "+doc.getDocumentId());
            throw new ApiException(400,"le contenu n'est pas correct");
        }
    }

    public Document updateDocument(Document document, String documentId) throws ApiException {
        loggerUpdateDoc.info("Demande de mise à jour du document: "+documentId);
        //Récupération du document à modifier dans la base
        Document updatedDocument = findDocumentById(documentId);
        //Vérification du verrou sur le document
        Lock lock = findLock(documentId);
        // Si la version à modifier est la version présente en base
        if(document.getVersion().equals(updatedDocument.getVersion())){
            loggerUpdateDoc.info("Version à modifier OK : "+ document.getVersion());
            //S'il n'existe pas de verrou, on le créé, le document est modifié, et le verrou est supprimé
            if(lock == null){
                loggerUpdateDoc.info("Aucun verrou posé sur le document à modifier");
                setLock(new Lock(documentId,document.getEditor(), LocalDateTime.now()),documentId);
                updatedDocument.setBody(document.getBody());
                updatedDocument.setTitle(document.getTitle());
                updatedDocument.setEditor(document.getEditor());
                updatedDocument.setUpdated(document.getUpdated());
                documentRepository.save(updatedDocument);
                loggerUpdateDoc.info("Mise à jour du document OK");
                deleteLock(documentId);
                return findDocumentById(documentId);

                //Si le document est verrouillé par l'éditeur du document, on accepte la modification
            }else if(lock.getOwner().equals(document.getEditor())){
                loggerUpdateDoc.info("Verrou OK sur le document à modifier");
                updatedDocument.setBody(document.getBody());
                updatedDocument.setTitle(document.getTitle());
                updatedDocument.setEditor(document.getEditor());
                updatedDocument.setUpdated(document.getUpdated());
                documentRepository.save(updatedDocument);
                loggerUpdateDoc.info("Mise à jour du document OK");
                return findDocumentById(documentId);
                //Si le document est verrouillé est ne correspond pas à l'éditeurn on refuse la modification
            }else{
                loggerUpdateDoc.error("Document "+documentId+" verrouiller, la mise à jour est impossible");
                throw new ApiException(409,"Le document est verrouillé !");
            }

        }else{
            loggerUpdateDoc.warn("Une version plus récente existe");
            throw new ApiException(412,"Une version plus récente existe");
        }
    }

    public Lock setLock(Lock sentLock ,String documentId) throws ApiException {
        //vérification de l'existance du document
        checkIfDocExist(documentId);

        //récupération du verrou avec un id = id du doc
        //si aucun lock n'est récupéré alors le document n'a pas de verrou
        Optional<Lock> lock = lockRepository.findByLockId(documentId);
        if(lock.isPresent()){
            loggerLock.warn("Un verrou est déjà posé sur le document: "+ documentId);
            return lock.get();
        }else{
            loggerLock.info("Verouillage du document : "+ documentId);
            sentLock.setLockId(documentId);
            lockRepository.save(sentLock);
            return sentLock;
        }
    }

    public void deleteLock(String documentId) throws ApiException {
        //vérification de l'existance du document
        checkIfDocExist(documentId);

        //récupération du verrou avec un id = id du doc
        //si aucun lock n'est récupéré alors le document n'a pas de verrou
        Optional<Lock> lock = lockRepository.findByLockId(documentId);
        if(lock.isPresent()){
            lockRepository.delete(lock.get());
            loggerLock.info("Suppression du verrou sur le document: "+ documentId);
        }else{
            loggerLock.info("Suppression impossible, aucun verrou sur le document: "+ documentId);
            throw new ApiException(204,"Aucun verrou trouvé");
        }
    }

    public Lock findLock(String documentId) throws ApiException {
        //vérification de l'existance du document
        checkIfDocExist(documentId);

        //récupération du verrou avec un id = id du doc
        //si aucun lock n'est récupéré alors le document n'a pas de verrou
        Optional<Lock> lock = lockRepository.findByLockId(documentId);
        if(lock.isPresent()){
            loggerLock.warn("Un verrou est déjà posé sur le document: "+ documentId);
            return lock.get();
        }else{
            loggerLock.info("Aucun verrou posé sur le document: "+ documentId);
            return null;
        }
    }
}

