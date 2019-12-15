package esipe.fr.tpconcurrence.repositories;

import esipe.fr.tpconcurrence.entities.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LockRepository extends MongoRepository<Lock,String> {
    public Optional<Lock> findByLockId(String lockId);
}
