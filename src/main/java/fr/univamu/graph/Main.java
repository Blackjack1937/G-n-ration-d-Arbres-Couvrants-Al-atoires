package fr.univamu.graph;

import fr.univamu.graph.*;
import fr.univamu.graph.generators.*;
import fr.univamu.graph.Graphics.*;
import fr.univamu.graph.RandomTreeAlgos.*;
import fr.univamu.graph.rootedtrees.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;


public class Main {

	@SuppressWarnings("unused")
	private final static Random gen = new Random();

	static Grid grid = null;


	public static void main(String[] argv) {

		Graph graph = chooseFromGraphFamily();
		ArrayList<Edge> randomTree = null;

		int noOfSamples = 10;
		Stats stats = new Stats(noOfSamples);
		for (int i = 0; i < noOfSamples; i++) {
			randomTree = genTree(graph);
			stats.update(randomTree);
		}
		stats.print();

		if (grid != null) showGrid(grid, randomTree);
	}

	private static Graph chooseFromGraphFamily() {
		/* Paramétriser ici cette fonction afin de pouvoir choisir quelle classe de Graph utiliser */

		grid = new Grid(920 / 11, 580 / 11);

		//System.out.println(graph.getAdjacency());
		//Graph graph = new Complete(100).graph;
		//Graph graph = new ErdosRenyi(1_000, 100).graph;
		//Graph graph = new Lollipop(1_000).graph;
		return grid.graph;
	}

	public static ArrayList<Edge> genTree(Graph graph) {

		/* Paramétriser l'algorithme utilisé ici, ou bien le faire à l'aide de la ligne de commande */

		AldousBroder ab = new AldousBroder(graph, 0);
		//Wilson ab = new Wilson(graph, 0);
		// Non-random BFS
		//ArrayList<Edge> randomTree = Kruskal.generateTree(graph);

		return ab.generateTree();
	}


	private static class Stats {
		public int nbrOfSamples = 10;
		private int diameterSum = 0;
		private double eccentricitySum = 0;
		private long wienerSum = 0;
		private final int[] degreesSum = {0, 0, 0, 0, 0};
		long startingTime = 0;

		public Stats(int noOfSamples) {
			long startingTime = System.nanoTime();
		}

		public void print() {
			long delay = System.nanoTime() - startingTime;

			System.out.println("\n Testing of the  algorithm on " + nbrOfSamples + " samples :");
			System.out.println("\n - Average eccentricity : "
					+ (eccentricitySum / nbrOfSamples));
			System.out.println("\n - Average Wiener index : "
					+ (wienerSum / nbrOfSamples));
			System.out.println("\n - Average tree diameter : "
					+ (diameterSum / nbrOfSamples));
			System.out.println("\n - Average number of leaves : "
					+ (degreesSum[1] / nbrOfSamples));
			System.out.println("\n - Average number of 2nd degree vertices : "
					+ (degreesSum[2] / nbrOfSamples));
			System.out.println("\n - Average computing time : "
					+ delay / (nbrOfSamples * 1_000_000L) + "ms");

		}

		public void update(ArrayList<Edge> randomTree) {
			RootedTree rooted = new RootedTree(randomTree, 0);
			//rooted.printStats();
			diameterSum = diameterSum + rooted.getDiameter();
			eccentricitySum = eccentricitySum + rooted.getAverageEccentricity();
			wienerSum = wienerSum + rooted.getWienerIndex();

			int[] degrees = rooted.getDegreeDistribution(4);
			for (int j = 1; j < 5; j++) {
				degreesSum[j] = degreesSum[j] + degrees[j];
			}
		}

	}

	private static void showGrid(
			Grid grid,
			ArrayList<Edge> randomTree
	) {
		RootedTree rooted = new RootedTree(randomTree, 0);

		JFrame window = new JFrame("solution");
		final Labyrinth laby = new Labyrinth(grid, rooted);

		laby.setStyleBalanced();
		//laby.setShapeBigNodes();
		//laby.setShapeSmallAndFull();
		laby.setShapeSmoothSmallNodes();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(laby);
		window.pack();
		window.setLocationRelativeTo(null);


		for (final Edge e : randomTree) {
			laby.addEdge(e);
		}
		laby.drawLabyrinth();

		window.setVisible(true);

		/* Génération du fichier image */
		try {
			laby.saveImage("resources/random.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}