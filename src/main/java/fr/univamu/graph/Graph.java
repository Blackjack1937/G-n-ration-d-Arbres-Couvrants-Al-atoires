package fr.univamu.graph;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph implements Cloneable {
    private int order;
    private final int upperBound;
    private int edgeCardinality;

    private final ArrayList<LinkedList<Edge>> adjacency;
    private final ArrayList<LinkedList<Arc>> inAdjacency;
    private final ArrayList<LinkedList<Arc>> outAdjacency;

    public Graph(int upperBound) {
        this.order = 0;
        this.upperBound = upperBound;
        this.adjacency = new ArrayList<>(upperBound + 1);
        this.inAdjacency = new ArrayList<>(upperBound + 1);
        this.outAdjacency = new ArrayList<>(upperBound + 1);

        for (int i = 0; i <= upperBound; i++) {
            this.adjacency.add(null);
            this.inAdjacency.add(null);
            this.outAdjacency.add(null);
        }
    }

    public boolean isVertex(int index) {
        return index >= adjacency.size() || adjacency.get(index) == null;
    }

    public void addVertex(int indexVertex) {
        if (isVertex(indexVertex)) {
            adjacency.set(indexVertex, new LinkedList<>());
            inAdjacency.set(indexVertex, new LinkedList<>());
            outAdjacency.set(indexVertex, new LinkedList<>());
            order++;
        }
    }

    public void ensureVertex(int indexVertex) {
        if (isVertex(indexVertex)) {
            addVertex(indexVertex);
        }
    }

    public void addArc(Arc arc) {
        outAdjacency.get(arc.getSource()).add(arc);
        inAdjacency.get(arc.getDest()).add(arc);
    }

    public void addEdge(Edge e) {
        ensureVertex(e.getDest());
        ensureVertex(e.getSource());

        boolean redunDest = false;
        boolean redunSource = false;

        for (Edge edge : adjacency.get(e.getDest())) {
            if (edge.getSource() == e.getSource() && edge.getDest() == e.getDest()) {
                redunDest = true;
                break;
            }
        }

        for (Edge edge : adjacency.get(e.getSource())) {
            if (edge.getSource() == e.getSource() && edge.getDest() == e.getDest()) {
                redunSource = true;
                break;
            }
        }

        if (!redunDest) {
            adjacency.get(e.getDest()).add(e);
        }

        if (!redunSource) {
            adjacency.get(e.getSource()).add(e);
        }

        addArc(new Arc(e, false));
        addArc(new Arc(e, true));
    }

    public Arc[] outNeighbours(int v) {
        if (outAdjacency.get(v) != null) {
            return outAdjacency.get(v).toArray(new Arc[0]);
        }
        return new Arc[0];
    }

    public ArrayList<LinkedList<Arc>> getOutAdjacency() {
        return outAdjacency;
    }

    public ArrayList<LinkedList<Edge>> getAdjacency() {
        return adjacency;
    }

    public int getOrder() {
        return order;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void removeArc(Arc arc) {
        this.outAdjacency.get(arc.getSource()).remove(arc);
        this.inAdjacency.get(arc.getDest()).remove(arc);
    }

    public Arc ensureArc(int v, Edge e) {
        for (Arc a : this.outAdjacency.get(v)) {
            if (a.getSupport().equals(e)) return a;
        }
        return null;
    }

    public void removeEdge(Edge e) {
        this.adjacency.get(e.getDest()).remove(e);
        this.adjacency.get(e.getSource()).remove(e);
        removeArc(ensureArc(e.getSource(), e));
        removeArc(ensureArc(e.getDest(), e));
    }

    @Override
    public Graph clone() {
        try {
            /* Copie de l'état mutable (si nécessaire)*/
            return (Graph) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public List<Arc> outAdjacency(int sommetActuel) {
        return outAdjacency.get(sommetActuel);
    }
}
