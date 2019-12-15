# TP Modelisation - Gestion de la concurrence pour une API REST

>  Auteurs : SAMMANDAMOURTHY Suriya, KHERA-VEILLARD Angélique, RAMANANTSOA Jessica.

## Description

Cette application a pour but de gérer la concurrence sur les interfaces de mise à jour et de lock :
- *Optimistic Lock* avec la norme HTTP (version)
- *Pessimistic Lock* avec la gestion des verrous en back (/lock en base de données)

La **base de données** utilisée est MongoDB.
La **spécification swagger** se situe dans le dossier */specifications/spec-swagger.yml*

## Utilisation & Tests

### Afficher un document selon son id

>  Request URL : **/api/docs/{documentId}**

#### HTTP 200 : affichage du document

```
{
    "customLock": "unlocked",
    "documentId": "5dea7f2ae681e656346937b9",
    "created": "2019-12-06T17:17:46.359+0100",
    "updated": "2019-12-06T17:17:46.359+0100",
    "title": "Mon document",
    "creator": "Le créateur",
    "editor": "L'éditeur",
    "body": "Ceci est mon document"
    "version": 0
}
```

#### HTTP 500 : le document n'existe pas

```
{
    "errorType": "TECHNICAL",
    "errors": [
        {
            "errorCode": "err.tech.wired.unknown-error",
            "errorMessage": "An unknown error happened"
        }
    ]
}
```

### Créer un document:

>  Request URL : **/api/docs/**

#### HTTP 201 : le document est crée

Request

```
{
    "title": "Mon document",
    "creator": "Le créateur",
    "editor": "L'éditeur",
    "body": "Ceci est mon document"
}
```

Response

```
{
    "customLock": "unlocked",
    "documentId": "5dea7f2ae681e656346937b9",
    "created": "2019-12-06T17:17:46.359+0100",
    "updated": "2019-12-06T17:17:46.359+0100",
    "title": "Mon document",
    "creator": "Le créateur",
    "editor": "L'éditeur",
    "body": "Ceci est mon document"
    "version": 0
}
```

#### HTTP 400 bad request : le document n'est pas créé 

Si on omet un attribut ayant l'annotation @NotNull, par exemple le title :

Request

```
{
    "creator": "toto",
    "editor": "toto",
    "body": "ceci est mon premier document"
}
```

Response

```
{
    "errorType": "FUNCTIONAL",
    "errors": [
        {
            "errorCode": "err.func.wired.badrequest",
            "errorMessage": "Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.modelisation.tp.tpmodelisation.dto.DocDto> com.modelisation.tp.tpmodelisation.endpoint.DocController.createDoc(com.modelisation.tp.tpmodelisation.dto.DocDto,org.springframework.web.util.UriComponentsBuilder) with 2 errors: [Field error in object 'docDto' on field 'title': rejected value [null]; codes [NotBlank.docDto.title,NotBlank.title,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [docDto.title,title]; arguments []; default message [title]]; default message [title must not be blank]] [Field error in object 'docDto' on field 'title': rejected value [null]; codes [NotNull.docDto.title,NotNull.title,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [docDto.title,title]; arguments []; default message [title]]; default message [must not be null]] "
        }
    ]
}
```

### Liste des documents:

>  Request URL : **/api/docs/**

#### HTTP 201 : affichage de la liste des documents

```
{
    "totalElements": 3,
    "page": 0,
    "data": [
        {
            "documentId": "5dea7f2ae681e656346937b9",
            "created": "2019-12-06T17:17:46.359+0100",
            "updated": "2019-12-06T17:17:46.359+0100",
            "title": "Mon document",
            "creator": "Le créateur",
            "editor": "L'éditeur",
            "body": "Ceci est mon document"
        },
       .....
    ]
}
```

### Liste des documents avec pagination

La pagination débute à la page 0 et la taille peut être définie (ici 3).

> Request URL : **/api/docs?page=0&size=3** 

