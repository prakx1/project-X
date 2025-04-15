/**
 * Implementation of Dijkstra's Algorithm for finding the shortest path in a weighted graph.
 * 
 * Dijkstra's algorithm is a greedy algorithm that finds the shortest path from a source vertex
 * to all other vertices in a weighted graph with non-negative edge weights.
 * 
 * Time Complexity:
 * - With binary heap priority queue: O((V + E) log V) where V is the number of vertices and E is the number of edges
 * - With Fibonacci heap (not implemented here): O(E + V log V)
 * 
 * Space Complexity: O(V) for the distance and previous maps, and priority queue
 */
import java.util.*;

public class DijkstraAlgorithm {
    
    /**
     * Represents a weighted graph using an adjacency list.
     */
    public static class Graph {
        private final Map<Integer, List<Edge>> adjacencyList;
        private final int numVertices;
        private final boolean isDirected;
        
        /**
         * Inner class to represent a weighted edge.
         */
        public static class Edge {
            int destination;
            int weight;
            
            public Edge(int destination, int weight) {
                this.destination = destination;
                this.weight = weight;
            }
        }
        
        /**
         * Constructs a graph with the specified number of vertices.
         * 
         * @param numVertices the number of vertices
         * @param isDirected whether the graph is directed
         */
        public Graph(int numVertices, boolean isDirected) {
            this.numVertices = numVertices;
            this.isDirected = isDirected;
            adjacencyList = new HashMap<>();
            
            // Initialize adjacency list for all vertices
            for (int i = 0; i < numVertices; i++) {
                adjacencyList.put(i, new ArrayList<>());
            }
        }
        
        /**
         * Adds an edge to the graph.
         * 
         * @param source the source vertex
         * @param destination the destination vertex
         * @param weight the weight of the edge
         */
        public void addEdge(int source, int destination, int weight) {
            // Validate vertices
            if (source < 0 || source >= numVertices || destination < 0 || destination >= numVertices) {
                throw new IllegalArgumentException("Invalid vertex");
            }
            
            // Add edge from source to destination
            adjacencyList.get(source).add(new Edge(destination, weight));
            
            // If undirected, add edge from destination to source as well
            if (!isDirected) {
                adjacencyList.get(destination).add(new Edge(source, weight));
            }
        }
        
        /**
         * Gets the adjacent edges for a vertex.
         * 
         * @param vertex the vertex to get adjacent edges for
         * @return a list of adjacent edges
         */
        public List<Edge> getAdjacentEdges(int vertex) {
            // Validate vertex
            if (vertex < 0 || vertex >= numVertices) {
                throw new IllegalArgumentException("Invalid vertex");
            }
            
            return adjacencyList.get(vertex);
        }
        
        /**
         * Gets the number of vertices in the graph.
         * 
         * @return the number of vertices
         */
        public int getNumVertices() {
            return numVertices;
        }
    }
    
    /**
     * Entry class for the priority queue in Dijkstra's algorithm.
     */
    private static class Entry implements Comparable<Entry> {
        int vertex;
        int distance;
        
