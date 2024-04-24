# Génération d'Arbres Couvrants Aléatoires

## Description
Ce projet a pour objectif de développer et d'analyser différents algorithmes pour la génération d'arbres couvrants aléatoires dans des graphes. Les algorithmes implémentés comprennent l'algorithme de Kruskal, le BFS (Breadth-First Search), ainsi que les algorithmes spécifiques pour la génération aléatoire comme Aldous-Broder et Wilson.

## Sources
- https://math.univ-lyon1.fr/irem/IMG/pdf/arbres_gloutons.pdf
- https://en.wikipedia.org/wiki/Minimum_spanning_tree#Faster_algorithms
## Structure du Projet
Le projet est structuré en plusieurs packages, chacun contenant des classes spécifiques :

- `Graph`: Contient les classes de base pour représenter des graphes, des arcs et des arêtes.
- `Generators`: Contient des classes pour générer des types spécifiques de graphes comme des grilles, des graphes
  complets, des graphes Erdős-Rényi, et des graphes en forme de sucette (Lollipop).
- `RandomTreeAlgos`: Contient les implémentations des algorithmes de génération d'arbres couvrants aléatoires.
- `Graphics`: Fournit une classe labyrinth qui permet la visualisation graphique des arbres et des graphes.
- `RootedTrees` : Fournit une classe dédiée aux arbres enracinés.
- `Main`: Le point d'entrée principal du projet qui coordonne la génération des arbres et leur analyse statistique.

## Détails des Algorithmes
### Aldous-Broder
Cet algorithme génère un arbre couvrant aléatoire en parcourant le graphe de manière aléatoire jusqu'à ce que tous les sommets soient visités.

### Wilson
Similaire à Aldous-Broder, mais utilise une approche différente pour connecter les sommets non visités à l'arbre couvrant existant.

### Kruskal
Génère un arbre couvrant minimal en ajoutant les arêtes dans l'ordre croissant de leur poids, en veillant à ne pas créer de cycles.

### Breadth-First Search (BFS)
Une implémentation non aléatoire, utilisée pour les comparaisons et les analyses.

## Statistiques et Analyse
Un module statistique calcule et affiche diverses métriques sur les arbres générés, telles que le diamètre, l'indice de Wiener et l'excentricité moyenne.

## Visualisation
La visualisation des arbres et des graphes est réalisée à l'aide de la bibliothèque graphique Java Swing, permettant une compréhension visuelle des structures générées.

## Améliorations Apportées
Au cours du développement, plusieurs améliorations ont été apportées pour optimiser le code, améliorer
l'encapsulation des données et augmenter la lisibilité et la maintenabilité du code. Plusieurs packages et classes
ont malheureusement été supprimés, car le projet initial nous a paru d'une grande complicité, et nous n'avions pas
pu utiliser correctement l'ensemble des fonctionnalités de la template initiale. Nous avons donc opté donc pour un
code plus simple avec moins de classes.

## Exécution
Pour exécuter le projet, lancez la classe `Main` avec les paramètres appropriés pour choisir le type de graphe et
l'algorithme de génération d'arbre (Default configuré sur Aldous-Broder).

## Résultats
Les résultats sont disponibles en sortie du code, avec les valeurs spécifique à chaque échantillon, un résultat général composé des valeurs moyennes, ainsi qu'un fichier image.

# Comparaison des Algorithmes de Génération d'Arbres Couvrants

Cette comparaison examine les performances et les caractéristiques des arbres couvrants générés par les algorithmes Aldous-Broder, Wilson, Kruskal et BFS (Breadth-First Search).

## Caractéristiques des Arbres

| Caractéristique      | Aldous-Broder        | Wilson              | Kruskal             | BFS           |
| -------------------- | -------------------- | ------------------- | ------------------- |---------------|
| Diamètre             | 414                  | 11                  | 1                   | 106           |
| Rayon                | 207                  | 6                   | (Très petit)        | 53            |
| Indice de Wiener     | 1,473,612,502        | 211,259             | 13,106              | 753,402       |
| Excentricité Moyenne | Environ 104.54       | Environ 0.011       | Environ 0.000336    | Environ 49.78 |

## Performance et Complexité

| Algorithme      | Temps de Calcul Moyen |
| --------------- |-----------------------|
| Aldous-Broder   | Environ 25.7 secondes |
| Wilson          | Environ 74.5 secondes |
| Kruskal         | Environ 91.0 secondes |
| BFS (Hypothétique) | Environ 5.2 secondes  |

## Analyse et Comparaison

### Aldous-Broder
- Génère des arbres étendus.
- Rapidité après BFS.
- Convient pour des structures étendues et une rapidité de calcul.

### Wilson
- Produit des arbres compacts.
- Diamètre et excentricité nettement inférieurs.
- Moins efficace en temps, mais fournit des arbres équilibrés.

### Kruskal
- Crée des arbres très compacts ou linéaires.
- Le plus lent en raison du tri des arêtes et de la vérification des cycles.

### BFS
- Génération la plus rapide.
- Diamètre modéré, ni trop étendu ni trop compact.

## Conclusion

- **Pour une génération rapide** : BFS est idéal pour des structures modérément étendues.
- **Pour des arbres compacts** : Wilson est préférable, malgré un temps de calcul plus long.
- **Pour des structures très compactes** : Kruskal, mais avec le temps de calcul le plus long.
- **Pour des structures étendues et un temps de calcul raisonnable** : Aldous-Broder offre un bon équilibre.

Cette comparaison met en évidence les différences de structure, de complexité et de temps de calcul entre chaque algorithme, soulignant l'importance du choix de l'algorithme en fonction des besoins spécifiques de l'application.

#  Précisions techniques

Le projet est écrit pour Java 20. Le moteur de production utilisé est Gradle. Il contient initialement deux tâches
```run``` implémentées, l'une permet de lancer une application avec sortie en console, l'autre une interface graphique
permettant de dessiner un graphe.

Nous avons essayé de résoudre le problème de dépreciation de Gradle 8.x par rapport au 9 grace au fichier "gradle properties",
sans succès. Nous nous sommes donc limités à supprimer l'avertissement du terminal seulement.

N-B : Une erreur pourrait arriver en fonction de la manière dont le fichier a été décompressé, il suffit de modifier le path "resources
à la fin de la classe main (le code fonctionnera de toute façon).

# Licence

Ce projet est sous licence "Apache 2.0" - voir le fichier LICENSE pour plus de détails.
