package fr.esipe.documentManagerAPI.dao;

import fr.esipe.documentManagerAPI.model.DocumentModel;

import fr.esipe.documentManagerAPI.model.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LockDao extends MongoRepository<Lock, Integer> {

    @Query("{docId:?0}")
    Optional<Lock> findLockByDocId(int id);

    @Query(value="{docId:?0}",delete = true)
    void deleteByDocId(int id);
}
