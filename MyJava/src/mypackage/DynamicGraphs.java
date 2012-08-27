/*This program uses Weighted Graphs technology.
 *The program lets the users create a weighted graph dynamically. 
 *The user can create a vertex by entering its name and location and
 *also user can create an edge to connect two vertices.
 *The user may specify two vertices and let the program display their shortest path
 *in blue.
 *
 *
 *More about Graphs
 *Graphs play an important role in modeling real-world problems. For example, the problem to
 *find the shortest distance between two cities can be modeled using a graph, where the vertices
 *represent cities and the edges represent the roads and distances between two adjacent cities. 
 *The problem of finding the shortest distance between two cities is
 *reduced to finding a shortest path between two vertices in a graph.
 *The study of graph problems is known as graph theory. Graph theory was founded by
 *Leonard Euler in 1736, when he introduced graph terminology to solve the famous Seven
 *Bridges of Königsberg problem. The city of Königsberg, Prussia (now Kaliningrad, Russia)
 *was divided by the Pregel River. There were two islands on the river. The city and islands
 *were connected by seven bridges. The question is, can one take a
 *walk, cross each bridge exactly once, and return to the starting point? Euler proved that it is
 *not possible.
 *To establish a proof, Euler first abstracted the Königsberg city map by eliminating all
 *streets, producing the sketch. Second, he replaced each land mass
 *with a dot, called a vertex or a node, and each bridge with a line, called an edge, as shown in
 *This structure with vertices and edges is called a graph.
 *Looking at the graph, we ask whether there is a path starting from any vertex, traversing all
 *edges exactly once, and returning to the starting vertex. Euler proved that for such path to
 *exist, each vertex must have an even number of edges. Therefore, the Seven Bridges of
 *Königsberg problem has no solution.
 *Graph problems are often solved using algorithms. Graph algorithms have many applications
 *in various areas, such as in computer science, mathematics, biology, engineering, economics,
 *genetics, and social sciences.
 */

package mypackage;

import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

public class DynamicGraphs extends JApplet {
	GraphView graphView;
	JTextField jtfName = new JTextField();
	JTextField jtfWeight = new JTextField();
	JTextField jtfX = new JTextField();
	JTextField jtfY = new JTextField();
	JTextField jtfU = new JTextField();
	JTextField jtfV = new JTextField();
	JTextField jtfStarting = new JTextField();
	JTextField jtfEnding = new JTextField();
	private WeightedGraph<City> graph = new WeightedGraph<City>(
			new java.util.ArrayList<WeightedEdge>(), 0);

