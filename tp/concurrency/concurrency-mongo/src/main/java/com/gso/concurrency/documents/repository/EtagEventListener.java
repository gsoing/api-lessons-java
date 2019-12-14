package com.gso.concurrency.documents.repository;

import com.gso.concurrency.documents.model.DocumentModel;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class EtagEventListener extends AbstractMongoEventListener<DocumentModel> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<DocumentModel> event) {
        event.getDocument().put("etag", calculateEtag(event.getDocument()));
        log.debug("before save etag {} for documentId {}", event.getSource().getEtag(), event.getSource().getId());
    }

    @Override
    public void onAfterSave(AfterSaveEvent<DocumentModel> event) {
        event.getSource().setEtag(event.getDocument().get("etag").toString());
        log.debug("before save etag {} for documentId {}", event.getSource().getEtag(), event.getSource().getId());
    }

    /**
     * On calcule l'etag en calculant une MD5 sur le string json
     * @param document
     * @return
     */
    private String calculateEtag(Document document) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            DigestUtils.appendMd5DigestAsHex(new ByteArrayInputStream(document.toJson().getBytes()), stringBuilder);
        } catch (IOException e) {
            throw new DataRetrievalFailureException("Unable to create etag", e);
        }
        return stringBuilder.toString();
    }
}
