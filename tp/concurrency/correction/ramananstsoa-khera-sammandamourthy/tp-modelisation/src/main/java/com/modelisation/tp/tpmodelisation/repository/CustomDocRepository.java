package com.modelisation.tp.tpmodelisation.repository;

import com.modelisation.tp.tpmodelisation.model.Doc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;

public interface CustomDocRepository {

    Page<Doc> findDocs(Criteria query, Pageable pageable);
}
