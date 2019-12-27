Lors de la mise à jour il aurait été bien de vérifier qu'un verrou n'est pas posé.

La gestion des verrous est aussi un peu violente, chargés tous les verrous en mémoire pour le filtrer en mémoire,
le jour ou il y a 1 millions de verrou soit la base soit l'application plante. Vous avez mis l'id du document au niveau du verrou,
une requête avec un WHERE c'est possible aussi !!!

La pause du verrou n'est pas transactionnelle, comment vous vous assurez que personne ne pose de verrou.