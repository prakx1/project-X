/**
 * Implementation of Union-Find (Disjoint Set) data structure.
 * 
 * Union-Find is a data structure that keeps track of elements partitioned into 
 * non-overlapping sets. It provides near-constant time operations to:
 * 1. Create a new set with a single element (MakeSet)
 * 2. Find which set an element belongs to (Find)
 * 3. Merge two sets (Union)
 * 
 * Time Complexity:
 * - MakeSet: O(1)
 * - Find: O(α(n)) where α is the inverse Ackermann function (practically constant)
 * - Union: O(α(n))
 * 
 * Space Complexity: O(n)
 * 
 * Common Interview Uses:
 * - Kruskal's algorithm for Minimum Spanning Trees
 * - Detecting cycles in undirected graphs
 * - Finding connected components
 * - Network connectivity problems
 */
public class UnionFind {
    
    private int[] parent;  // parent[i] = parent of i
    private int[] rank;    // rank[i] = rank of subtree rooted at i (never decreases)
    private int count;     // number of components
    
    /**
     * Initialize Union-Find data structure with n elements
     * @param n number of elements
     */
    public UnionFind(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of elements must be positive");
        }
        
        count = n;
        parent = new int[n];
        rank = new int[n];
        
        // Initialize each element as its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    
    /**
     * Find the root of element p with path compression
     * @param p element to find
     * @return the root of p
     */
    public int find(int p) {
        validateElement(p);
        
        // Path compression: make every examined node point directly to the root
        if (p != parent[p]) {
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }
    
    /**
     * Check if elements p and q are in the same set
     * @param p first element
     * @param q second element
     * @return true if p and q are in the same set, false otherwise
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
    
    /**
     * Merge sets containing elements p and q
     * @param p first element
     * @param q second element
     * @return true if p and q were not already in the same set, false otherwise
     */
    public boolean union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        
        if (rootP == rootQ) {
            return false; // Already in the same set
        }
        
        // Union by rank: attach the smaller rank tree under the root of the higher rank tree
        if (rank[rootP] < rank[rootQ]) {
            parent[rootP] = rootQ;
        } else if (rank[rootP] > rank[rootQ]) {
            parent[rootQ] = rootP;
        } else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        
        count--;
        return true;
    }
    
    /**
     * Get the number of disjoint sets
     * @return the number of sets
     */
    public int count() {
        return count;
    }
    
    /**
     * Validate that element p is a valid index
     */
    private void validateElement(int p) {
        if (p < 0 || p >= parent.length) {
            throw new IllegalArgumentException("Element " + p + " is not a valid index");
        }
    }
    
    /**
     * Example usage of UnionFind data structure
     */
    public static void main(String[] args) {
        // Initialize with 10 elements
        UnionFind uf = new UnionFind(10);
        System.out.println("Initial number of sets: " + uf.count());
        
        // Perform some unions
        System.out.println("Union 0-1: " + uf.union(0, 1));
        System.out.println("Union 2-3: " + uf.union(2, 3));
        System.out.println("Union 4-5: " + uf.union(4, 5));
        System.out.println("Union 6-7: " + uf.union(6, 7));
        System.out.println("Union 8-9: " + uf.union(8, 9));
        System.out.println("Number of sets after initial unions: " + uf.count());
        
        // Connect some of the sets
        System.out.println("Union 0-2: " + uf.union(0, 2));
        System.out.println("Union 4-6: " + uf.union(4, 6));
        System.out.println("Union 0-4: " + uf.union(0, 4));
        System.out.println("Number of sets now: " + uf.count());
        
        // Check connectivity
        System.out.println("Is 1 connected to 7? " + uf.connected(1, 7));
        System.out.println("Is 2 connected to 9? " + uf.connected(2, 9));
        
        // Try to union already connected elements
        System.out.println("Union 1-5 (already connected): " + uf.union(1, 5));
        
        // Final connection
        System.out.println("Union 0-8: " + uf.union(0, 8));
        System.out.println("Final number of sets: " + uf.count());
        System.out.println("Is everyone connected? " + (uf.count() == 1));
        
        // Cycle detection example
        System.out.println("\nCycle Detection Example:");
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}};
        boolean hasCycle = detectCycle(6, edges);
        System.out.println("Graph has cycle: " + hasCycle);
    }
    
    /**
     * Utility method to detect a cycle in an undirected graph using Union-Find
     * @param n number of vertices
     * @param edges array of edges, each edge is an array of two vertices
     * @return true if the graph contains a cycle, false otherwise
     */
    public static boolean detectCycle(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            // If vertices are already in the same set, adding this edge creates a cycle
            if (uf.connected(u, v)) {
                System.out.println("Cycle detected with edge: " + u + "-" + v);
                return true;
            }
            
            // Union the sets
            uf.union(u, v);
        }
        
        return false;
    }
}
