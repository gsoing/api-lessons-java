package com.esipe.clementilies.tpConcurrency.repository;

import com.esipe.clementilies.tpConcurrency.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>{
    List<Document> findAll(Pageable pageable);
}