```
{
    "totalElements": 4,
    "page": 0,
    "data": [
        {
            "documentId": "5dea7f2ae681e656346937b9",
            "created": "2019-12-06T17:17:46.359+0100",
            "updated": "2019-12-06T17:17:46.359+0100",
            "title": "Mon document",
            "creator": "Le créateur",
            "editor": "L'éditeur",
            "body": "Ceci est mon document"
        },
        {
            "documentId": "5dea804ce681e656346937ba",
            "created": "2019-12-06T17:22:36.927+0100",
            "updated": "2019-12-06T17:22:36.927+0100",
            "title": "Deuxième document",
            "creator": "Créateur",
            "editor": "Editeur",
            "body": "Ceci est mon deuxième document"
        },
        {
            "documentId": "5dea8058e681e656346937bb",
            "created": "2019-12-06T17:22:48.434+0100",
            "updated": "2019-12-06T17:22:48.434+0100",
            "title": "Troisième document",
            "creator": "Créateur",
            "editor": "Editeur",
            "body": "Ceci est mon troisième document"
        }
    ]
}
```

> Request URL : **/api/docs?page=1** 

```
{
    "totalElements": 4,
    "page": 1,
    "data": [
        {
            "documentId": "5dea807be681e656346937bc",
            "created": "2019-12-06T17:23:23.062+0100",
            "updated": "2019-12-06T17:23:23.062+0100",
            "title": "Quatrième document",
            "creator": "Créateur",
            "editor": "Editeur",
            "body": "Ceci est mon quatrième document"
        }
    ]
}
```

### Mettre à jour un document - Prise en compte du Optimistic Lock

> Request URL : **/api/docs/{documentId}**

#### HTTP 200 : le document est mis à jour

Request

```
{
    "title": "Eragon",
    "creator": "Christopher Paolini",
    "editor": "Bayard Jeunesse",
    "version": 1
}
```

Response

```
{
    "documentId": "5dea7f12fcfc18577f422173",
    "created": "2019-12-06T17:17:22.095+0100",
    "updated": "2019-12-06T18:06:07.817+0100",
    "title": "Eragon",
    "creator": "Christopher Paolini",
    "editor": "Bayard Jeunesse",
    "version" : 2
}
```

#### HTTP 400 : Bad Request


* Le format JSON n'est pas correct. 

Request

```
{
    "title": /,
    "creator": "JK Rowling",
    "editor": "Je ne sais pas",
    "body": "Il était une fois ..."
}
```

Response

```
{
    "errorType": "FUNCTIONAL",
    "errors": [
        {
            "errorCode": "err.func.wired.badrequest",
            "errorMessage": "JSON parse error: Unexpected character ('/' (code 47)): maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser); nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character ('/' (code 47)): maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)\n at [Source: (PushbackInputStream); line: 2, column: 11]"
        }
    ]
}
```

* La version est **fausse** ou n'est pas **renseignée** (Optimistic Lock) 

Request


```
{
    "title": "Eragon",
    "creator": "Christopher Paolini",
    "editor": "Bayard Jeunesse",
    "version": 2
}
```

**OU**


```
{
    "title": "Eragon",
    "creator": "Christopher Paolini",
    "editor": "Bayard Jeunesse"
}
```

Response

```
{
    "errorType": "FUNCTIONAL",
    "errors": [
        {
            "errorCode": "err.func.wired.server",
            "errorMessage": "The server encountered an internal error or misconfiguration and was unable to complete your request"
        }
    ]
}
```

### Afficher le lock d'un document

>  Request URL : **/api/docs/{documentId}/lock**

#### HTTP 200 : affichage du lock d'un document

```
{
    "id": "5df2a114b382615a56a86cdd",
    "created": "2019-12-12T21:20:36.816+0100",
    "owner": "Suriya & Jessica & Angélique"
}
```

#### HTTP 500 : ce document ne possède pas de lock

```
{
    "errorType": "FUNCTIONAL",
    "errors": [
        {
            "errorCode": "err.func.wired.server",
            "errorMessage": "The server encountered an internal error or misconfiguration and was unable to complete your request"
        }
    ]
}
```

### Ajouter un lock à un document

>  Request URL : **/api/docs/{documentId}/lock**

Request

```
{
	"owner": "Suriya & Jessica & Angélique"
}
```

Response

#### HTTP 200 : affichage du lock d'un document

```
{
    "id": "5df2a03db382615a56a86cdc",
    "created": "2019-12-12T21:17:01.302+0100",
    "owner": "Suriya & Jessica & Angélique"
}
```

#### HTTP 409 : ce document possède déjà un lock (retourne le lock existant)

Response

```
{
    "id": "5df2a13db452380a56b86ckf",
    "created": "2019-12-12T22:13:01.302+0100",
    "owner": "toto"
}
```


### Supprimer le lock d'un document

>  Request URL : **/api/docs/{documentId}/lock**

#### HTTP 204 : le lock du document a été supprimé