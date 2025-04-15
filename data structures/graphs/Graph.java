/**
 * Implementation of a Graph data structure using adjacency list representation.
 * This implementation supports both directed and undirected graphs.
 * 
 * Time Complexity:
 * - Add vertex: O(1)
 * - Add edge: O(1)
 * - Remove vertex: O(V + E) where V is the number of vertices and E is the number of edges
 * - Remove edge: O(E) where E is the number of edges
 * - Check if vertex exists: O(1) due to HashMap
 * - Check if edge exists: O(E) where E is the number of edges
 * 
 * Space Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 */
import java.util.*;

public class Graph<T> {
    
    // Adjacency list representation of the graph
    private Map<T, List<Edge<T>>> adjacencyList;
    private boolean isDirected;
    
    /**
     * Inner class representing an edge between two vertices
     */
    public static class Edge<T> {
        private T destination;
        private int weight;
        
        public Edge(T destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
        
        public Edge(T destination) {
            this(destination, 1); // Default weight is 1
        }
        
        public T getDestination() {
            return destination;
        }
        
        public int getWeight() {
            return weight;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Edge<?> edge = (Edge<?>) obj;
            return Objects.equals(destination, edge.destination);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(destination);
        }
    }
    
    /**
     * Constructor for creating a graph
     * @param isDirected true for directed graph, false for undirected
     */
    public Graph(boolean isDirected) {
        this.adjacencyList = new HashMap<>();
        this.isDirected = isDirected;
    }
    
    /**
     * Constructor for creating an undirected graph by default
     */
    public Graph() {
        this(false); // Default is undirected
    }
    
    /**
     * Add a vertex to the graph
     * @param vertex vertex to add
     * @return true if vertex was added, false if it already exists
     */
    public boolean addVertex(T vertex) {
        if (hasVertex(vertex)) {
            return false; // Vertex already exists
        }
        
        adjacencyList.put(vertex, new ArrayList<>());
        return true;
    }
    
    /**
     * Add an edge between two vertices
     * @param source source vertex
     * @param destination destination vertex
     * @param weight weight of the edge
     * @return true if edge was added, false if vertices don't exist or edge already exists
     */
    public boolean addEdge(T source, T destination, int weight) {
        // Check if both vertices exist
        if (!hasVertex(source) || !hasVertex(destination)) {
            return false;
        }
        
        // Check if edge already exists
        if (hasEdge(source, destination)) {
            return false;
        }
        
        // Add edge from source to destination
        adjacencyList.get(source).add(new Edge<>(destination, weight));
        
        // If undirected, add edge from destination to source as well
        if (!isDirected && !source.equals(destination)) {
            adjacencyList.get(destination).add(new Edge<>(source, weight));
        }
        
        return true;
    }
    
    /**
     * Add an edge between two vertices with default weight 1
     * @param source source vertex
     * @param destination destination vertex
     * @return true if edge was added, false if vertices don't exist or edge already exists
     */
    public boolean addEdge(T source, T destination) {
        return addEdge(source, destination, 1);
    }
    
    /**
     * Remove a vertex and all its connected edges
     * @param vertex vertex to remove
     * @return true if vertex was removed, false if it doesn't exist
     */
    public boolean removeVertex(T vertex) {
        if (!hasVertex(vertex)) {
            return false;
        }
        
        // Remove all edges pointing to this vertex (for undirected or directed graph)
        for (T v : adjacencyList.keySet()) {
            List<Edge<T>> edges = adjacencyList.get(v);
            edges.removeIf(edge -> edge.getDestination().equals(vertex));
        }
        
        // Remove the vertex and its edges
        adjacencyList.remove(vertex);
        return true;
    }
    
    /**
     * Remove an edge between two vertices
     * @param source source vertex
     * @param destination destination vertex
     * @return true if edge was removed, false if vertices or edge don't exist
     */
    public boolean removeEdge(T source, T destination) {
        // Check if both vertices exist
        if (!hasVertex(source) || !hasVertex(destination)) {
            return false;
        }
        
        // Check if edge exists
        if (!hasEdge(source, destination)) {
            return false;
        }
        
        // Remove edge from source to destination
        List<Edge<T>> sourceEdges = adjacencyList.get(source);
        sourceEdges.removeIf(edge -> edge.getDestination().equals(destination));
        
        // If undirected, remove edge from destination to source as well
        if (!isDirected) {
            List<Edge<T>> destEdges = adjacencyList.get(destination);
            destEdges.removeIf(edge -> edge.getDestination().equals(source));
        }
        
        return true;
    }
    
