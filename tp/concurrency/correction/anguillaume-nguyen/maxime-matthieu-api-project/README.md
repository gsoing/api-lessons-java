# Document API Manager

Groupe Maxime Nguyen, Matthieu Anguillaume


# URL
**Start**
POST: Créer une liste de 20 documents et leurs sommaires
http://localhost:8088/api/createList

**Lock**
POST: Créer un lock sur un document 
http://localhost:8088/documents/{id}/lock

GET: Récupère un lock sur un document 
http://localhost:8088/documents/{id}/lock

DELETE: Delete un lock sur un document 
http://localhost:8088/documents/{id}/lock

**Document**

GET: Récupère un document avec son ID
http://localhost:8088/api/documents/{id}

DELETE: Delete un document avec son ID
http://localhost:8088/api/documents/{id}

GET: Récupère tous les documents
http://localhost:8088/api/documents

GET: Récupère tout les documents avec PAGINATION
http://localhost:8088/documents/list?pageSize={nb}&pageNo={num}

**Sommaire**

PUT: Update un sommaire
http://localhost:8088/summary/{id}

## Warning

Au niveau du code, certaine mesure on été prise à cause de l'erreur **E11000 duplicate key error collection** , nous empêchant de create ou update sur les tables. 
C'est pourquoi, pour le CREATE bien qu'il y'ai l'annotation @version (idem pour l'UPDATE), nous sommes obligés de spécifier un id.
De même, pour l'UPDATE, nous sommes obligé de DELETE avant de recréer le document.


