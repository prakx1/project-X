import java.util.*;

/**
 * Implementation of Kruskal's algorithm for finding Minimum Spanning Tree.
 * 
 * A Minimum Spanning Tree (MST) is a subset of edges in a connected, 
 * undirected graph that connects all vertices with the minimum possible 
 * total edge weight.
 * 
 * Time Complexity: O(E log E) where E is the number of edges
 * Space Complexity: O(V + E) where V is the number of vertices
 * 
 * Common Interview Uses:
 * - Network design (minimize cost while connecting all nodes)
 * - Cluster analysis
 * - Circuit design
 */
public class KruskalMST {
    
    /**
     * Edge class to represent weighted edges in the graph
     */
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;
        
        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
        
        @Override
        public String toString() {
            return src + " -- " + dest + " == " + weight;
        }
    }
    
    /**
     * Union-Find data structure for Kruskal's algorithm
     */
    static class UnionFind {
        int[] parent, rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        // Find root of set with path compression
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        // Union by rank
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) return;
            
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
    
    private int V; // Number of vertices
    private List<Edge> edges; // Collection of all edges
    
    /**
     * Constructor
     * @param v number of vertices
     */
    public KruskalMST(int v) {
        this.V = v;
        this.edges = new ArrayList<>();
    }
    
    /**
     * Adds an edge to the graph
     * @param src source vertex
     * @param dest destination vertex
     * @param weight weight of the edge
     */
    public void addEdge(int src, int dest, int weight) {
        Edge edge = new Edge(src, dest, weight);
        edges.add(edge);
    }
    
    /**
     * Finds the Minimum Spanning Tree using Kruskal's algorithm
     * @return list of edges in the MST
     */
    public List<Edge> kruskalMST() {
        List<Edge> mst = new ArrayList<>();
        
        // Step 1: Sort all edges in non-decreasing order of their weight
        Collections.sort(edges);
        
        // Create disjoint sets
        UnionFind uf = new UnionFind(V);
        
        // Step 2: Pick the smallest edge and check if it forms a cycle with MST
        int edgeCount = 0;
        int index = 0;
        
        // Process all edges until we have V-1 edges in MST
        while (edgeCount < V - 1 && index < edges.size()) {
            Edge nextEdge = edges.get(index++);
            
            int x = uf.find(nextEdge.src);
            int y = uf.find(nextEdge.dest);
            
            // If including this edge doesn't cause a cycle, include it
            if (x != y) {
                mst.add(nextEdge);
                uf.union(x, y);
                edgeCount++;
            }
            // Else discard it
        }
        
        return mst;
    }
    
    /**
     * Calculates the total weight of the MST
     * @param mst list of edges in the MST
     * @return total weight
     */
    public int getTotalWeight(List<Edge> mst) {
        int totalWeight = 0;
        for (Edge edge : mst) {
            totalWeight += edge.weight;
        }
        return totalWeight;
    }
    
    /**
     * Example usage of Kruskal's MST algorithm
     */
    public static void main(String[] args) {
        // Example graph with 5 vertices
        /*
        (0)--(1)
        |   / |
        |  /  |
        | /   |
        (2)--(3)
        |
        |
        (4)
        
        The numbers on edges are weights
        */
        int V = 5;
        KruskalMST graph = new KruskalMST(V);
        
        // Add edges
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);
        graph.addEdge(2, 4, 2);
        
        // Find MST using Kruskal's
        List<Edge> mst = graph.kruskalMST();
        
        System.out.println("Edges in MST:");
        for (Edge edge : mst) {
            System.out.println(edge);
        }
        
        System.out.println("Total MST weight: " + graph.getTotalWeight(mst));
        
        // Larger example
        V = 9;
        KruskalMST largeGraph = new KruskalMST(V);
        
        largeGraph.addEdge(0, 1, 4);
        largeGraph.addEdge(0, 7, 8);
        largeGraph.addEdge(1, 2, 8);
        largeGraph.addEdge(1, 7, 11);
        largeGraph.addEdge(2, 3, 7);
        largeGraph.addEdge(2, 8, 2);
        largeGraph.addEdge(2, 5, 4);
        largeGraph.addEdge(3, 4, 9);
        largeGraph.addEdge(3, 5, 14);
        largeGraph.addEdge(4, 5, 10);
        largeGraph.addEdge(5, 6, 2);
        largeGraph.addEdge(6, 7, 1);
        largeGraph.addEdge(6, 8, 6);
        largeGraph.addEdge(7, 8, 7);
        
        mst = largeGraph.kruskalMST();
        
        System.out.println("\nLarger Example - Edges in MST:");
        for (Edge edge : mst) {
            System.out.println(edge);
        }
        
        System.out.println("Total MST weight: " + largeGraph.getTotalWeight(mst));
    }
}
