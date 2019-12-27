package esipe.tp.com.gestionconcurence;

import esipe.tp.com.gestionconcurence.model.Document;
import esipe.tp.com.gestionconcurence.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class Locktest implements CommandLineRunner {
    @Autowired
    DocumentService documentService;

    @Override
    public void run(String... args) throws Exception {
        Document d = new Document();
        documentService.createDocument(d);
        Integer documentId = d.getDocumentId();
        System.out.println("-- document AJOUTER --");
        System.out.println(documentService.getDocument(documentId));

        ExecutorService es = Executors.newFixedThreadPool(2);

        es.execute(() ->{
            System.out.println(" -- updating TITLE TO ABERKANE --");
            Document d2 = documentService.getDocument(documentId);
            d2.setTitle("aberkane");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                documentService.updateDocument(d2);
            } catch (Exception e) {
                System.err.println("user1 " + e);
                System.out.println("user1 after error :" + documentService.getDocument(documentId));
                return;
            }
            System.out.println("user1 finished: " + documentService.getDocument(documentId));

        });

        es.execute(() -> {
            System.out.println(" -- updating TITLE TO BOB --");
            Document d3 = documentService.getDocument(documentId);
            d3.setTitle("bob");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                documentService.updateDocument(d3);
            } catch (Exception e) {
                System.err.println("user2 " + e);
                System.out.println("user2 after error :" + documentService.getDocument(documentId));
                return;
            }
            System.out.println("user2 finished: " + documentService.getDocument(documentId));

        });
        es.shutdown();
        try {
            es.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
