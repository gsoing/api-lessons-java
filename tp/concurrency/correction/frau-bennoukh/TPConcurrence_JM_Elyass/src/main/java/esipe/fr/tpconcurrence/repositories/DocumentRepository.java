package esipe.fr.tpconcurrence.repositories;

import esipe.fr.tpconcurrence.entities.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DocumentRepository extends MongoRepository<Document,String> {
    public Optional<Document> findByDocumentId(String documentId);
    public boolean existsByDocumentId(String documentId);
}
