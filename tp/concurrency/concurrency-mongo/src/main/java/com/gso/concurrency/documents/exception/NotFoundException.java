package com.gso.concurrency.documents.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class NotFoundException extends AbstractException {

    public static final String NOT_FOUND_CODE = "err.func.wired.notfound";
    public static final String NOT_FOUND_MESSAGE = "The ressource {0} is not found";

    public NotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, NOT_FOUND_CODE, MessageFormat.format(NOT_FOUND_MESSAGE, id));
    }
}
