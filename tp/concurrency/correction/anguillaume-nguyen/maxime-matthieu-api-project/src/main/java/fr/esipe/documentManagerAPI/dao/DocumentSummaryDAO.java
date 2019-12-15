package fr.esipe.documentManagerAPI.dao;

import fr.esipe.documentManagerAPI.model.DocumentList;
import fr.esipe.documentManagerAPI.model.DocumentModel;

import fr.esipe.documentManagerAPI.model.DocumentSummary;
import fr.esipe.documentManagerAPI.model.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DocumentSummaryDAO extends PagingAndSortingRepository<DocumentSummary, Integer> {
/*
    @Query("{documentId:?0}")
    Optional<Lock> findByDocId(int id);

    @Query("cloud.documentSummary.updateOne({ documentId: ?0 },{$set: { title:?1, created:?2, updated:?3 }})")
    void updateByDocId(int id, String title, Date created, Date updated);
*/
}
