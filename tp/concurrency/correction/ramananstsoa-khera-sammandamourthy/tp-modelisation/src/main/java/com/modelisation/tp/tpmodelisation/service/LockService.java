package com.modelisation.tp.tpmodelisation.service;

import com.modelisation.tp.tpmodelisation.exception.ConflictException;
import com.modelisation.tp.tpmodelisation.exception.NotFoundException;
import com.modelisation.tp.tpmodelisation.model.CustomLock;
import com.modelisation.tp.tpmodelisation.model.Doc;
import com.modelisation.tp.tpmodelisation.repository.CustomLockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LockService {

    @Autowired
    private CustomLockRepository lockRepository;

    public Iterable<CustomLock> findAll() {
        return lockRepository.findAll();
    }

    /**
     * Get lock
     * @param idLock
     * @return
     */
    public CustomLock getLock(String idLock) {
        CustomLock lock = lockRepository.findById(idLock).orElseThrow(() -> ConflictException.DEFAULT);
        return lock;
    }

    /**
     * Delete lock
     * @param customLock
     */
    public void deleteLock(String customLock) {
        lockRepository.deleteById(customLock);
    }
}
