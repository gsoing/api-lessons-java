# Tweets

L'idée ici est de présenter un exmple d'implémentation d'une API CRUD. L'API twitter s'y prête bien car elle est simple.

## CRUD
Ici on expose une api sous /api/v1/tweets 

### Création 
On peut créer un tweet avec la requête suivante
``` console
curl --location --request POST 'http://localhost:8080/api/v1/tweets' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text": "Hello World",
    "source": "WEB",
    "user": "guillaume"
}'
```
La réponse est la suivante : 
``` console
< HTTP/1.1 201 
< ETag: "efee1f994914b238c287e4e469ff014d"
< Last-Modified: Sun, 04 Oct 2020 10:16:47 GMT
< Location: http://localhost:8080/api/v1/tweets/5f79a10f4a7cd422aed5bca8
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sun, 04 Oct 2020 10:16:47 GMT

{"id":"5f79a10f4a7cd422aed5bca8","text":"Hello World","source":"WEB","created":"2020-10-04T12:16:47.267+0200","modified":"2020-10-04T12:16:47.267+0200"}
```

### Lecture
La requête de lecture
``` console
curl --location --request GET 'http://localhost:8080/api/v1/tweets/5f79a10f4a7cd422aed5bca8' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text": "Hello World",
    "source": "WEB",
    "user": "guillaume"
}'
``` 
La réponse :
``` console
< HTTP/1.1 200 
< Last-Modified: Sun, 04 Oct 2020 10:16:47 GMT
< ETag: "5badf87052ea04692664f54e7c022318"
< Cache-Control: max-age=36000
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sun, 04 Oct 2020 10:20:34 GMT 

{"id":"5f79a10f4a7cd422aed5bca8","text":"Hello World","source":"WEB","created":"2020-10-04T12:16:47.267+0200","modified":"2020-10-04T12:16:47.267+0200"}
``` 

### Mise à jour 
Requête :
``` console
curl --location --request PUT 'http://localhost:8080/api/v1/tweets/5f79a10f4a7cd422aed5bca8' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text": "Hello World 2",
    "source": "WEB",
    "user": "guillaume"
}'
``` 
Réponse :
``` console
< HTTP/1.1 200 
< Last-Modified: Sun, 04 Oct 2020 10:23:07 GMT
< ETag: "930a08b1098dc33967979e01536eca0e"
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sun, 04 Oct 2020 10:23:07 GMT

{"id":"5f79a10f4a7cd422aed5bca8","text":"Hello World 2","source":"WEB","created":"2020-10-04T12:16:47.267+0200","modified":"2020-10-04T12:23:07.233+0200"}
``` 