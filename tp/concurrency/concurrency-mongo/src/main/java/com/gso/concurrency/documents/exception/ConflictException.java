package com.gso.concurrency.documents.exception;

import com.gso.concurrency.documents.model.LockModel;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class ConflictException extends AbstractException {

    LockModel lock;

    public static final String CONFLICT_CODE = "err.func.wired.conflict";
    public static final String CONFLICT_MESSAGE = "A lock is already set";

    public ConflictException(LockModel lockModel) {
        super(HttpStatus.CONFLICT, CONFLICT_CODE, CONFLICT_MESSAGE);
        this.lock = lockModel;
    }
}
