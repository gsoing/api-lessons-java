package fr.esipe.documentManagerAPI.dao;

import fr.esipe.documentManagerAPI.model.DocumentModel;

import fr.esipe.documentManagerAPI.model.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Repository
public interface DocumentDAO extends MongoRepository<DocumentModel, Integer> {


}