        public Entry(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
        
        @Override
        public int compareTo(Entry other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
    
    /**
     * Finds the shortest path from a source vertex to all other vertices using Dijkstra's algorithm.
     * 
     * @param graph the graph to find shortest paths in
     * @param source the source vertex
     * @return a map from vertex to its shortest distance from the source
     */
    public static Map<Integer, Integer> findShortestPaths(Graph graph, int source) {
        // Validate source vertex
        if (source < 0 || source >= graph.getNumVertices()) {
            throw new IllegalArgumentException("Invalid source vertex");
        }
        
        // Distance map: vertex -> shortest distance from source
        Map<Integer, Integer> distances = new HashMap<>();
        
        // Priority queue to get the vertex with the smallest distance
        PriorityQueue<Entry> pq = new PriorityQueue<>();
        
        // Set to keep track of vertices that have been processed
        Set<Integer> processed = new HashSet<>();
        
        // Initialize distances for all vertices to infinity except the source
        for (int i = 0; i < graph.getNumVertices(); i++) {
            distances.put(i, i == source ? 0 : Integer.MAX_VALUE);
        }
        
        // Add source to priority queue
        pq.add(new Entry(source, 0));
        
        // Process vertices
        while (!pq.isEmpty()) {
            // Get vertex with minimum distance
            Entry current = pq.poll();
            int vertex = current.vertex;
            
            // Skip if already processed or distance is infinity
            if (processed.contains(vertex) || distances.get(vertex) == Integer.MAX_VALUE) {
                continue;
            }
            
            // Mark as processed
            processed.add(vertex);
            
            // Process all adjacent edges
            for (Graph.Edge edge : graph.getAdjacentEdges(vertex)) {
                int neighbor = edge.destination;
                
                // Skip if already processed
                if (processed.contains(neighbor)) {
                    continue;
                }
                
                // Calculate new distance
                int newDistance = distances.get(vertex) + edge.weight;
                
                // Update distance if it's shorter
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    pq.add(new Entry(neighbor, newDistance));
                }
            }
        }
        
        return distances;
    }
    
    /**
     * Finds the shortest path from a source vertex to a specific destination vertex using Dijkstra's algorithm.
     * 
     * @param graph the graph to find shortest path in
     * @param source the source vertex
     * @param destination the destination vertex
     * @return a list of vertices representing the shortest path, or an empty list if no path exists
     */
    public static List<Integer> findShortestPath(Graph graph, int source, int destination) {
        // Validate vertices
        if (source < 0 || source >= graph.getNumVertices() || destination < 0 || destination >= graph.getNumVertices()) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        
        // Distance map: vertex -> shortest distance from source
        Map<Integer, Integer> distances = new HashMap<>();
        
        // Previous map: vertex -> previous vertex in the shortest path
        Map<Integer, Integer> previous = new HashMap<>();
        
        // Priority queue to get the vertex with the smallest distance
        PriorityQueue<Entry> pq = new PriorityQueue<>();
        
        // Set to keep track of vertices that have been processed
        Set<Integer> processed = new HashSet<>();
        
        // Initialize distances for all vertices to infinity except the source
        for (int i = 0; i < graph.getNumVertices(); i++) {
            distances.put(i, i == source ? 0 : Integer.MAX_VALUE);
            previous.put(i, -1); // -1 indicates no previous vertex
        }
        
        // Add source to priority queue
        pq.add(new Entry(source, 0));
        
        // Process vertices
        while (!pq.isEmpty()) {
            // Get vertex with minimum distance
            Entry current = pq.poll();
            int vertex = current.vertex;
            
            // If destination reached, break
            if (vertex == destination) {
                break;
            }
            
            // Skip if already processed or distance is infinity
            if (processed.contains(vertex) || distances.get(vertex) == Integer.MAX_VALUE) {
                continue;
            }
            
            // Mark as processed
            processed.add(vertex);
            
            // Process all adjacent edges
            for (Graph.Edge edge : graph.getAdjacentEdges(vertex)) {
                int neighbor = edge.destination;
                
                // Skip if already processed
                if (processed.contains(neighbor)) {
                    continue;
                }
                
                // Calculate new distance
                int newDistance = distances.get(vertex) + edge.weight;
                
                // Update distance if it's shorter
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, vertex);
                    pq.add(new Entry(neighbor, newDistance));
                }
            }
        }
        
        // Reconstruct the path
        List<Integer> path = new ArrayList<>();
        
        // If no path exists
        if (distances.get(destination) == Integer.MAX_VALUE) {
            return path;
        }
        
        // Trace back from destination to source
        for (int vertex = destination; vertex != -1; vertex = previous.get(vertex)) {
            path.add(0, vertex);
        }
        
        return path;
    }
    
    /**
     * Main method with examples on how to use Dijkstra's Algorithm
     */
    public static void main(String[] args) {
        // Example usage
        Graph graph = new Graph(6, true); // 6 vertices, directed graph
        
        // Add edges
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 7);
        graph.addEdge(2, 4, 3);
        graph.addEdge(3, 5, 1);
        graph.addEdge(4, 3, 2);
        graph.addEdge(4, 5, 5);
        
        // Find shortest paths from vertex 0 to all other vertices
        Map<Integer, Integer> distances = findShortestPaths(graph, 0);
        
        System.out.println("Shortest distances from vertex 0:");
        for (int i = 0; i < graph.getNumVertices(); i++) {
            System.out.println("To vertex " + i + ": " + distances.get(i));
        }
        
        // Find shortest path from vertex 0 to vertex 5
        List<Integer> path = findShortestPath(graph, 0, 5);
        
        System.out.println("\nShortest path from vertex 0 to vertex 5:");
        if (path.isEmpty()) {
            System.out.println("No path exists");
        } else {
            System.out.print("Path: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("\nTotal distance: " + distances.get(5));
        }
        
        // Example of no path
        Graph disconnectedGraph = new Graph(4, true);
        disconnectedGraph.addEdge(0, 1, 1);
        disconnectedGraph.addEdge(2, 3, 1);
        
        List<Integer> noPath = findShortestPath(disconnectedGraph, 0, 3);
        
        System.out.println("\nShowing no path in a disconnected graph:");
        System.out.println("Path from 0 to 3 exists: " + !noPath.isEmpty());
        
        // Example with undirected graph
        System.out.println("\nExample with undirected graph:");
        Graph undirectedGraph = new Graph(5, false);
        
        undirectedGraph.addEdge(0, 1, 4);
        undirectedGraph.addEdge(0, 2, 3);
        undirectedGraph.addEdge(1, 2, 1);
        undirectedGraph.addEdge(1, 3, 2);
        undirectedGraph.addEdge(2, 3, 4);
        undirectedGraph.addEdge(3, 4, 2);
        
        List<Integer> undirectedPath = findShortestPath(undirectedGraph, 0, 4);
        
        System.out.print("Shortest path from 0 to 4: ");
        for (int i = 0; i < undirectedPath.size(); i++) {
            System.out.print(undirectedPath.get(i));
            if (i < undirectedPath.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}
