package mypackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraph<V> implements Graph<V> {
	private static final long serialVersionUID = 1L;
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
		private static final long serialVersionUID = 1L;
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

	
		
	}






	public void addVertex(V vertex) {
		vertices.add(vertex);
		neighbors.add(new ArrayList<Integer>());
	}

	public void addEdge(int u, int v) {
		neighbors.get(u).add(v);
		neighbors.get(v).add(u);
	}
}
