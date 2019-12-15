package org.esipe.springbootfromswagger.persistance;

import org.esipe.springbootfromswagger.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {

    Page<Document> findAll(Pageable pageable);

}
