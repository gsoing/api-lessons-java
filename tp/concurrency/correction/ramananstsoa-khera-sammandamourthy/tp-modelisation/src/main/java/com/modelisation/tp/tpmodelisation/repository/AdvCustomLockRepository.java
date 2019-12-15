package com.modelisation.tp.tpmodelisation.repository;

import com.modelisation.tp.tpmodelisation.model.CustomLock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;

public interface AdvCustomLockRepository {

    Page<CustomLock> findByDocId(Criteria query, Pageable pageable);
}