	public DynamicGraphs() {

		JPanel jpSouth = new JPanel(new GridLayout(1, 3));

		JPanel jp1 = new JPanel(new GridLayout(4, 2));
		jp1.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1),
				"Add a new vertex"));
		jp1.add(new JLabel("Vertex name: ", JLabel.LEFT));
		jp1.add(jtfName);
		jp1.add(new JLabel("x-coordinates: ", JLabel.LEFT));
		jp1.add(jtfX);
		jp1.add(new JLabel("y-coordinates: ", JLabel.LEFT));
		jp1.add(jtfY);
		jp1.add(new JPanel());
		JButton jbtAddVertex = new JButton("Add Vertex");
		jp1.add(jbtAddVertex);
		jpSouth.add(jp1);

		JPanel jp2 = new JPanel(new GridLayout(4, 2));
		jp2.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1),
				"Add a new edge"));
		jp2.add(new JLabel("Vertex u (name): ", JLabel.LEFT));
		jp2.add(jtfU);
		jp2.add(new JLabel("Vertex v (name): ", JLabel.LEFT));
		jp2.add(jtfV);
		jp2.add(new JLabel("Weight (int): ", JLabel.LEFT));
		jp2.add(jtfWeight);
		jp2.add(new JPanel());
		JButton jbtAddEdge = new JButton("Add Edge");
		jp2.add(jbtAddEdge);
		jpSouth.add(jp2);

		JPanel jp3 = new JPanel(new GridLayout(3, 2));
		jp3.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1),
				"Find a shortest path"));
		jp3.add(new JLabel("Starting vertex: ", JLabel.LEFT));
		jp3.add(jtfStarting);
		jp3.add(new JLabel("Ending vertex: ", JLabel.LEFT));
		jp3.add(jtfEnding);
		jp3.add(new JPanel());
		JButton jbtShortestPath = new JButton("Shortest Path");
		jp3.add(jbtShortestPath);
		jpSouth.add(jp3);

		add(jpSouth, BorderLayout.SOUTH);
		add(graphView = new GraphView(graph));

		jbtAddVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = jtfName.getText();
				java.util.List<City> vertices = graph.getVertices();
				for (int i = 0; i < vertices.size(); i++) {
					if (vertices.get(i).name.equals(name)) {
						JOptionPane.showMessageDialog(null,
								"Vertex already exist");
						return;
					}
				}
				int x = 0;
				int y = 0;
				try {
					x = Integer.parseInt(jtfX.getText());
					y = Integer.parseInt(jtfY.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				City city = new City(name, x, y);
				graph.addVertex(city);
				graphView.repaint();
			}
		});
		jbtAddEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String u = jtfU.getText();
				String v = jtfV.getText();
				int weight;
				try {
					weight = Integer.parseInt(jtfWeight.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				int indexU = -1;
				int indexV = -1;
				java.util.List<City> vertices = graph.getVertices();
				for (int i = 0; i < vertices.size(); i++) {
					if (vertices.get(i).name.equals(u))
						indexU = i;
					else if (vertices.get(i).name.equals(v))
						indexV = i;
					if (indexU != -1 && indexV != -1)
						break;
				}
				if (indexU == -1 || indexV == -1) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				graph.addEdge(indexU, indexV, weight);
				graph.addEdge(indexV, indexU, weight);
				graphView.repaint();
			}
		});
		jbtShortestPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String startString = jtfStarting.getText();
				String endString = jtfEnding.getText();
				int startIndex = -1;
				int endIndex = -1;
				java.util.List<City> vertices = graph.getVertices();
				for (int i = 0; i < vertices.size(); i++) {
					if (vertices.get(i).name.equals(startString))
						startIndex = i;
					else if (vertices.get(i).name.equals(endString))
						endIndex = i;
					if (startIndex != -1 && endIndex != -1)
						break;
				}
				if (startIndex == -1 || endIndex == -1) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				java.util.List<Integer> path = graph
						.getShortestPath(startIndex).getIndexesPath(endIndex);
				graphView.setPath(path);
				graphView.repaint();
			}
		});
		graphView.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				jtfX.setText(e.getX() + "");
				jtfY.setText(e.getY() + "");
			}
		});
	}

	static class City implements Displayable {
		private int x, y;
		private String name;

		City(String name, int x, int y) {
			this.name = name;
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public String getName() {
			return name;
		}

		public boolean equals(Object o) {
			return name.equals(o);
		}

		public int hashCode() {
			return name.hashCode();
		}
	}

	public class GraphView extends JPanel {
		private java.util.List<Integer> path;
		private WeightedGraph<? extends Displayable> graph;

		public GraphView(WeightedGraph<? extends Displayable> graph) {
			this.graph = graph;
		}

		public void setPath(java.util.List<Integer> path) {
			this.path = path;
			repaint();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Draw vertices
			java.util.List<? extends Displayable> vertices = graph
					.getVertices();
			for (int i = 0; i < graph.getSize(); i++) {
				int x = vertices.get(i).getX();
				int y = vertices.get(i).getY();
				String name = vertices.get(i).getName();

				g.fillOval(x - 8, y - 8, 16, 16); // Display a vertex
				g.drawString(name, x - 12, y - 12); // Display the name
			}

			// Draw edges and their weights
			java.util.List<java.util.PriorityQueue<WeightedEdge>> queues = graph
					.getWeightedEdges();
			for (java.util.PriorityQueue<WeightedEdge> queue : queues) {
				for (WeightedEdge edge : queue) {
					int x1 = graph.getVertex(edge.u).getX();
					int y1 = graph.getVertex(edge.u).getY();
					int x2 = graph.getVertex(edge.v).getX();
					int y2 = graph.getVertex(edge.v).getY();
					g.drawLine(x1, y1, x2, y2); // Draw an edge for (i, v)

					int weightX = (x1 + x2) / 2 + 3;
					int weightY = (y1 + y2) / 2 - 5;
					g.drawString("" + edge.weight, weightX, weightY);
				}
			}
			if (path != null) {
				g.setColor(Color.GREEN);
				for (int i = 1; i < path.size(); i++) {
					int u = path.get(i - 1);
					int v = path.get(i);
					int x1 = graph.getVertex(u).getX();
					int y1 = graph.getVertex(u).getY();
					int x2 = graph.getVertex(v).getX();
					int y2 = graph.getVertex(v).getY();
					g.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Weighted Graph");
		DynamicGraphs applet = new DynamicGraphs();
		frame.add(applet);
		applet.init();
		applet.start();

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 550);
		frame.setVisible(true);
	}
}

interface Displayable {
	public int getX(); // Get x-coordinate of the vertex

	public int getY(); // Get x-coordinate of the vertex

	public String getName(); // Get display name of the vertex
}

class WeightedGraph<V> extends AbstractGraph<V> {
	private static final long serialVersionUID = 1L;
	// Priority adjacency lists
	private List<PriorityQueue<WeightedEdge>> queues;

	/** Construct a WeightedGraph from edges and vertices in arrays */
	public WeightedGraph(int[][] edges, V[] vertices) {
		super(edges, vertices);
		createQueues(edges, vertices.length);
	}

	/** Construct a WeightedGraph from edges and vertices in List */
	public WeightedGraph(int[][] edges, int numberOfVertices) {
		super(edges, numberOfVertices);
		createQueues(edges, numberOfVertices);
	}

	/** Construct a WeightedGraph for vertices 0, 1, 2 and edge list */
	public WeightedGraph(List<WeightedEdge> edges, List<V> vertices) {
		super((List) edges, vertices);
		createQueues(edges, vertices.size());
	}

	/** Construct a WeightedGraph from vertices 0, 1, and edge array */
	public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
		super((List) edges, numberOfVertices);
		createQueues(edges, numberOfVertices);
	}

	/** Create priority adjacency lists from edge arrays */
	private void createQueues(int[][] edges, int numberOfVertices) {
		queues = new ArrayList<PriorityQueue<WeightedEdge>>();
		for (int i = 0; i < numberOfVertices; i++) {
			queues.add(new PriorityQueue<WeightedEdge>()); // Create a queue
		}

		for (int i = 0; i < edges.length; i++) {
			int u = edges[i][0];
			int v = edges[i][1];
			int weight = edges[i][2];
			// Insert an edge into the queue
			queues.get(u).offer(new WeightedEdge(u, v, weight));
		}
	}

	/** Create priority adjacency lists from edge lists */
	private void createQueues(List<WeightedEdge> edges, int numberOfVertices) {
		queues = new ArrayList<PriorityQueue<WeightedEdge>>();
		for (int i = 0; i < numberOfVertices; i++) {
			queues.add(new PriorityQueue<WeightedEdge>()); // Create a queue
		}

		for (WeightedEdge edge : edges) {
			queues.get(edge.u).offer(edge); // Insert an edge into the queue
		}
	}

	/** Display edges with weights */
	public void printWeightedEdges() {
		for (int i = 0; i < queues.size(); i++) {
			System.out.print("Vertex " + i + ": ");
			for (WeightedEdge edge : queues.get(i)) {
				System.out.print("(" + edge.u + ", " + edge.v + ", "
						+ edge.weight + ") ");
			}
			System.out.println();
		}
	}

	/** Get a minimum spanning tree rooted at vertex 0 */
	public MST getMinimumSpanningTree() {
		return getMinimumSpanningTree(0);
	}

	/** Get a minimum spanning tree rooted at a specified vertex */
	public MST getMinimumSpanningTree(int startingIndex) {
		List<Integer> T = new ArrayList<Integer>();
		// T initially contains the startingVertex;
		T.add(startingIndex);

		int numberOfVertices = vertices.size(); // Number of vertices
		int[] parent = new int[numberOfVertices]; // Parent of a vertex
		// Initially set the parent of all vertices to -1
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1;
		int totalWeight = 0; // Total weight of the tree thus far

		// Clone the priority queue, so to keep the original queue intact
		List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

		// All vertices are found?
		while (T.size() < numberOfVertices) {
			// Search for the vertex with the smallest edge adjacent to
			// a vertex in T
			int v = -1;
			double smallestWeight = Double.MAX_VALUE;
			for (int u : T) {
				while (!queues.get(u).isEmpty()
						&& T.contains(queues.get(u).peek().v)) {
					// Remove the edge from queues[u] if the adjacent
					// vertex of u is already in T
					queues.get(u).remove();
				}

				if (queues.get(u).isEmpty()) {
					continue; // Consider the next vertex in T
				}

				// Current smallest weight on an edge adjacent to u
				WeightedEdge edge = queues.get(u).peek();
				if (edge.weight < smallestWeight) {
					v = edge.v;
					smallestWeight = edge.weight;
					// If v is added to the tree, u will be its parent
					parent[v] = u;
				}
			} // End of for

			T.add(v); // Add a new vertex to the tree
			totalWeight += smallestWeight;
		} // End of while

		return new MST(startingIndex, parent, T, totalWeight);
	}

	/** Clone an array of queues */
	private List<PriorityQueue<WeightedEdge>> deepClone(
			List<PriorityQueue<WeightedEdge>> queues) {
		List<PriorityQueue<WeightedEdge>> copiedQueues = new ArrayList<PriorityQueue<WeightedEdge>>();

		for (int i = 0; i < queues.size(); i++) {
			copiedQueues.add(new PriorityQueue<WeightedEdge>());
			for (WeightedEdge e : queues.get(i)) {
				copiedQueues.get(i).add(e);
			}
		}

		return copiedQueues;
	}

	/** MST is an inner class in WeightedGraph */
	public class MST extends Tree {
		private int totalWeight; // Total weight of all edges in the tree

		public MST(int root, int[] parent, List<Integer> searchOrder,
				int totalWeight) {
			super(root, parent, searchOrder);
			this.totalWeight = totalWeight;
		}

		public int getTotalWeight() {
			return totalWeight;
		}
	}

	/** Find single source shortest paths */
	public ShortestPathTree getShortestPath(int sourceIndex) {
		// T stores the vertices whose path found so far
		List<Integer> T = new ArrayList<Integer>();
		// T initially contains the sourceVertex;
		T.add(sourceIndex);

		// vertices is defined in AbstractGraph
		int numberOfVertices = vertices.size();

		// parent[v] stores the previous vertex of v in the path
		int[] parent = new int[numberOfVertices];
		parent[sourceIndex] = -1; // The parent of source is set to -1

		// costs[v] stores the cost of the path from v to the source
		int[] costs = new int[numberOfVertices];
		for (int i = 0; i < costs.length; i++) {
			costs[i] = Integer.MAX_VALUE; // Initial cost set to infinity
		}
		costs[sourceIndex] = 0; // Cost of source is 0

		// Get a copy of queues
		List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

		// Expand verticesFound
		while (T.size() < numberOfVertices) {
			int v = -1; // Vertex to be determined
			int smallestCost = Integer.MAX_VALUE; // Set to infinity
			for (int u : T) {
				while (!queues.get(u).isEmpty()
						&& T.contains(queues.get(u).peek().v)) {
					queues.get(u).remove(); // Remove the vertex in
											// verticesFound
				}

				if (queues.get(u).isEmpty()) {
					// All vertices adjacent to u are in verticesFound
					continue;
				}

				WeightedEdge e = queues.get(u).peek();
				if (costs[u] + e.weight < smallestCost) {
					v = e.v;
					smallestCost = costs[u] + e.weight;
					// If v is added to the tree, u will be its parent
					parent[v] = u;
				}
			} // End of for
			if (v == -1)
				break;
			T.add(v); // Add a new vertex to T
			costs[v] = smallestCost;
		} // End of while

		// Create a ShortestPathTree
		return new ShortestPathTree(sourceIndex, parent, T, costs);
	}

	/** ShortestPathTree is an inner class in WeightedGraph */
	public class ShortestPathTree extends Tree {
		private int[] costs; // costs[v] is the cost from v to source

		/** Construct a path */
		public ShortestPathTree(int source, int[] parent,
				List<Integer> searchOrder, int[] costs) {
			super(source, parent, searchOrder);
			this.costs = costs;
		}

		/** Return the cost for a path from the root to vertex v */
		public int getCost(int v) {
			return costs[v];
		}

		/** Print paths from all vertices to the source */
		public void printAllPaths() {
			System.out.println("All shortest paths from "
					+ vertices.get(getRoot()) + " are:");
			for (int i = 0; i < costs.length; i++) {
				printPath(i); // Print a path from i to the source
				System.out.println("(cost: " + costs[i] + ")"); // Path cost
			}
		}
	}

	public List<PriorityQueue<WeightedEdge>> getWeightedEdges() {
		return queues;
	}

	public void addVertex(V vertex) {
		super.addVertex(vertex);
		queues.add(new PriorityQueue<WeightedEdge>());
	}

	public void addEdge(int u, int v, int weight) {
		super.addEdge(u, v);
		queues.get(u).add(new WeightedEdge(u, v, weight));
		queues.get(v).add(new WeightedEdge(v, u, weight));
	}

	public List<Integer> getShortestHamiltonianCycle() {
		int minDistance = Integer.MAX_VALUE;
		List<Integer> shortestPath = null;
		for (int i = 0; i < vertices.size(); i++) {
			List<Integer> path = new ArrayList<Integer>();
			Integer weight = getShortestHamiltonianCycleWeight(i, path,
					new boolean[vertices.size()], i, 0, queues);
			if (weight != null) {
				if (weight < minDistance) {
					minDistance = weight;
					shortestPath = path;
				}
			}
		}
		return shortestPath;
	}

	/**
	 * return the shortest Hamiltonian cycle from specified index. Returns null
	 * is such path does not exist
	 * 
	 * @param index
	 * @return List<Integer>
	 */
	public List<Integer> getShortestHamiltonianCycleFromIndex(int index) {
		List<Integer> path = new ArrayList<Integer>();
		getShortestHamiltonianCycleWeight(index, path,
				new boolean[vertices.size()], index, 0, queues);
		return path;
	}

	protected Integer getShortestHamiltonianCycleWeight(int v,
			List<Integer> path, boolean[] isVisited, int endIndex,
			int totalWeight, List<PriorityQueue<WeightedEdge>> list) {
		isVisited[v] = true; // Mark vertex v visited

		if (allVisited(isVisited)) {
			if (neighbors.get(v).contains(endIndex)) {
				path.add(v);
				return totalWeight;
			}
		}

		for (WeightedEdge edge : list.get(v)) {
			if (!isVisited[edge.v]
					&& getShortestHamiltonianCycleWeight(edge.v, path,
							isVisited, endIndex, totalWeight, list) != null) {
				path.add(0, v);
				return totalWeight + edge.weight;
			}
		}

		isVisited[v] = false; // Backtrack, v is marked unvisited now
		return null;
	}

	/** Return true if all elements in array isVisited are true */
	private boolean allVisited(boolean[] isVisited) {
		boolean result = true;

		for (int i = 0; i < getSize(); i++)
			result = result && isVisited[i];

		return result;
	}
}

abstract class AbstractGraph<V> implements Graph<V> {
	protected List<V> vertices; // Store vertices
	protected List<List<Integer>> neighbors; // Adjacency lists

	/** Construct a graph from edges and vertices stored in arrays */
	protected AbstractGraph(int[][] edges, V[] vertices) {
		this.vertices = new ArrayList<V>();
		for (int i = 0; i < vertices.length; i++)
			this.vertices.add(vertices[i]);

		createAdjacencyLists(edges, vertices.length);
	}

	/** Construct a graph from edges and vertices stored in List */
	protected AbstractGraph(List<Edge> edges, List<V> vertices) {
		this.vertices = vertices;
		createAdjacencyLists(edges, vertices.size());
	}

	/** Construct a graph for integer vertices 0, 1, 2 and edge list */
	protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
		vertices = new ArrayList<V>(); // Create vertices
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V) (new Integer(i))); // vertices is {0, 1, ...}
		}
		createAdjacencyLists(edges, numberOfVertices);
	}

	/** Construct a graph from integer vertices 0, 1, and edge array */
	protected AbstractGraph(int[][] edges, int numberOfVertices) {
		vertices = new ArrayList<V>(); // Create vertices
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V) (new Integer(i))); // vertices is {0, 1, ...}
		}
		createAdjacencyLists(edges, numberOfVertices);
	}

	/** Create adjacency lists for each vertex */
	private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
		// Create a linked list
		neighbors = new ArrayList<List<Integer>>();
		for (int i = 0; i < numberOfVertices; i++) {
			neighbors.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < edges.length; i++) {
			int u = edges[i][0];
			int v = edges[i][1];
			neighbors.get(u).add(v);
		}
	}

	/** Create adjacency lists for each vertex */
	private void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
		// Create a linked list
		neighbors = new ArrayList<List<Integer>>();
		for (int i = 0; i < numberOfVertices; i++) {
			neighbors.add(new ArrayList<Integer>());
		}

		for (Edge edge : edges) {
			neighbors.get(edge.u).add(edge.v);
		}
	}

	/** Return the number of vertices in the graph */
	public int getSize() {
		return vertices.size();
	}

	/** Return the vertices in the graph */
	public List<V> getVertices() {
		return vertices;
	}

	/** Return the object for the specified vertex */
	public V getVertex(int index) {
		return vertices.get(index);
	}

	/** Return the index for the specified vertex object */
	public int getIndex(V v) {
		return vertices.indexOf(v);
	}

	/** Return the neighbors of vertex with the specified index */
	public List<Integer> getNeighbors(int index) {
		return neighbors.get(index);
	}

	/** Return the degree for a specified vertex */
	public int getDegree(int v) {
		return neighbors.get(v).size();
	}

	/** Return the adjacency matrix */
	public int[][] getAdjacencyMatrix() {
		int[][] adjacencyMatrix = new int[getSize()][getSize()];

		for (int i = 0; i < neighbors.size(); i++) {
			for (int j = 0; j < neighbors.get(i).size(); j++) {
				int v = neighbors.get(i).get(j);
				adjacencyMatrix[i][v] = 1;
			}
		}

		return adjacencyMatrix;
	}

	/** Print the adjacency matrix */
	public void printAdjacencyMatrix() {
		int[][] adjacencyMatrix = getAdjacencyMatrix();
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[0].length; j++) {
				System.out.print(adjacencyMatrix[i][j] + " ");
			}

			System.out.println();
		}
	}

	/** Print the edges */
	public void printEdges() {
		for (int u = 0; u < neighbors.size(); u++) {
			System.out.print("Vertex " + u + ": ");
			for (int j = 0; j < neighbors.get(u).size(); j++) {
				System.out.print("(" + u + ", " + neighbors.get(u).get(j)
						+ ") ");
			}
			System.out.println();
		}
	}

	/** Edge inner class inside the AbstractGraph class */
	public static class Edge {
		public int u; // Starting vertex of the edge
		public int v; // Ending vertex of the edge

		/** Construct an edge for (u, v) */
		public Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}
	}

	/** Obtain a DFS tree starting from vertex v */
	/** To be discussed in Section 27.6 */
	public Tree dfs(int v) {
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1; // Initialize parent[i] to -1

		// Mark visited vertices
		boolean[] isVisited = new boolean[vertices.size()];

		// Recursively search
		dfs(v, parent, searchOrders, isVisited);

		// Return a search tree
		return new Tree(v, parent, searchOrders);
	}

	/** Recursive method for DFS search */
	private void dfs(int v, int[] parent, List<Integer> searchOrders,
			boolean[] isVisited) {
		// Store the visited vertex
		searchOrders.add(v);
		isVisited[v] = true; // Vertex v visited

		for (int i : neighbors.get(v)) {
			if (!isVisited[i]) {
				parent[i] = v; // The parent of vertex i is v
				dfs(i, parent, searchOrders, isVisited); // Recursive search
			}
		}
	}

	/** Starting bfs search from vertex v */
	/** To be discussed in Section 27.7 */
	public Tree bfs(int v) {
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1; // Initialize parent[i] to -1

		java.util.LinkedList<Integer> queue = new java.util.LinkedList<Integer>(); // list
																					// used
																					// as
																					// a
																					// queue
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v); // Enqueue v
		isVisited[v] = true; // Mark it visited

		while (!queue.isEmpty()) {
			int u = queue.poll(); // Dequeue to u
			searchOrders.add(u); // u searched
			for (int w : neighbors.get(u)) {
				if (!isVisited[w]) {
					queue.offer(w); // Enqueue w
					parent[w] = u; // The parent of w is u
					isVisited[w] = true; // Mark it visited
				}
			}
		}

		return new Tree(v, parent, searchOrders);
	}

	/** Tree inner class inside the AbstractGraph class */
	/** To be discussed in Section 27.5 */
	public class Tree implements Serializable {
		private int root; // The root of the tree
		private int[] parent; // Store the parent of each vertex
		private List<Integer> searchOrders; // Store the search order

		/** Construct a tree with root, parent, and searchOrder */
		public Tree(int root, int[] parent, List<Integer> searchOrders) {
			this.root = root;
			this.parent = parent;
			this.searchOrders = searchOrders;
		}

		/**
		 * Construct a tree with root and parent without a particular order
		 */
		public Tree(int root, int[] parent) {
			this.root = root;
			this.parent = parent;
		}

		/** Return the root of the tree */
		public int getRoot() {
			return root;
		}

		/** Return the parent of vertex v */
		public int getParent(int v) {
			return parent[v];
		}

		/** Return an array representing search order */
		public List<Integer> getSearchOrders() {
			return searchOrders;
		}

		/** Return number of vertices found */
		public int getNumberOfVerticesFound() {
			return searchOrders.size();
		}

		/** Return the path of vertices from a vertex index to the root */
		public List<V> getPath(int index) {
			ArrayList<V> path = new ArrayList<V>();
			if (!searchOrders.contains(index)) {
				return null;
			}
			do {
				path.add(vertices.get(index));
				index = parent[index];
			} while (index != -1);

			return path;
		}

		public List<Integer> getIndexesPath(int index) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			if (!searchOrders.contains(index)) {
				return null;
			}
			do {
				path.add(index);
				index = parent[index];
			} while (index != -1);

			return path;
		}

		/** Print a path from the root to vertex v */
		public void printPath(int index) {
			List<V> path = getPath(index);
			System.out.print("A path from " + vertices.get(root) + " to "
					+ vertices.get(index) + ": ");
			for (int i = path.size() - 1; i >= 0; i--)
				System.out.print(path.get(i) + " ");
		}

		/** Print the whole tree */
		public void printTree() {
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for (int i = 0; i < parent.length; i++) {
				if (parent[i] != -1) {
					// Display an edge
					System.out.print("(" + vertices.get(parent[i]) + ", "
							+ vertices.get(i) + ") ");
				}
			}
			System.out.println();
		}

		/** returns depth of vertex index v in the tree */
		public int depth(int v) {
			int depth = 0;
			int parent = getParent(v);
			while (true) {
				if (parent != -1) {
					depth++;
					parent = getParent(parent);
				} else
					return depth;
			}

		}
	}

	/**
	 * Return a Hamiltonian path from the specified vertex object Return null if
	 * the graph does not contain a Hamiltonian path
	 */
	public List<Integer> getHamiltonianPath(V vertex) {
		return getHamiltonianPath(getIndex(vertex));
	}

	/**
	 * Return a Hamiltonian path from the specified vertex label Return null if
	 * the graph does not contain a Hamiltonian path
	 */
	public List<Integer> getHamiltonianPath(int v) {
		// A path starts from v. (i, next[i]) represents an edge in
		// the path. isVisited[i] tracks whether i is currently in the
		// path.
		int[] next = new int[getSize()];
		for (int i = 0; i < next.length; i++)
			next[i] = -1; // Indicate no subpath from i is found yet

		boolean[] isVisited = new boolean[getSize()];

		// The vertices in the Hamiltionian path are stored in result
		List<Integer> result = null;

		// To speed up search, reorder the adjacency list for each
		// vertex so that the vertices in the list are in increasing
		// order of their degrees
		for (int i = 0; i < getSize(); i++)
			reorderNeigborsBasedOnDegree(neighbors.get(i));

		if (getHamiltonianPath(v, next, isVisited)) {
			result = new ArrayList<Integer>(); // Create a list for path
			int vertex = v; // Starting from v
			while (vertex != -1) {
				result.add(vertex); // Add vertex to the result list
				vertex = next[vertex]; // Get the next vertex in the path
			}
		}

		return result; // return null if no Hamiltionian path is found
	}

	/** Reorder the adjacency list in increasing order of degrees */
	private void reorderNeigborsBasedOnDegree(List<Integer> list) {
		for (int i = list.size() - 1; i >= 1; i--) {
			// Find the maximum in the list[0..i]
			int currentMaxDegree = getDegree(list.get(0));
			int currentMaxIndex = 0;

			for (int j = 1; j <= i; j++) {
				if (currentMaxDegree < getDegree(list.get(j))) {
					currentMaxDegree = getDegree(list.get(j));
					currentMaxIndex = j;
				}
			}

			// Swap list[i] with list[currentMaxIndex] if necessary;
			if (currentMaxIndex != i) {
				int temp = list.get(currentMaxIndex);
				list.set(currentMaxIndex, list.get(i));
				list.set(i, temp);
			}
		}
	}

	/** Return true if all elements in array isVisited are true */
	private boolean allVisited(boolean[] isVisited) {
		boolean result = true;

		for (int i = 0; i < getSize(); i++)
			result = result && isVisited[i];

		return result;
	}

	/** Search for a Hamiltonian path from v */
	private boolean getHamiltonianPath(int v, int[] next, boolean[] isVisited) {
		isVisited[v] = true; // Mark vertex v visited

		if (allVisited(isVisited))
			return true; // The path now includes all vertices, thus found

		for (int i = 0; i < neighbors.get(v).size(); i++) {
			int u = neighbors.get(v).get(i);
			if (!isVisited[u] && getHamiltonianPath(u, next, isVisited)) {
				next[v] = u; // Edge (v, u) is in the path
				return true;
			}
		}

		isVisited[v] = false; // Backtrack, v is marked unvisited now
		return false; // No Hamiltonian path exists from vertex v
	}

	/**
	 * return Hamiltonian cycle. Return null if the graph contains no
	 * Hamiltonian cycle
	 * 
	 * @param vertex
	 *            is starting edge in Hamiltonian cycle
	 */
	public List<Integer> getHamiltonianCycle() {
		// To speed up search, reorder the adjacency list for each
		// vertex so that the vertices in the list are in increasing
		// order of their degrees
		for (int i = 0; i < getSize(); i++)
			reorderNeigborsBasedOnDegree(neighbors.get(i));

		for (int i = 0; i < vertices.size(); i++) {
			List<Integer> path = new ArrayList<Integer>();
			if (getHamiltonianCycle(i, path, new boolean[vertices.size()], i) != null)
				return path;
		}
		return null;
	}

	/**
	 * return Hamiltonian cycle from specified index. Returns null is such path
	 * does not exist
	 * 
	 * @param index
	 * @return List<Integer>
	 */
	public List<Integer> getHamiltonianCycleFromIndex(int index) {
		// To speed up search, reorder the adjacency list for each
		// vertex so that the vertices in the list are in increasing
		// order of their degrees
		for (int i = 0; i < getSize(); i++)
			reorderNeigborsBasedOnDegree(neighbors.get(i));

		return getHamiltonianCycle(index, new ArrayList<Integer>(),
				new boolean[vertices.size()], index);
	}

	protected List<Integer> getHamiltonianCycle(int v, List<Integer> path,
			boolean[] isVisited, int endIndex) {
		isVisited[v] = true; // Mark vertex v visited

		if (allVisited(isVisited)) {
			if (neighbors.get(v).contains(endIndex)) {
				path.add(v);
				return path;
			}
		}

		// reordering neighBors
		List<Integer> list = neighbors.get(v);
		for (int i = list.size() - 1; i >= 1; i--) {
			// Find the maximum in the list[0..i]
			int currentMaxDegree = getAvailableDegree(isVisited, list.get(0));
			int currentMaxIndex = 0;

			for (int j = 1; j <= i; j++) {
				int jAvailable = getAvailableDegree(isVisited, list.get(j));
				if (currentMaxDegree < jAvailable) {
					currentMaxDegree = jAvailable;
					currentMaxIndex = j;
				}
			}

			// Swap list[i] with list[currentMaxIndex] if necessary;
			if (currentMaxIndex != i) {
				int temp = list.get(currentMaxIndex);
				list.set(currentMaxIndex, list.get(i));
				list.set(i, temp);
			}
		}

		for (int i = 0; i < neighbors.get(v).size(); i++) {
			int u = neighbors.get(v).get(i);
			if (!isVisited[u]
					&& getHamiltonianCycle(u, path, isVisited, endIndex) != null) {
				path.add(0, v);
				return path;
			}
		}

		isVisited[v] = false; // Backtrack, v is marked unvisited now
		return null;
	}

	private int getAvailableDegree(boolean[] isVisited, int vertex) {
		int allDegree = getDegree(vertex);
		List<Integer> neib = neighbors.get(vertex);
		for (int i = 0; i < neib.size(); i++) {
			if (isVisited[neib.get(i)])
				allDegree--;
		}
		return allDegree;
	}

	public void addVertex(V vertex) {
		vertices.add(vertex);
		neighbors.add(new ArrayList<Integer>());
	}

	public void addEdge(int u, int v) {
		neighbors.get(u).add(v);
		neighbors.get(v).add(u);
	}

	// find all connected components in a graph
	public List<List<Integer>> getConnectedComponent() {
		List<List<Integer>> components = new ArrayList<List<Integer>>();
		List<Integer> left = new LinkedList<Integer>();
		for (int i = 0; i < vertices.size(); i++)
			left.add(i);
		while (!left.isEmpty()) {
			if (left.size() == 1) {
				List<Integer> component = new ArrayList<Integer>();
				component.add(left.remove(0));
				components.add(component);
				break;
			}

			Tree tree = dfs(left.get(0));
			List<Integer> treeElements = tree.getSearchOrders();
			components.add(treeElements);
			for (int element : treeElements) {
				left.remove(new Integer(element));
			}
		}
		return components;
	}

	/**
	 * find path between two vertices, if path does not exist or if parameters
	 * is incorrect method returns null
	 */
	public List<Integer> getPath(int u, int v) {
		Tree tree = bfs(v);
		if (!tree.getSearchOrders().contains(u) || u >= vertices.size()
				|| u < 0 || v >= vertices.size() || v < 0)
			return null;
		List<Integer> path = new ArrayList<Integer>();
		do {
			path.add(u);
			u = tree.getParent(u);
		} while (u != -1);
		return path;
	}

	/** determines whether there is a cycle is in a graph */
	public boolean isCyclic() {
		boolean[] visited = new boolean[getSize()];
		List<List<Integer>> components = getConnectedComponent();
		for (List<Integer> component : components) {
			if (hasCycle(component.get(0), -1, visited))
				return true;
		}
		return false;
	}

	/** recursive method for isCyclic() */
	private boolean hasCycle(int index, int parent, boolean[] visited) {
		visited[index] = true;
		List<Integer> neighbors = getNeighbors(index);
		for (int neighbor : neighbors) {
			if (neighbor == parent)
				continue;
			if (visited[neighbor])
				return true;
			if (hasCycle(neighbor, index, visited))
				return true;
		}
		return false;
	}

	/**
	 * return cycle from graph. if cycle not exist return null p.s method is
	 * wrong
	 */
	public List<Integer> getCycle() {
		List<List<Integer>> components = getConnectedComponent();

		for (List<Integer> component : components) {
			boolean cyclic = true;
			for (int element : component) {
				if (getNeighbors(element).size() != 2) {
					cyclic = false;
					break;
				}
			}
			if (cyclic) {
				return component;
			}
		}
		return null;
	}

	/** detect is graph is bipartite */
	public boolean isBipartite() {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		for (int i = 0; i < vertices.size(); i++) {
			if (!hasEdges(i, list1))
				list1.add(i);
			else if (!hasEdges(i, list2))
				list2.add(i);
			else
				return false;
		}
		return true;
	}

	/** return true if vertex n has any neighbors in a list */
	protected boolean hasEdges(int n, List<Integer> list) {
		List<Integer> neighbors = getNeighbors(n);
		for (int neighbor : neighbors) {
			for (int element : list) {
				if (neighbor == element)
					return true;
				List<Integer> elementNeighbors = getNeighbors(element);
				for (int i = 0; i < elementNeighbors.size(); i++) {
					if (elementNeighbors.get(i) == n)
						return true;
				}
			}
		}
		return false;
	}

	/** return two bipartite sets. If graph is not bipartite return null */
	public List<List<Integer>> getBipartite() {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		for (int i = 0; i < vertices.size(); i++) {
			if (!hasEdges(i, list1))
				list1.add(i);
			else if (!hasEdges(i, list2))
				list2.add(i);
			else
				return null;
		}
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		return list;
	}

	/** add a vertex to the graph and returns true if succeeded */
	public boolean add(V vertex) {
		if (vertices.contains(vertex))
			return false;
		vertices.add(vertex);
		neighbors.add(new ArrayList<Integer>());
		return true;
	}

	/** Remove vertex from the graph and return true if succeeded */
	public boolean remove(V vertex) {
		if (!vertices.contains(vertex))
			return false;
		if (vertices.size() == 1) {
			vertices.clear();
			neighbors.clear();
			return true;
		}

		int vertexIndex = vertices.indexOf(vertex);
		int lastIndex = vertices.size() - 1;

		if (vertexIndex == lastIndex) {
			vertices.remove(vertexIndex);
			neighbors.remove(lastIndex);
			for (List<Integer> v : neighbors) {
				v.remove(new Integer(vertexIndex));
			}
			return true;
		}

		vertices.set(vertexIndex, vertices.remove(lastIndex));
		neighbors.set(vertexIndex, neighbors.remove(lastIndex));
		for (List<Integer> v : neighbors) {
			v.remove(new Integer(vertexIndex));
			int indexOfLastIndex = v.indexOf(lastIndex);
			if (indexOfLastIndex != -1)
				v.set(v.indexOf(lastIndex), vertexIndex);
		}
		return true;
	}

	/** Add an edge to the graph and return true if secceded */
	public boolean add(Edge edge) {
		if (vertices.size() == 0 || edge.u < 0 || edge.v < 0
				|| edge.u >= vertices.size() || edge.v >= vertices.size()
				|| neighbors.get(edge.u).contains(edge.v))
			return false;
		neighbors.get(edge.u).add(edge.v);
		return true;
	}

	/** Remove an edge from the graph and return true if succeeded */
	public boolean remove(Edge edge) {
		if (vertices.size() == 0 || edge.u < 0 || edge.v < 0
				|| edge.u >= vertices.size() || edge.v >= vertices.size()
				|| !neighbors.get(edge.u).contains(edge.v))
			return false;
		neighbors.get(edge.u).remove(new Integer(edge.v));
		return true;
	}

	/**
	 * Given an undirected Graph G = (V, E) and an integer k, find an induced
	 * subgraph H of G of maximum size such that all vertices of H have degree
	 * >= k, if no such induced graph exists returns null
	 */
	public static <E> Graph<E> maxInducedSubgraph(Graph<E> edge, int k) {
		if (edge.getSize() < k)
			return null;

		List<Edge> edges = new ArrayList<Edge>();
		List<E> vertices = edge.getVertices();

		// getting edges
		for (int i = 0; i < edge.getSize(); i++) {
			for (int j = 0; j < edge.getNeighbors(i).size(); j++) {
				edges.add(new Edge(i, edge.getNeighbors(i).get(j)));
			}
		}

		AbstractGraph<E> graph = new UnweightedGraph<E>(edges, vertices);

		boolean changed = true;
		while (changed) {
			changed = false;
			for (int i = 0; i < graph.getSize(); i++) {
				if (graph.getNeighbors(i).size() < k) {
					graph.remove(graph.getVertex(i));
					i--;
					changed = true;
				}
			}
		}
		if (graph.getSize() == 0)
			return null;
		return graph;
	}

	/** determines if graph is connected */
	public boolean isConnected() {
		if (getSize() < 2)
			return true;
		Tree tree = dfs(0);
		return (tree.getNumberOfVerticesFound() == getSize());
	}
}

