import java.util.*;

/**
 * Implementation of Topological Sort algorithm.
 * 
 * A topological sort of a directed graph is a linear ordering of its vertices such that
 * for every directed edge (u, v), vertex u comes before vertex v in the ordering.
 * Topological Sort is only possible for Directed Acyclic Graphs (DAG).
 * 
 * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 * Space Complexity: O(V) for the visited array and stack
 * 
 * Common Interview Uses:
 * - Scheduling tasks with dependencies
 * - Course prerequisite problems
 * - Build systems and dependency resolution
 * - Package installation
 */
public class TopologicalSort {
    
    private int V; // Number of vertices
    private List<List<Integer>> adj; // Adjacency list representation
    
    /**
     * Constructor
     * @param v number of vertices
     */
    public TopologicalSort(int v) {
        this.V = v;
        adj = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }
    }
    
    /**
     * Add a directed edge from vertex u to vertex v
     * @param u source vertex
     * @param v destination vertex
     */
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }
    
    /**
     * Performs topological sort using Depth-First Search
     * @return array containing the topologically sorted vertices, or null if graph has a cycle
     */
    public int[] topologicalSort() {
        // Create a boolean array to track visited vertices
        boolean[] visited = new boolean[V];
        // Create a boolean array to track vertices in current recursion stack
        boolean[] recStack = new boolean[V];
        // Create a stack to store the result
        Stack<Integer> stack = new Stack<>();
        
        // Call the recursive helper for all unvisited vertices
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (topologicalSortUtil(i, visited, recStack, stack)) {
                    // Cycle detected
                    return null;
                }
            }
        }
        
        // Pop all vertices from stack and return result
        int[] result = new int[V];
        int index = 0;
        while (!stack.isEmpty()) {
            result[index++] = stack.pop();
        }
        
        return result;
    }
    
    /**
     * Recursive utility function for topological sort
     * @param v current vertex
     * @param visited array to track visited vertices
     * @param recStack array to track vertices in current recursion
     * @param stack stack to store the result
     * @return true if a cycle is detected, false otherwise
     */
    private boolean topologicalSortUtil(int v, boolean[] visited, boolean[] recStack, Stack<Integer> stack) {
        // Mark the current vertex as visited and add to recursion stack
        visited[v] = true;
        recStack[v] = true;
        
        // Process all adjacent vertices
        for (Integer neighbor : adj.get(v)) {
            // If not visited, recursively process
            if (!visited[neighbor]) {
                if (topologicalSortUtil(neighbor, visited, recStack, stack)) {
                    return true; // Cycle detected
                }
            } 
            // If already in recursion stack, we found a cycle
            else if (recStack[neighbor]) {
                return true;
            }
        }
        
        // Remove vertex from recursion stack
        recStack[v] = false;
        
        // Add current vertex to stack (only after all dependencies are added)
        stack.push(v);
        
        return false;
    }
    
    /**
     * Alternative implementation using Kahn's algorithm (using indegree)
     * @return array containing the topologically sorted vertices, or null if graph has a cycle
     */
    public int[] topologicalSortKahn() {
        // Calculate indegree for each vertex
        int[] indegree = new int[V];
        for (int i = 0; i < V; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }
        
        // Create a queue and enqueue all vertices with indegree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }
        
        // Initialize count of visited vertices
        int count = 0;
        
        // Create a list to store the result
        int[] result = new int[V];
        int index = 0;
        
        // Process all vertices with indegree 0 first
        while (!queue.isEmpty()) {
            // Extract a vertex from queue
            int u = queue.poll();
            result[index++] = u;
            count++;
            
            // Reduce indegree of adjacent vertices
            for (int neighbor : adj.get(u)) {
                indegree[neighbor]--;
                
                // If indegree becomes 0, add to queue
                if (indegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        // Check if there was a cycle
        if (count != V) {
            return null; // Graph has a cycle
        }
        
        return result;
    }
    
    /**
     * Utility method to print the topological sort
     * @param order the topologically sorted order of vertices
     */
    public static void printOrder(int[] order) {
        if (order == null) {
            System.out.println("Graph contains a cycle, topological sort not possible");
            return;
        }
        
        System.out.print("Topological Sort: ");
        for (int vertex : order) {
            System.out.print(vertex + " ");
        }
        System.out.println();
    }
    
    /**
     * Example usage of Topological Sort
     */
    public static void main(String[] args) {
        // Example 1: Valid DAG
        /*
         * Graph:
         *   5 → 0 ← 4
         *   ↓   ↑   ↑
         *   2 → 3 → 1
         */
        TopologicalSort g1 = new TopologicalSort(6);
        g1.addEdge(5, 2);
        g1.addEdge(5, 0);
        g1.addEdge(4, 0);
        g1.addEdge(4, 1);
        g1.addEdge(2, 3);
        g1.addEdge(3, 1);
        g1.addEdge(3, 0);
        
        System.out.println("Example 1 (DFS-based):");
        int[] order1 = g1.topologicalSort();
        printOrder(order1);
        
        System.out.println("Example 1 (Kahn's algorithm):");
        int[] order1Kahn = g1.topologicalSortKahn();
        printOrder(order1Kahn);
        
        // Example 2: Graph with a cycle
        /*
         * Graph with cycle:
         *   0 → 1 → 2
         *   ↑     ↙
         *   └── 3 ←┘
         */
        TopologicalSort g2 = new TopologicalSort(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(3, 0); // Creates a cycle
        
        System.out.println("\nExample 2 (Graph with cycle, DFS-based):");
        int[] order2 = g2.topologicalSort();
        printOrder(order2);
        
        System.out.println("Example 2 (Graph with cycle, Kahn's algorithm):");
        int[] order2Kahn = g2.topologicalSortKahn();
        printOrder(order2Kahn);
        
        // Example 3: Course Schedule Problem
        /*
         * Courses: 0, 1, 2, 3, 4
         * Prerequisites:
         * - To take course 1, you need course 0
         * - To take course 2, you need courses 0 and 1
         * - To take course 3, you need course 1
         * - To take course 4, you need courses 2 and 3
         */
        TopologicalSort courseSchedule = new TopologicalSort(5);
        courseSchedule.addEdge(0, 1);
        courseSchedule.addEdge(0, 2);
        courseSchedule.addEdge(1, 2);
        courseSchedule.addEdge(1, 3);
        courseSchedule.addEdge(2, 4);
        courseSchedule.addEdge(3, 4);
        
        System.out.println("\nCourse Schedule Example:");
        int[] courseOrder = courseSchedule.topologicalSort();
        System.out.print("Order to take courses: ");
        printOrder(courseOrder);
    }
}
