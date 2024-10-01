Chagnon - Etame Mayi - Hossain - Nguyen - blocks3

Comment utiliser DEAP ?

× Comment allumer DEAP ?
Les classes et autres fichiers du projet sont dans le répertoire sources.
Après compilation, exécutez la classe DEAP.

La fenêtre est décomposée en 3 parties distinctes :
- la 1ere (en haut à gauche) est la zone Exécution . Elle vous permettra de voir les effets de vos programmes.
- la 2eme (en bas à gauche) est la zone Choix. Tout ce que vous avez besoin pour rédiger vos méthodes est ici. Elle présente différents onglets séparant les différentes catégories de blocs.
-la 3eme (à droite) est la zone Édition. C'est ici que vous pourrez créer vos méthodes et les exécuter.

× Comment utiliser DEAP ?

- Pour commencer à créer votre première méthode, appuyer sur le bouton "Ajouter un programme" dans la barre de menu de la zone Édition. Une colonne jaune va apparaître, vous pouvez maintenant coder.
- Pour construire un programme, il faut glisser-déposer des blocs de la zone Choix vers l'endroit du programme où vous voulez le mettre. Si rien ne se passe, c'est qu'il y a une erreur de type, ou que vous avez essayé de mettre une expression seule dans une ligne (les expressions sont toujours des arguments d'instructions).
-ATTENTION : nous avons du faire un compromis au niveau du code : un bloc ne peut pas être déplacé si il possède des fils. Par exemple, si vous voulez déplacer une somme, il faut d'abord lui retirer ses deux termes. La même règle s'applique avec les instructions d'une boucle. Elle ne s'applique pas au membre gauche d'une affectation.
-ATTENTION : quand vous modifiez le nom d'une variable ou la valeur d'une constante au clavier, veillez à bien appuyer sur entrée.
-Nous avons implémenté les variables. Il n'y a pas de déclaration, mais que des affectations (un peu comme en Python). Il y a trois environnements différents (pour les trois types utilisés). Donc on peut créer une variable entière de nom "x" et une variable booléenne de nom "x", ce ne seront pas les mêmes.
-Il y a un code couleur pour chaque type. Vous vous y retrouverez facilement.
-Le bouton "exécuter" d'un programme ne sera pas cliquable si les variables utilisées ou les arguments des opérations ne sont pas toutes initialisées.
-L'instruction "Affiche" prends n'importe quelle  expression en paramètre et l'affiche dans votre terminal.
-L'instruction "Trace" permet de lever le crayon si il est baissé, ou de le baisser si il est levé. Quand le crayon est baissé, le sprite est censé laisser une trace derrière lui lorsqu'il se déplace. Elle ne marche pas très bien selon l'ordinateur :(
-L'instruction "Salut" déclenche une petite animation de la part du sprite.
-Pour déplacer une constante ou une variable, il faut cliquer sur la zone violette à gauche du bloc.
