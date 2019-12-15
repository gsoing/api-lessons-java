package tpmodeldereal.demo.Repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tpmodeldereal.demo.Model.document;

import javax.persistence.LockModeType;
import java.util.List;


@Repository
public interface DocRepository extends MongoRepository<document,String > {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("{ 'document.documentId' : ?0 }")
    document findByDocumentId (String documentId) ;

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("{ db.document.find() }")
    List<document> findDocs() ;
}
