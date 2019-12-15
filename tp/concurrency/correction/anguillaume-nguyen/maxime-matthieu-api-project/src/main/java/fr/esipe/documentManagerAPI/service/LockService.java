package fr.esipe.documentManagerAPI.service;


import fr.esipe.documentManagerAPI.dao.DocumentDAO;
import fr.esipe.documentManagerAPI.dao.LockDao;
import fr.esipe.documentManagerAPI.model.DocumentModel;
import fr.esipe.documentManagerAPI.model.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LockService {

    @Autowired
    LockDao dao;

    public void createLock(Lock lock) {
        dao.save(lock);
    }

    public List<Lock> getAllLock() {
        return dao.findAll();
    }

    public Optional<Lock> findLockById(int id) {
        return dao.findById(id);
    }

    public void deleteLockById(int id) {
        dao.deleteById(id);
    }

    public void deleteLockByDocId(int id) {
        dao.deleteByDocId(id);
    }

    public void updateLock(Lock lock) {
        dao.save(lock);
    }

    public void deleteAllLock() {
        dao.deleteAll();
    }

    public Optional<Lock> findLockByDocId(int id) {
        return dao.findLockByDocId(id);
    }
}
