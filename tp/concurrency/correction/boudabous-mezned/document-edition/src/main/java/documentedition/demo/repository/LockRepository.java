package documentedition.demo.repository;

import documentedition.demo.model.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LockRepository extends MongoRepository<Lock,String> {
    Optional<Lock> findByLockId(String documentId);
}
