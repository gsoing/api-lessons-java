Il manque toute la gestion des objets lock.

Les annotations @Lock ne fonctionne pas sur mongo, il faut utiliser plutot un attribut version
sur l'objet sauvegardé.