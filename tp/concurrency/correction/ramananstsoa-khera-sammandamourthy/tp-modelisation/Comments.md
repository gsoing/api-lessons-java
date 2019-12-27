Les spécifications sont respectées par contre la gestion de la concurrence est incomplète.

Sur la gestion du lock notamment :
```java
    public Doc addLock(Doc doc, String owner) {
        Doc docWithLock = docRepository.findById(doc.getDocumentId()).orElseThrow(() -> NotFoundException.DEFAULT);
        CustomLock lock = new CustomLock(owner);
        // Ici 2 threads peuvent créer le lock en parallèle
        lockRepository.save(lock);
    
        // ici on essaie de sauver l'identifiant du verrou, la gestion des versions
        // va bloquer une des deux mises à jour mais impossible de savoir laquelle
        docWithLock.setCustomLock(lock.getId());
        return docRepository.save(docWithLock);
    }
```

Pour le coup il aurait été plus judicieux de plutôt positionne l'identfiant du document sur l'object lock (cf. le correctif)
ou de mettre directement l'identifiant du document comme identifiant du lock


Il aurait été sympa de nettoyer un peu le code également.
