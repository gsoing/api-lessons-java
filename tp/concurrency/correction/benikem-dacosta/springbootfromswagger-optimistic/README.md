Implémentation de la spécification SWAGGER par Mario DA COSTA & Nassim BENIKEN.

L'optimistic Locking est sur la branche master, la classe OptimisticLocking qui se lance au démarrage de l'application permet de tester l'implémentation de l'optimistic locking (voir les logs en sortie ainsi que le script dans la classe OptimisticLocking)
La simulation est aussi possible avec "./optimisticBashOne.sh & ./optimisticBashTwo.sh & wait" (vérifier la ressource avant de lancer).

Le Pessimistic Locking est sur une branche dédiée (PessimisticLocking), pour tester l'implémentation du Pessimistic locking, il est nécessaire de lancer deux opérations "POST/{documentId}" en simultanées.

Dans la classe DocumentServiceImpl, nous avons ajouté dans la méthode updateById un appel à Thread.sleep(5000) afin de simuler un traitement long pour que l'on puisse ensuite tester le lock sur une ressource.

Une requête POST/{documentId} prend donc un peu de temps à s'exécuter (légèrement plus de 5 secondes) ce qui est dû à cet appel.

