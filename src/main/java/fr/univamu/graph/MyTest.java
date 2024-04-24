package fr.univamu.graph;

import fr.univamu.graph.Graph;

import java.util.*;

import fr.univamu.graph.*;
import fr.univamu.graph.RandomTreeAlgos.Kruskal;

public class MyTest {

    public Graph graph;

    public MyTest() {
        graph = new Graph(5);
        graph.addEdge(new Edge(0, 0, 0.2));
        graph.addEdge(new Edge(0, 1, 0.3));
        graph.addEdge(new Edge(0, 3, 0.4));
        graph.addEdge(new Edge(2, 0, 0.5));

        graph.addEdge(new Edge(1, 2, 0.1));
    }
    public boolean DFS(ArrayList<LinkedList<Edge>> adjacency, int v, boolean[] visited, Stack<Integer> stack)
    {
        visited[v] = true;
        //System.out.println(v);

        for (int i = 0; i < adjacency.get(v).size(); i++) {

            int actualVertex = adjacency.get(v).get(i).getDest();

            if (stack.size() != 0) {
                //System.out.println(comp.peek());
                if (visited[actualVertex] && stack.peek() != actualVertex) {
                    //System.out.println(comp.get(comp.size() - 1));
                    //System.out.println(actualVertex);
                    //System.out.println("stop here");
                    //break;
                    System.out.println("Cycle found 1");
                    return true;
                }
            }


            if (!visited[actualVertex]) {
                //System.out.println(v + " " + actualVertex);
                stack.push(actualVertex);
                if (DFS(adjacency, actualVertex, visited, stack)) {
                    return true;
                }
            }


        }



        //System.out.println(stack);
        return false;

    }

    /** Fonction dédiée à l'opération "fill order */

    public List<Integer> fillOrder(Graph graph)

    {

        ArrayList<LinkedList<Edge>> adjacency = graph.getAdjacency();

        int V = graph.getOrder();

        Stack<Integer> stack = new Stack<>();

        boolean[] visited = new boolean[V];

        //System.out.println(adjacency.get(0).get(4).dest);

        boolean haveCycle = false;

        for (int i = 0; i < V; i++) {

            if (!visited[i]) {
                haveCycle = DFS(adjacency, i, visited, stack);
            }

            if (haveCycle) {
                System.out.println("Cycle found 2");
                break;
            }

        }


        return stack;

    }

    public void doIt() {
        System.out.println(this.fillOrder(this.graph));
    }


    public static void main(String[] args) {

        //MyTest test = new MyTest();
        //test.doIt();
        Graph graph2 = new Graph(5);
        graph2.addEdge(new Edge(0, 1, 0.3));
        graph2.addEdge(new Edge(0, 3, 0.4));
        graph2.addEdge(new Edge(2, 0, 0.5));

        graph2.addEdge(new Edge(1, 2, 0.1));

        Kruskal k = new Kruskal(graph2);

        //System.out.println(k.getEdgesWeightOrdered());
        //System.out.println(graph2.getOutAdjacency());

        ArrayList<Edge> aTree = Kruskal.generateTree(graph2);
        System.out.println(aTree);


    }
}

