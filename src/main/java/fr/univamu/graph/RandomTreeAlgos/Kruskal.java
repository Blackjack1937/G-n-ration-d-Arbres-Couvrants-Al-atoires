package fr.univamu.graph.RandomTreeAlgos;

import fr.univamu.graph.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Kruskal {

    Graph graph;
    Graph graph2;
    ArrayList<Edge> tree;
    boolean verbose = false;
    LinkedHashMap<String, Double> edgesWeightOrdered = new LinkedHashMap<>();

    public Kruskal(Graph graphI) {
        this.graph = graphI;
        LinkedHashMap<String, Double> edgesWeight = new LinkedHashMap<>();

        for (int i = 0; i < graph.getOrder(); i++) {
            if (graph.getAdjacency().get(i) != null) {
                for (int j = 0; j < graph.getAdjacency().get(i).size(); j++) {
                    Random r = new Random();
                    double randomValue = round(0 + (1) * r.nextDouble(), 1);
                    Edge edge = graph.getAdjacency().get(i).get(j);
                    edge.setWeight(randomValue);
                    edgesWeight.put(i + ":" + j, randomValue);
                }
            }
        }

        edgesWeight.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> edgesWeightOrdered.put(x.getKey(), x.getValue()));
    }

    public void search() {
        int counter = 0;
        tree = new ArrayList<>();
        graph2 = new Graph(graph.getOrder());

        for (Map.Entry<String, Double> entry : edgesWeightOrdered.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            int vertex1 = Integer.parseInt(key.split(":")[0]);
            int vertex2 = Integer.parseInt(key.split(":")[1]);
            Edge edge = graph.getAdjacency().get(vertex1).get(vertex2);

            if (counter > 1) {
                graph2.addEdge(edge);
                if (checkCycle(graph2)) {
                    graph2.removeEdge(edge);
                    continue;
                }
            }

            tree.add(edge);
            graph2.addEdge(edge);

            counter++;
        }
    }

    public static ArrayList<Edge> generateTree(Graph graph) {
        Kruskal algo = new Kruskal(graph);
        algo.search();
        return algo.tree;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean DFS(ArrayList<LinkedList<Edge>> adjacency, int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        if (adjacency.get(v) != null) {
            for (Edge edge : adjacency.get(v)) {
                int actualVertex = edge.getDest();

                if (!stack.isEmpty() && visited[actualVertex] && stack.peek() != actualVertex) {
                    return true;
                }

                if (!visited[actualVertex]) {
                    stack.push(actualVertex);
                    if (DFS(adjacency, actualVertex, visited, stack)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean checkCycle(Graph graph) {
        ArrayList<LinkedList<Edge>> adjacency = graph.getAdjacency();
        int V = graph.getOrder();

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];

        boolean haveCycle;

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                haveCycle = DFS(adjacency, i, visited, stack);
                if (haveCycle) {
                    return true;
                }
            }
        }

        return false;
    }

    public LinkedHashMap<String, Double> getEdgesWeightOrdered() {
        return edgesWeightOrdered;
    }
}