package esipe.tp.com.gestionconcurence.repository;

import esipe.tp.com.gestionconcurence.model.Locks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocksRepository extends JpaRepository<Locks,Integer> {
    Locks findByDocumentId(Integer documentId);
}
