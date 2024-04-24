package fr.univamu.graph;

/**
 * Représente une arête dans un graphe avec un poids.
 */
public class Edge implements Comparable<Edge> {

  private final int source;
  private final int dest;
  private double weight;

  /**
   * Constructeur pour créer une arête.
   *
   * @param source Le sommet source de l'arête.
   * @param dest   Le sommet de destination de l'arête.
   * @param weight Le poids de l'arête.
   */
  public Edge(int source, int dest, double weight) {
    this.source = source;
    this.dest = dest;
    this.weight = weight;
  }

  @Override
  public String toString() {
    return "Edge{" +
            "source=" + source +
            ", dest=" + dest +
            ", weight=" + weight +
            '}';
  }

  @Override
  public int compareTo(Edge e) {
    return Double.compare(this.weight, e.weight);
  }

  /**
   * @param vertex Le sommet pour lequel trouver l'extrémité opposée.
   * @return L'extrémité opposée de l'arête.
   */
  public int oppositeExtremity(int vertex) {
    return (dest == vertex ? source : dest);
  }

  // Getters et Setters
  public int getSource() { return this.source; }
  public int getDest() { return this.dest; }
  public void setWeight(double weight) { this.weight = weight; }

  public int vertex2() {
    return 0;
  }
}

