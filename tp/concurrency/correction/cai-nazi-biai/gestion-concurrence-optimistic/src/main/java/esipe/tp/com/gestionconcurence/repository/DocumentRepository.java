package esipe.tp.com.gestionconcurence.repository;

import esipe.tp.com.gestionconcurence.model.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;


import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
       // List<Document> findAllBy
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Document> findById(Integer documentId);

}
