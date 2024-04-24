package fr.univamu.graph.generators;


import fr.univamu.graph.*;

public class Complete {

	public Graph graph;
	public Complete(int order) {
		/* Vérifie que l'ordre est positif */
		if (order < 0) {
			throw new IllegalArgumentException("L'ordre du graphe doit être positif.");
		}

		this.graph = new Graph(order);
		for (int i = 0; i < order; i++) {
			for (int j = i + 1; j < order; j++) {
				graph.addEdge(new Edge(i, j, 0)); /* poids initialisé à valeur nulle */
			}
		}
	}
}

