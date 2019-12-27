Attention il y a confusion, un DAO est un objet qui est lié à la base données, à ne pas confondre avec le repository
qui permet de requêter la base de donnée

Je sais bien que c'est un TP, mais ne jamais mettre de system.out dans du code et en plus vous utilisez un logger.

Pourquoi avoir fait un repository pour les "summary", ce n'est qu'une version simplifiée du même objet pas la peine de 
les stocker à part.

La gestion du verrou est incomplète également, la méthode save fait un update ou un insert donc si le verrou est déjà présent on l'écrase au lieu
de lever un exception
