package fr.univamu.graph.generators;

import fr.univamu.graph.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe représentant un graphe en forme de sucette (lollipop).
 * Un graphe lollipop est composé d'un chemin (tige) et d'un graphe complet (tête).
 */
public class Lollipop {

	public Graph graph;

	public Lollipop(int order) {
		if (order < 3) {
			throw new IllegalArgumentException("L'ordre du graphe doit être au moins 3 pour créer un graphe lollipop.");
		}

		graph = new Graph(order);
		ArrayList<Integer> permutation = new ArrayList<>(order);
		for (int i = 0; i < order; i++)
			permutation.add(i);
		Collections.shuffle(permutation);

		int t = order / 3; // Taille de la tige de la sucette

		// Construction de la tige
		for (int i = 0; i < t; i++) {
			graph.addEdge(new Edge(permutation.get(i), permutation.get(i + 1), 0));
		}

		// Construction de la tête
		for (int i = t; i < order; i++) {
			for (int j = i + 1; j < order; j++) {
				graph.addEdge(new Edge(permutation.get(i), permutation.get(j), 0));
			}
		}
	}
}