class UnweightedGraph<V> extends AbstractGraph<V> {
	/** Construct a graph from edges and vertices stored in arrays */
	public UnweightedGraph(int[][] edges, V[] vertices) {
		super(edges, vertices);
	}

	/** Construct a graph from edges and vertices stored in List */
	public UnweightedGraph(List<Edge> edges, List<V> vertices) {
		super(edges, vertices);
	}

	/** Construct a graph for integer vertices 0, 1, 2 and edge list */
	public UnweightedGraph(List<Edge> edges, int numberOfVertices) {
		super(edges, numberOfVertices);
	}

	/** Construct a graph from integer vertices 0, 1, and edge array */
	public UnweightedGraph(int[][] edges, int numberOfVertices) {
		super(edges, numberOfVertices);
	}
}

interface Graph<V> extends Serializable {
	/** Return the number of vertices in the graph */
	public int getSize();

	/** Return the vertices in the graph */
	public java.util.List<V> getVertices();

	/** Return the object for the specified vertex index */
	public V getVertex(int index);

	/** Return the index for the specified vertex object */
	public int getIndex(V v);

	/** Return the neighbors of vertex with the specified index */
	public java.util.List<Integer> getNeighbors(int index);

	/** Return the degree for a specified vertex */
	public int getDegree(int v);

