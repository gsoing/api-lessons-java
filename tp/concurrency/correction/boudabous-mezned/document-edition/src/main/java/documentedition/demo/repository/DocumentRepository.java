package documentedition.demo.repository;

import documentedition.demo.model.Doc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DocumentRepository extends MongoRepository<Doc,String> {
    Optional<Doc> findByDocumentId(String documentId);
}
