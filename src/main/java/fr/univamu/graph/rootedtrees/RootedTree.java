package fr.univamu.graph.rootedtrees;

import fr.univamu.graph.RandomTreeAlgos.BreadthFirstSearch;
import fr.univamu.graph.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;


public class RootedTree {

	private int getHeight(Node n) {
		return (n == null ? -1 : n.height);
	}

	private int getSize(Node n) {
		return (n == null ? -1 : n.size);
	}


	private class Node {
		int vertex;
		ArrayList<Node> sons;

		int height;
		int size;
		int depth;

		public Node(int vertex) {
			this.vertex = vertex;
			this.sons = new ArrayList<>();
			this.height = 0;
		}


		public void setHeight() {
			int maxHeight = -1;
			for (Node son : this.sons)
				maxHeight = Math.max(maxHeight, son.height);
			this.height = maxHeight + 1;
		}


		private void setSize() {
			size = 1;
			for (Node son : this.sons) size = size + son.size;
		}

		private void setSonsDepth() {
			for (Node son : this.sons) son.depth = this.depth + 1;
		}

		private Node maxSizeSon() {
			Node maxSon = null;
			for (Node son : sons) {
				if (son.size > getSize(maxSon)) maxSon = son;
			}
			return maxSon;
		}

		private Node maxHeightSon() {
			Node maxSon = null;
			for (Node son : sons) {
				if (son.height > getHeight(maxSon)) maxSon = son;
			}
			return maxSon;
		}


		private int secondMaxHeight() {
			int maxHeight = -1;
			int secondMaxHeight = -1;
			for (Node son : sons) {
				if (son.height > secondMaxHeight) {
					secondMaxHeight = Math.min(maxHeight, son.height);
					maxHeight = Math.max(maxHeight, son.height);
				}
			}
			return secondMaxHeight;
		}

		private void print() {
			System.out.print("Node " + this.vertex + ", sons: ");
			for (Node son : this.sons) {
				System.out.print(son.vertex + " ");
			}
			System.out.println("(height: " + this.height
					+ ", size: " + this.size
					+ ", 2nd height: " + this.secondMaxHeight()
					+ ", depth: " + this.depth
					+ ")");
		}

	}


	ArrayList<Node> inverseBfsOrder;
	ArrayList<Node> bfsOrder;

	Node[] nodes;
	Node root;
	int order;


	/** Initialisation d'arbre */

	public void computeAllHeights() {
		for(Node n : inverseBfsOrder) n.setHeight();
	}


	public void computeAllSizes() {
		for (Node n : inverseBfsOrder) n.setSize();
	}


	public void computeAllDepths() {
		root.depth = 0;
		for (Node n : bfsOrder) n.setSonsDepth();
	}




	/** Sommes de distances entre les paires de sommets */
	public long getWienerIndex() {
		long count = 0;
		for (Node n : bfsOrder) {
			if (n == root) continue;
			count = count + (long) n.size * (order - n.size);
		}
		return count;
	}


	public int[] getDegreeDistribution(int maxDegree) {
		int maxIndex = Math.min(maxDegree,order-1);
		int[] degrees = new int[1+maxIndex];
		for(int i = 0; i <= maxIndex; i++) degrees[i] = 0;
		int degree;
		for (Node n : bfsOrder) {
			degree = n.sons.size() + (n == root ? 0 : 1);
			if (degree <= maxIndex)
				degrees[degree]++;
		}
		return degrees;
	}


	public int getRadius() {
		return root.height;
	}


	public int getDiameter() {
		return root.height + root.secondMaxHeight() + 1;
	}


	private Node getCentroidNode() {
		Node centroid = root;
		while (centroid.maxSizeSon().size * 2 > order)
			centroid = centroid.maxSizeSon();
		return centroid;
	}

	public int getDistanceFromCenterToCentroid() {
		return getCentroidNode().depth;
	}

	public double getAverageEccentricity() {
		int sumEccentricity = 0;
		for (Node n : bfsOrder)
			sumEccentricity = sumEccentricity + n.depth;
		return (double) sumEccentricity / (double) order;
	}


	/** Accès au "nodes" */

	public int getRoot() { return root.vertex; }

