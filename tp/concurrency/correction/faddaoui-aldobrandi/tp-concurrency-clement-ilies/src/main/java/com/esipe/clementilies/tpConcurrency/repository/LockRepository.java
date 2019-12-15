package com.esipe.clementilies.tpConcurrency.repository;

import com.esipe.clementilies.tpConcurrency.models.Lock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockRepository extends JpaRepository<Lock, String> {
}
