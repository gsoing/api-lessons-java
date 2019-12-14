package com.gso.concurrency.documents.repository;

import com.gso.concurrency.documents.model.LockModel;

public interface LockCustomRepository {

    LockModel lock(LockModel lockModel);

}
