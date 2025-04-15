/**
 * Implementation of a Disjoint Set (Union-Find) data structure.
 * 
 * Disjoint Set is a data structure that keeps track of a set of elements partitioned
 * into a number of disjoint (non-overlapping) subsets. It provides near-constant-time
 * operations for adding new sets, merging sets, and determining whether elements are in the same set.
 * 
 * This implementation uses both path compression and union by rank optimizations
 * which gives an amortized time complexity of O(α(n)) per operation, where α is the
 * inverse Ackermann function, which grows extremely slowly.
 * 
 * Time Complexity:
 * - makeSet: O(1)
 * - find: O(α(n)) amortized
 * - union: O(α(n)) amortized
 * 
 * Space Complexity: O(n)
 * 
 * Common applications:
 * - Kruskal's algorithm for minimum spanning tree
 * - Finding connected components in a graph
 * - Cycle detection in graphs
 * - Network connectivity
 */
import java.util.*;

public class DisjointSet<T> {
    
    // Map to store the parent of each element
    private Map<T, T> parent;
    
    // Map to store the rank (approximate depth) of each set
    private Map<T, Integer> rank;
    
    // Map to store the size of each set
    private Map<T, Integer> size;
    
    // Number of disjoint sets
    private int count;
    
    /**
     * Constructs an empty DisjointSet.
     */
    public DisjointSet() {
        parent = new HashMap<>();
        rank = new HashMap<>();
        size = new HashMap<>();
        count = 0;
    }
    
    /**
     * Creates a new set with a single element.
     * 
     * @param element the element to create a set for
     * @return true if a new set was created, false if the element already exists
     */
    public boolean makeSet(T element) {
        if (parent.containsKey(element)) {
            return false; // Element already exists
        }
        
        parent.put(element, element); // Each element is its own parent initially
        rank.put(element, 0);   // Initial rank is 0
        size.put(element, 1);   // Initial size is 1
        count++;                // Increment count of disjoint sets
        
        return true;
    }
    
    /**
     * Finds the representative (root) of the set containing element.
     * Uses path compression to optimize future operations.
     * 
     * @param element the element to find the representative for
     * @return the representative of the set, or null if element doesn't exist
     */
    public T find(T element) {
        if (!parent.containsKey(element)) {
            return null; // Element doesn't exist
        }
        
        // Path compression: Make every examined node point directly to the root
        if (!parent.get(element).equals(element)) {
            parent.put(element, find(parent.get(element)));
        }
        
        return parent.get(element);
    }
    
    /**
     * Merges the sets containing elements x and y.
     * Uses union by rank to keep the tree balanced.
     * 
     * @param x the first element
     * @param y the second element
     * @return true if the sets were merged, false if the elements were already in the same set or don't exist
     */
    public boolean union(T x, T y) {
        T rootX = find(x);
        T rootY = find(y);
        
        // If either element doesn't exist or they're already in the same set
        if (rootX == null || rootY == null || rootX.equals(rootY)) {
            return false;
        }
        
        // Union by rank: Attach smaller rank tree under root of higher rank tree
        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);
        
        if (rankX < rankY) {
            // Attach X under Y
            parent.put(rootX, rootY);
            // Update size of Y's tree
            size.put(rootY, size.get(rootX) + size.get(rootY));
        } else if (rankX > rankY) {
            // Attach Y under X
            parent.put(rootY, rootX);
            // Update size of X's tree
            size.put(rootX, size.get(rootX) + size.get(rootY));
        } else {
            // Same rank, attach Y under X and increment X's rank
            parent.put(rootY, rootX);
            rank.put(rootX, rankX + 1);
            // Update size of X's tree
            size.put(rootX, size.get(rootX) + size.get(rootY));
        }
        
        count--; // Decrement count of disjoint sets
        
