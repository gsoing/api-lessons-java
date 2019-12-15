package com.esipe.clementilies.tpConcurrency.services;

import com.esipe.clementilies.tpConcurrency.models.Lock;
import com.esipe.clementilies.tpConcurrency.repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockService {
    @Autowired
    public LockRepository lockRepository;

    public Lock getLockFromDocument(String documentId){
        List<Lock> allLock = lockRepository.findAll();
        Lock lock = allLock.stream().filter( x -> documentId.equals(x.getDocumentId())).findAny().orElse(null);
        return  lock;
    }

    public Lock putLock(String documentId, Lock lock){
        if(getLockFromDocument(documentId) == null){
            lockRepository.save(lock);
            return lock;
        }
        return null;
    }

    public void deleteLock(String documentId){
        List<Lock> allLock = lockRepository.findAll();
        Lock lock = allLock.stream().filter( x -> documentId.equals(x.getDocumentId())).findAny().orElse(null);
        lockRepository.deleteById(lock.getLockId());

    }
}
