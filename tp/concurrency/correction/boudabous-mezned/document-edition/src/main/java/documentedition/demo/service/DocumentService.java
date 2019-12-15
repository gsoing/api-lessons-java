package documentedition.demo.service;

import documentedition.demo.exception.ConflictException;
import documentedition.demo.exception.NotFoundException;
import documentedition.demo.model.Doc;
import documentedition.demo.model.Lock;
import documentedition.demo.repository.DocumentRepository;

import documentedition.demo.repository.LockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private LockRepository lockRepository;


    public Page<Doc> getDocuments(Pageable pageable) {
        Page<Doc> documents = documentRepository.findAll(pageable);
        return documents;
    }


    public Doc getDocument(String documentId) {
        Optional<Doc> document = Optional.of(documentRepository.findByDocumentId(documentId).orElseThrow(() -> NotFoundException.DEFAULT));
        return document.get();
    }

    public Doc createDocument(Doc document){
        Doc insertedDocument = documentRepository.insert(document);
        ;
        return insertedDocument;
    }

    public Doc updateDocument(Doc updateDocument) {

        Optional<Doc> actualDocument = Optional.of((documentRepository.findById(updateDocument.getDocumentId()).orElseThrow(() -> NotFoundException.DEFAULT)));
        Optional<Lock> actualLock = (lockRepository.findByLockId(updateDocument.getDocumentId()));

        if(!actualLock.isPresent()) {
            Lock lock = new Lock(updateDocument.getDocumentId(), updateDocument.getEditor(), LocalDateTime.now());
            lockDoc(lock, updateDocument.getDocumentId());

            actualDocument.get().setTitle(updateDocument.getTitle());
            actualDocument.get().setCreator(updateDocument.getCreator());
            actualDocument.get().setEditor(updateDocument.getEditor());
            actualDocument.get().setBody(updateDocument.getBody());

            documentRepository.save(actualDocument.get());

            unlockDoc(updateDocument.getDocumentId());

            return getDocument(updateDocument.getDocumentId());
        }

        return null;
    }

    public Lock getLock(String documentId) {
        Optional<Lock> lock = lockRepository.findByLockId(documentId);

        if(lock.isPresent())
            return lock.get();
        else
            return null;
    }

    public Lock lockDoc(Lock lock, String documentId) {
        Optional<Lock> actualLock = (lockRepository.findByLockId(documentId));

        if (actualLock.isPresent()) {
            return actualLock.get();
        }
        else {
            lock.setLockId(documentId);
            lockRepository.save(lock);
            return lock;
        }
    }

    public void unlockDoc(String documentId) {
        Optional<Lock> actualLock = (lockRepository.findByLockId(documentId));

        if(actualLock.isPresent())
            lockRepository.delete(actualLock.get());
    }
}