        return true;
    }
    
    /**
     * Checks if two elements are in the same set.
     * 
     * @param x the first element
     * @param y the second element
     * @return true if the elements are in the same set, false otherwise
     */
    public boolean isConnected(T x, T y) {
        T rootX = find(x);
        T rootY = find(y);
        
        // If either element doesn't exist or they're in different sets
        return rootX != null && rootY != null && rootX.equals(rootY);
    }
    
    /**
     * Gets the number of elements in the set containing the specified element.
     * 
     * @param element the element to get the set size for
     * @return the size of the set, or 0 if the element doesn't exist
     */
    public int getSetSize(T element) {
        T root = find(element);
        return root != null ? size.get(root) : 0;
    }
    
    /**
     * Gets the number of disjoint sets.
     * 
     * @return the number of disjoint sets
     */
    public int getCount() {
        return count;
    }
    
    /**
     * Gets all the elements in the set containing the specified element.
     * 
     * @param element the element to get the set members for
     * @return a list of all elements in the same set, or an empty list if the element doesn't exist
     */
    public List<T> getSetMembers(T element) {
        T root = find(element);
        if (root == null) {
            return new ArrayList<>();
        }
        
        List<T> members = new ArrayList<>();
        for (T item : parent.keySet()) {
            if (find(item).equals(root)) {
                members.add(item);
            }
        }
        
        return members;
    }
    
    /**
     * Gets all distinct sets in the DisjointSet.
     * 
     * @return a map from representative elements to the sets they represent
     */
    public Map<T, List<T>> getAllSets() {
        Map<T, List<T>> sets = new HashMap<>();
        
        for (T element : parent.keySet()) {
            T representative = find(element);
            sets.computeIfAbsent(representative, k -> new ArrayList<>()).add(element);
        }
        
        return sets;
    }
    
    /**
     * Main method with examples on how to use the DisjointSet
     */
    public static void main(String[] args) {
        // Example with integers
        DisjointSet<Integer> ds = new DisjointSet<>();
        
        // Create individual sets
        for (int i = 1; i <= 10; i++) {
            ds.makeSet(i);
        }
        
        System.out.println("Initial number of sets: " + ds.getCount()); // 10
        
        // Perform some unions
        ds.union(1, 2);
        ds.union(3, 4);
        ds.union(5, 6);
        ds.union(7, 8);
        ds.union(9, 10);
        
        System.out.println("Number of sets after first unions: " + ds.getCount()); // 5
        
        ds.union(1, 3);
        ds.union(5, 7);
        ds.union(9, 5);
        
        System.out.println("Number of sets after more unions: " + ds.getCount()); // 2
        
        // Check if elements are connected
        System.out.println("Is 1 connected to 4? " + ds.isConnected(1, 4)); // true
        System.out.println("Is 1 connected to 5? " + ds.isConnected(1, 5)); // false
        
        // Get size of sets
        System.out.println("Size of set containing 1: " + ds.getSetSize(1)); // 4
        System.out.println("Size of set containing 5: " + ds.getSetSize(5)); // 6
        
        // Get members of a set
        System.out.println("Members of set containing 1: " + ds.getSetMembers(1));
        System.out.println("Members of set containing 5: " + ds.getSetMembers(5));
        
        // Combine all into one set
        ds.union(1, 5);
        
        System.out.println("Number of sets after final union: " + ds.getCount()); // 1
        System.out.println("Size of final set: " + ds.getSetSize(1)); // 10
        
        // Get all sets
        System.out.println("\nAll sets: ");
        Map<Integer, List<Integer>> allSets = ds.getAllSets();
        for (Map.Entry<Integer, List<Integer>> entry : allSets.entrySet()) {
            System.out.println("Set with representative " + entry.getKey() + ": " + entry.getValue());
        }
        
        // Example with strings (e.g., for connected computers in a network)
        System.out.println("\nExample with strings:");
        DisjointSet<String> network = new DisjointSet<>();
        
        // Create individual computers
        String[] computers = {"A", "B", "C", "D", "E", "F"};
        for (String computer : computers) {
            network.makeSet(computer);
        }
        
        // Connect some computers
        network.union("A", "B"); // A and B are connected
        network.union("C", "D"); // C and D are connected
        network.union("E", "F"); // E and F are connected
        
        System.out.println("Number of networks: " + network.getCount()); // 3
        
        // Connect networks
        network.union("B", "C"); // Now A, B, C, D are all connected
        
        System.out.println("Number of networks after connecting B and C: " + network.getCount()); // 2
        System.out.println("Is A connected to D? " + network.isConnected("A", "D")); // true
        System.out.println("Is A connected to E? " + network.isConnected("A", "E")); // false
        
        // Computers in the same network as A
        System.out.println("Computers in the same network as A: " + network.getSetMembers("A"));
    }
}