    /**
     * Check if vertex exists in the graph
     * @param vertex vertex to check
     * @return true if vertex exists, false otherwise
     */
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }
    
    /**
     * Check if edge exists between two vertices
     * @param source source vertex
     * @param destination destination vertex
     * @return true if edge exists, false otherwise
     */
    public boolean hasEdge(T source, T destination) {
        // Check if both vertices exist
        if (!hasVertex(source) || !hasVertex(destination)) {
            return false;
        }
        
        // Check if there's an edge from source to destination
        List<Edge<T>> edges = adjacencyList.get(source);
        for (Edge<T> edge : edges) {
            if (edge.getDestination().equals(destination)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Get weight of edge between two vertices
     * @param source source vertex
     * @param destination destination vertex
     * @return weight of edge if it exists, -1 otherwise
     */
    public int getEdgeWeight(T source, T destination) {
        // Check if both vertices exist
        if (!hasVertex(source) || !hasVertex(destination)) {
            return -1;
        }
        
        // Find the edge and return its weight
        List<Edge<T>> edges = adjacencyList.get(source);
        for (Edge<T> edge : edges) {
            if (edge.getDestination().equals(destination)) {
                return edge.getWeight();
            }
        }
        
        return -1; // Edge doesn't exist
    }
    
    /**
     * Get all vertices in the graph
     * @return set of vertices
     */
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }
    
    /**
     * Get all neighbors of a vertex
     * @param vertex vertex to get neighbors for
     * @return list of neighbor vertices, or empty list if vertex doesn't exist
     */
    public List<T> getNeighbors(T vertex) {
        if (!hasVertex(vertex)) {
            return new ArrayList<>();
        }
        
        List<T> neighbors = new ArrayList<>();
        List<Edge<T>> edges = adjacencyList.get(vertex);
        
        for (Edge<T> edge : edges) {
            neighbors.add(edge.getDestination());
        }
        
        return neighbors;
    }
    
    /**
     * Get all edges for a vertex
     * @param vertex vertex to get edges for
     * @return list of edges, or empty list if vertex doesn't exist
     */
    public List<Edge<T>> getEdges(T vertex) {
        if (!hasVertex(vertex)) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(adjacencyList.get(vertex));
    }
    
    /**
     * Get the number of vertices in the graph
     * @return number of vertices
     */
    public int getVertexCount() {
        return adjacencyList.size();
    }
    
    /**
     * Get the number of edges in the graph
     * @return number of edges
     */
    public int getEdgeCount() {
        int count = 0;
        for (List<Edge<T>> edges : adjacencyList.values()) {
            count += edges.size();
        }
        
        // If undirected, each edge is counted twice
        return isDirected ? count : count / 2;
    }
    
    /**
     * Check if the graph is directed
     * @return true if directed, false if undirected
     */
    public boolean isDirected() {
        return isDirected;
    }
    
    /**
     * Perform Breadth-First Search (BFS) traversal starting from a vertex
     * @param start starting vertex
     * @return list of vertices in BFS order
     */
    public List<T> bfs(T start) {
        if (!hasVertex(start)) {
            return new ArrayList<>();
        }
        
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        
        // Start BFS from the given vertex
        queue.add(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            T current = queue.poll();
            result.add(current);
            
            // Visit all unvisited neighbors
            for (T neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Perform Depth-First Search (DFS) traversal starting from a vertex
     * @param start starting vertex
     * @return list of vertices in DFS order
     */
    public List<T> dfs(T start) {
        if (!hasVertex(start)) {
            return new ArrayList<>();
        }
        
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        
        // Start DFS from the given vertex
        dfsRecursive(start, visited, result);
        
        return result;
    }
    
    /**
     * Helper method for recursive DFS traversal
     * @param current current vertex
     * @param visited set of visited vertices
     * @param result list to store traversal result
     */
    private void dfsRecursive(T current, Set<T> visited, List<T> result) {
        // Mark current vertex as visited and add to result
        visited.add(current);
        result.add(current);
        
        // Visit all unvisited neighbors
        for (T neighbor : getNeighbors(current)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }
    
    /**
     * Check if the graph has a cycle
     * @return true if cycle exists, false otherwise
     */
    public boolean hasCycle() {
        Set<T> visited = new HashSet<>();
        Set<T> recursionStack = new HashSet<>();
        
        // Check for cycle starting from each unvisited vertex
        for (T vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                if (isDirected) {
                    if (hasCycleDirected(vertex, visited, recursionStack)) {
                        return true;
                    }
                } else {
                    if (hasCycleUndirected(vertex, visited, null)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Helper method to check for cycle in directed graph
     */
    private boolean hasCycleDirected(T current, Set<T> visited, Set<T> recursionStack) {
        // Mark current vertex as visited and add to recursion stack
        visited.add(current);
        recursionStack.add(current);
        
        // Check all neighbors
        for (T neighbor : getNeighbors(current)) {
            // If neighbor is not visited, check if there's a cycle starting from it
            if (!visited.contains(neighbor)) {
                if (hasCycleDirected(neighbor, visited, recursionStack)) {
                    return true;
                }
            } 
            // If neighbor is in recursion stack, we found a cycle
            else if (recursionStack.contains(neighbor)) {
                return true;
            }
        }
        
        // Remove current vertex from recursion stack
        recursionStack.remove(current);
        return false;
    }
    
    /**
     * Helper method to check for cycle in undirected graph
     */
    private boolean hasCycleUndirected(T current, Set<T> visited, T parent) {
        // Mark current vertex as visited
        visited.add(current);
        
        // Check all neighbors
        for (T neighbor : getNeighbors(current)) {
            // If neighbor is not visited, check if there's a cycle starting from it
            if (!visited.contains(neighbor)) {
                if (hasCycleUndirected(neighbor, visited, current)) {
                    return true;
                }
            } 
            // If neighbor is visited and not the parent, we found a cycle
            else if (!neighbor.equals(parent)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Find shortest path between two vertices using Dijkstra's algorithm
     * @param start starting vertex
     * @param end ending vertex
     * @return list of vertices representing the shortest path, or empty list if no path exists
     */
    public List<T> shortestPath(T start, T end) {
        if (!hasVertex(start) || !hasVertex(end)) {
            return new ArrayList<>();
        }
        
        // Map to store the shortest distance from start to each vertex
        Map<T, Integer> distances = new HashMap<>();
        // Map to store the previous vertex in the shortest path
        Map<T, T> previousVertices = new HashMap<>();
        // Set of unvisited vertices
        Set<T> unvisited = new HashSet<>();
        
        // Initialize distances and unvisited set
        for (T vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
            unvisited.add(vertex);
        }
        distances.put(start, 0);
        
        // Dijkstra's algorithm
        while (!unvisited.isEmpty()) {
            // Find the unvisited vertex with the smallest distance
            T current = null;
            int smallestDistance = Integer.MAX_VALUE;
            
            for (T vertex : unvisited) {
                int distance = distances.get(vertex);
                if (distance < smallestDistance) {
                    smallestDistance = distance;
                    current = vertex;
                }
            }
            
            // If smallest distance is infinity, there's no path to remaining vertices
            if (current == null) {
                break;
            }
            
            // If we've reached the end vertex, we're done
            if (current.equals(end)) {
                break;
            }
            
            // Remove current vertex from unvisited set
            unvisited.remove(current);
            
            // Update distances to neighbors
            for (Edge<T> edge : getEdges(current)) {
                T neighbor = edge.getDestination();
                if (unvisited.contains(neighbor)) {
                    int newDistance = distances.get(current) + edge.getWeight();
                    
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousVertices.put(neighbor, current);
                    }
                }
            }
        }
        
        // Reconstruct the path
        List<T> path = new ArrayList<>();
        if (distances.get(end) == Integer.MAX_VALUE) {
            return path; // No path exists
        }
        
        T current = end;
        while (current != null) {
            path.add(0, current); // Add to beginning of list
            current = previousVertices.get(current);
        }
        
        return path;
    }
    
    /**
     * Perform topological sort on a directed acyclic graph (DAG)
     * @return list of vertices in topological order, or empty list if graph is not a DAG
     */
    public List<T> topologicalSort() {
        if (!isDirected || hasCycle()) {
            return new ArrayList<>(); // Topological sort only works on DAGs
        }
        
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Stack<T> stack = new Stack<>();
        
        // Visit all vertices
        for (T vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }
        
        // Pop everything from the stack to get the topological sort
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result;
    }
    
    /**
     * Helper method for topological sort
     */
    private void topologicalSortUtil(T current, Set<T> visited, Stack<T> stack) {
        // Mark current vertex as visited
        visited.add(current);
        
        // Visit all neighbors
        for (T neighbor : getNeighbors(current)) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }
        
        // Push current vertex to stack
        stack.push(current);
    }
    
    /**
     * String representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isDirected ? "Directed" : "Undirected").append(" Graph:\n");
        
        for (Map.Entry<T, List<Edge<T>>> entry : adjacencyList.entrySet()) {
            T vertex = entry.getKey();
            List<Edge<T>> edges = entry.getValue();
            
            sb.append(vertex).append(" -> ");
            
            for (int i = 0; i < edges.size(); i++) {
                Edge<T> edge = edges.get(i);
                sb.append(edge.getDestination());
                if (edge.getWeight() != 1) {
                    sb.append("(").append(edge.getWeight()).append(")");
                }
                
                if (i < edges.size() - 1) {
                    sb.append(", ");
                }
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Main method with examples on how to use the Graph
     */
    public static void main(String[] args) {
        // Example of undirected graph
        Graph<String> undirectedGraph = new Graph<>();
        
        // Add vertices
        undirectedGraph.addVertex("A");
        undirectedGraph.addVertex("B");
        undirectedGraph.addVertex("C");
        undirectedGraph.addVertex("D");
        undirectedGraph.addVertex("E");
        
        // Add edges
        undirectedGraph.addEdge("A", "B");
        undirectedGraph.addEdge("A", "C");
        undirectedGraph.addEdge("B", "D");
        undirectedGraph.addEdge("C", "D");
        undirectedGraph.addEdge("C", "E");
        undirectedGraph.addEdge("D", "E");
        
        System.out.println("Undirected Graph Example:");
        System.out.println(undirectedGraph);
        
        // BFS and DFS traversals
        System.out.println("BFS from A: " + undirectedGraph.bfs("A"));
        System.out.println("DFS from A: " + undirectedGraph.dfs("A"));
        
        // Check for cycle
        System.out.println("Has cycle: " + undirectedGraph.hasCycle());
        
        // Find shortest path
        System.out.println("Shortest path from A to E: " + undirectedGraph.shortestPath("A", "E"));
        
        // Example of directed graph
        Graph<String> directedGraph = new Graph<>(true);
        
        // Add vertices
        directedGraph.addVertex("A");
        directedGraph.addVertex("B");
        directedGraph.addVertex("C");
        directedGraph.addVertex("D");
        directedGraph.addVertex("E");
        
        // Add edges
        directedGraph.addEdge("A", "B");
        directedGraph.addEdge("A", "C");
        directedGraph.addEdge("B", "D");
        directedGraph.addEdge("C", "D");
        directedGraph.addEdge("D", "E");
        
        System.out.println("\nDirected Graph Example:");
        System.out.println(directedGraph);
        
        // Topological sort
        System.out.println("Topological Sort: " + directedGraph.topologicalSort());
        
        // Example of weighted graph
        Graph<String> weightedGraph = new Graph<>(true);
        
        // Add vertices
        weightedGraph.addVertex("A");
        weightedGraph.addVertex("B");
        weightedGraph.addVertex("C");
        weightedGraph.addVertex("D");
        weightedGraph.addVertex("E");
        
        // Add weighted edges
        weightedGraph.addEdge("A", "B", 2);
        weightedGraph.addEdge("A", "C", 3);
        weightedGraph.addEdge("B", "D", 1);
        weightedGraph.addEdge("C", "D", 5);
        weightedGraph.addEdge("D", "E", 2);
        weightedGraph.addEdge("C", "E", 7);
        
        System.out.println("\nWeighted Directed Graph Example:");
        System.out.println(weightedGraph);
        
        // Find shortest path in weighted graph
        System.out.println("Shortest path from A to E: " + weightedGraph.shortestPath("A", "E"));
    }
}
