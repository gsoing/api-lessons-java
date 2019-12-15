package fr.esipe.documentManagerAPI.dao;

import fr.esipe.documentManagerAPI.model.DocumentList;
import fr.esipe.documentManagerAPI.model.DocumentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ListDAO extends PagingAndSortingRepository<DocumentList, Long>, MongoRepository<DocumentList, Long> {

}