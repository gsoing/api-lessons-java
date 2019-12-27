Bon TP dans l'ensemble

Attention ne pas confondre DTO (data transfer object) et repository, 
un dto est un objet qui transite en les programmes : https://fr.wikipedia.org/wiki/Objet_de_transfert_de_donn%C3%A9es

Il aurait été plaisant de faire un petit effort en utilisant les api java8
```java
        List<Document> l1 = this.documentDto.findAll();
        List<DocumentSummary> l2 = new ArrayList<>();
        for(Document doc : l1){
            l2.add(new DocumentSummary(doc));
        }
        DocumentsList documentsList = new DocumentsList();
        documentsList.data(l2);
        return documentsList;
```
on peut faire cela : 
```java
List<DocumentSummary> mylist = this.documentDto.findAll()
                            .stream()
                            .map(doc -> { new DocumentSummary(doc); })
                            .collect(Collectors.toList());
DocumentsList documentsList = new DocumentsList();
documentList.data(mylist);
```

La gestion des verrous n'est pas bonne, la méthode save() fait un update ou un insert donc ici si le verrou existe déjà cela va simplement le mettre à jour