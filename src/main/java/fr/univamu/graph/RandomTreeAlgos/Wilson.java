package fr.univamu.graph.RandomTreeAlgos;


import fr.univamu.graph.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**
 * Implémentation de l'algorithme de Wilson pour générer un arbre couvrant aléatoire.
 */
public class Wilson {
    private final Graph graph;
    private final ArrayList<Edge> tree;
    private final BitSet visited;
    private final Random random;

    /**
     * Constructeur pour l'algorithme de Wilson.
     *
     * @param graph Le graphe sur lequel générer un arbre couvrant.
     * @param seed  La graine pour l'initialisation de l'objet Random.
     */
    public Wilson(Graph graph, long seed) {
        if (graph == null || graph.getOrder() == 0) {
            throw new IllegalArgumentException("Le graphe ne doit pas être null ou vide.");
        }

        this.graph = graph;
        this.tree = new ArrayList<>();
        this.visited = new BitSet(graph.getOrder());
        this.random = new Random(seed);
    }

    /**
     * Génère un arbre couvrant aléatoire à l'aide de l'algorithme de Wilson.
     *
     * @return Une liste d'arêtes représentant l'arbre couvrant généré.
     */
    public ArrayList<Edge> generateTree() {
        // Sélectionner un sommet de départ au hasard
        int start = random.nextInt(graph.getOrder());
        visited.set(start);

        // Pour chaque sommet non visité, trouver un chemin qui le relie
        // au graphe couvrant et ajouter tous les bords de ce chemin à
        // l'arbre
        for (int i = 0; i < graph.getOrder(); i++) {
            if (!visited.get(i)) {
                List<Arc> path = findPath(i);
                for (Arc arc : path) {
                    if (!visited.get(arc.getDest())) {
                        tree.add(arc.getSupport());
                        visited.set(arc.getDest());
                    }
                }
            }
        }

        return tree;
    }

    private List<Arc> findPath(int vertex) {
        List<Arc> path = new ArrayList<>();

        while (!visited.get(vertex)) {
            List<Arc> adjacency = graph.outAdjacency(vertex);
            Arc next = adjacency.get(random.nextInt(adjacency.size()));
            path.add(next);
            vertex = next.getDest();
        }

        return path;
    }
}
