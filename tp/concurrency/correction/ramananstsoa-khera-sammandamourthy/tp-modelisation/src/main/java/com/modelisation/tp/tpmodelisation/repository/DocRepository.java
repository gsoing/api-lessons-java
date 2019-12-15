package com.modelisation.tp.tpmodelisation.repository;

import com.modelisation.tp.tpmodelisation.model.Doc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends CrudRepository<Doc, String> {

    @Query("{'doc.documentId' : ?0}")
    Page<Doc> findById(String documentId, Pageable pageable);


}
