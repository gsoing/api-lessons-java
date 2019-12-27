package org.esipe.springbootfromswagger.persistance;

import lombok.extern.slf4j.Slf4j;
import org.esipe.springbootfromswagger.model.Document;
import org.esipe.springbootfromswagger.service.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OptimisticLocking implements CommandLineRunner {

    private DocumentServiceImpl documentService;

    @Autowired
    public void setDocumentService(DocumentServiceImpl documentService) {
        this.documentService = documentService;
    }

    @Override
    public void run(String... args) {

        Document document = new Document();
        document.setBody("body");
        document.setTitle("title");
        this.documentService.save(document);
        log.info("A document with the following attributes has been created: ID: " + document.getDocumentId() + " TITLE: " + document.getTitle() + " BODY: " + document.getBody());

        ExecutorService es = Executors.newFixedThreadPool(2);

        es.execute(() -> {
            Document uno = this.documentService.findById(document.getDocumentId());
            uno.setTitle("UNO");
            log.info("USER 1 SETTING TITLE TO " + uno.getTitle());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                documentService.updateById(uno, document.getDocumentId());
            } catch (Exception e) {
                log.info("USER 1 " + e);
                log.info("USER 1 after error: " + this.documentService.findById(document.getDocumentId()));
                return;
            }
            log.info("USER 1 Finished " + this.documentService.findById(document.getDocumentId()));
        });

        es.execute(() -> {
            Document dos = this.documentService.findById(document.getDocumentId());
            dos.setTitle("DOS");
            log.info("USER 2 SETTING TITLE TO " + dos.getTitle());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                documentService.updateById(dos, document.getDocumentId());
            } catch (Exception e) {
                log.info("USER 2 " + e);
                log.info("USER 2 after error: " + this.documentService.findById(document.getDocumentId()));
                return;
            }
            log.info("USER 2 Finished " + this.documentService.findById(document.getDocumentId()));
        });

        // cette méthode arrête tous les threads mêmes si ils n'ont pas fini
        // je ne vois pas pkoi vous l'utilisez ici, alors que vous utilisez la bonne méthode en dessous
        es.shutdown();
        try {
            es.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
