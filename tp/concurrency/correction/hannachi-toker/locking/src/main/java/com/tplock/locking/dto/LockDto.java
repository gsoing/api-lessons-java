package com.tplock.locking.dto;

import com.tplock.locking.model.Lock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockDto extends MongoRepository<Lock,String> {
    public Lock findByLockId(String lockId);
}
