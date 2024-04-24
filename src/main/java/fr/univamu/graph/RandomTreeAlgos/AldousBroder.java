package fr.univamu.graph.RandomTreeAlgos;



import fr.univamu.graph.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Implémentation de l'algorithme d'Aldous-Broder pour générer un arbre couvrant aléatoire. */
public class AldousBroder {
    private final Graph graph;
    private final ArrayList<Edge> tree;
    private final boolean[] visited;
    private final Random random;

    /**
     * Constructeur pour l'algorithme d'Aldous-Broder.
     *
     * @param graph Le graphe sur lequel générer un arbre couvrant.
     * @param seed  La graine pour l'initialisation de l'objet Random.
     */
    public AldousBroder(Graph graph, long seed) {
        if (graph == null || graph.getOrder() == 0) {
            throw new IllegalArgumentException("Le graphe ne doit pas être null ou vide.");
        }

        this.graph = graph;
        this.tree = new ArrayList<>();
        this.visited = new boolean[graph.getOrder()];
        this.random = new Random(seed);
    }

    /**
     * Génère un arbre couvrant aléatoire à l'aide de l'algorithme d'Aldous-Broder.
     *
     * @return Une liste d'arêtes représentant l'arbre couvrant généré.
     */
    public ArrayList<Edge> generateTree() {
        int currentVertex = random.nextInt(graph.getOrder());
        visited[currentVertex] = true;

        while (tree.size() < graph.getOrder() - 1) {
            List<Arc> adjacency = graph.outAdjacency(currentVertex);
            if (adjacency.isEmpty()) continue; // Gestion des cas sans arcs sortants

            Arc arc = adjacency.get(random.nextInt(adjacency.size()));
            visit(arc);
            currentVertex = arc.getDest();
        }

        return tree;
    }

    /**
     * Visite un sommet et ajoute une arête à l'arbre si le sommet n'a pas été visité auparavant.
     *
     * @param arc L'arc à visiter.
     */
    private void visit(Arc arc) {
        if (!visited[arc.getDest()]) {
            tree.add(arc.getSupport());
            visited[arc.getDest()] = true;
        }
    }
}