	public int getHeight(int vertex) {
		if (nodes[vertex] == null) {
			return 0;
		}
		return nodes[vertex].height;
	}

	public int getDepth(int vertex) {
		if (nodes[vertex] == null) {
			return 0;
		}
		return nodes[vertex].depth;
	}

	public int getSubtreeSize(int vertex) {
		return nodes[vertex].size;
	}

	public int getCentroid() {
		return getCentroidNode().vertex;
	}


	/** Printers */

	public void printStats() {
		System.out.println("Order: " + order);
		System.out.println("Diameter: " + getDiameter());
		System.out.println("Radius: " + getRadius());
		System.out.println("Wiener index: " + getWienerIndex());
		System.out.println("Center to centroid: "
				+ getDistanceFromCenterToCentroid());
		System.out.println("Average eccentricity: "
				+ getAverageEccentricity());
	}



	public void printNode(int vertex) {
		nodes[vertex].print();
	}

	public void printTree() {
		for (Node n : bfsOrder) n.print();
	}

	/** Ci-dessous jusqu'à la fin : construction de l'arbre à partir de la liste d'arcs.
	 * Nous voulons que le centre de l'arbre soit la racine.
	 * 1) createTree : Obtient un arbre encodé dans la structure Node.
	 * Ceci est réalisé en utilisant l'algorithme bfs sur le graphe des arêtes.
	 * À partir de la liste bfs des arcs, crée chaque nœud et l'attache à son père
	 * Stocke chaque nœud dans un tableau indexé par les sommets.
	 * 2) Calcule la hauteur de chaque nœud, dans l'ordre bfs inverse.
	 * 3) rerootTree : Déplace la racine vers le centre.
	 * les deux fils les plus hauts doivent avoir presque la même hauteur.
	 * il détecte si c'est équilibré,
	 * et si ce n'est pas le cas, déplace la racine vers le fils le plus haut (swapRootWith)
	 * 4) resetBfsOrdering : recalcule l'ordre bfs et bfs inverse.
	 * cette fois, un bfs sur la structure de nœud suffit
	 * 5) Calcule la hauteur, la taille et la profondeur de chaque nœud. */

	private void resetBfsOrdering() {
		Queue<Node> stack = new LinkedList<>();
		stack.offer(root);
		bfsOrder.clear();
		inverseBfsOrder.clear();
		Node current;
		while (!stack.isEmpty()) {
			current = stack.poll();
			for (Node son : current.sons) stack.offer(son);
			bfsOrder.add(current);
			inverseBfsOrder.add(current);
		}
		Collections.reverse(inverseBfsOrder);

	}


	private void swapRootWith(Node son) {
		root.sons.remove(son);
		root.setHeight();
		son.height = Math.max(root.height + 1, son.height);
		son.sons.add(root);
		root = son;
	}


	private boolean isUnbalanced() {
		return root.height > root.secondMaxHeight() + 2;
	}

	private void rerootTree() {
		computeAllHeights();
		while (isUnbalanced())
			swapRootWith(root.maxHeightSon());
		resetBfsOrdering();
	}


	private void createNode(Node[] nodes, Arc arc) {
		int son = arc.getDest();
		int father = arc.getSource();
		nodes[son] = new Node(son);
		nodes[father].sons.add(nodes[son]);
	}


	private void createTree(int root, ArrayList<Arc> sortedArcs) {
		this.bfsOrder = new ArrayList<>(order);
		this.inverseBfsOrder = new ArrayList<>(order);
		nodes = new Node[order];
		nodes[root] = new Node(root);

		this.bfsOrder.add(nodes[root]);
		for (Arc arc : sortedArcs) {
			createNode(nodes,arc);
			this.bfsOrder.add(nodes[arc.getDest()]);
		}

		inverseBfsOrder.addAll(bfsOrder);
		Collections.reverse(inverseBfsOrder);
		this.root = nodes[root];
	}


	public RootedTree(ArrayList<Edge> edges, int root) {
		this.order = edges.size() + 1;
		Graph graph = new Graph(order);
		for (Edge e : edges) graph.addEdge(e);

		createTree(root, BreadthFirstSearch.generateTree(graph, root));

		rerootTree();
		computeAllHeights();
		computeAllSizes();
		computeAllDepths();
	}


}
