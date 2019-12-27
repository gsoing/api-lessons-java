# Commentaires

Globalement bon TP dans l'ensemble. L'utilisation de mongo est ambitieuse car la base ne gères pas les locks pour les transactions.

Donc mettre le documentId en id du lock oui sauf que la méthode ``save()`` fait un insert ou un update si le document est présent

Pas mal de code aurait pu être évité via l'utilisation d'annoations : 
````java
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Document>(this.documentsService.updateDocument(document,documentId), HttpStatus.OK);
        }
````
Peut-être remplacé par l'annoation `@Consume("application/json)` et pour info quand le content-type n'est pas correct
il faut retourner l'erreur ``415 Unsupported Media Type``

Sur les DTO tous les champs sont obligatoires, c'est compliqué lors de la création de connaitre les Id et les etag sur 
la création il faut laisser passer avec des champs vides

Egalement à partir de java 8 il y a des facons plus élégantes de faire : 
````java
        List<Document> liste = this.documentRepository.findAll();
        List<DocumentSummary> liste2 = new ArrayList<>();
        // On est en java8 les stream ca exsite :D
        for(Document doc : liste){
            liste2.add(new DocumentSummary(doc));
        }
````
````java
        DocumentsList documentsList = new DocumentsList();
        documentsList.data(this.documentRepository
            .findAll()
            .stream()
            .map(doc -> new DocumentSummary(doc))
            .collect(Collectors.toList()));
````