	/** Return the adjacency matrix */
	public int[][] getAdjacencyMatrix();

	/** Print the adjacency matrix */
	public void printAdjacencyMatrix();

	/** Print the edges */
	public void printEdges();

	/** Obtain a depth-first search tree */
	public AbstractGraph<V>.Tree dfs(int v);

	/** Obtain a breadth-first search tree */
	public AbstractGraph<V>.Tree bfs(int v);

	/**
	 * Return a Hamiltonian path from the specified vertex Return null if the
	 * graph does not contain a Hamiltonian path
	 */
	public java.util.List<Integer> getHamiltonianPath(V vertex);

	/**
	 * Return a Hamiltonian path from the specified vertex label Return null if
	 * the graph does not contain a Hamiltonian path
	 */
	public java.util.List<Integer> getHamiltonianPath(int inexe);
}

class WeightedEdge extends AbstractGraph.Edge implements
		Comparable<WeightedEdge> {
	public int weight; // The weight on edge (u, v)

	/** Create a weighted edge on (u, v) */
	public WeightedEdge(int u, int v, int weight) {
		super(u, v);
		this.weight = weight;
	}

	/** Compare two edges on weights */
	public int compareTo(WeightedEdge edge) {
		if (weight > edge.weight)
			return 1;
		else if (weight == edge.weight) {
			return 0;
		} else {
			return -1;
		}
	}

	public boolean equals(Object o) {
		WeightedEdge edge = (WeightedEdge) o;
		return (edge.u == u && edge.v == v);
	}

	public int hashCode() {
		return weight;
	}
}
