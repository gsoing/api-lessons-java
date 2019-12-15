package com.esipe.clementilies.tpConcurrency.Exception;

public class NotFoundException extends Exception {
    private String documentId;
    public NotFoundException(String documentId){
        super(String.format("Document id not exist: " + documentId));
    }
}
