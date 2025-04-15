import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of an LRU (Least Recently Used) Cache.
 * 
 * LRU Cache is a data structure that maintains a fixed-size cache of items,
 * evicting the least recently used item when the cache is full and a new item needs to be added.
 * 
 * Time Complexity:
 * - get: O(1)
 * - put: O(1)
 * 
 * Space Complexity: O(capacity)
 * 
 * This is a very common interview question that tests understanding of hashmap and doubly linked list.
 */
public class LRUCache {
    
    // Node class for doubly linked list
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private Map<Integer, Node> cache;
    private Node head; // Most recently used
    private Node tail; // Least recently used
    
    /**
     * Constructor initializes an empty LRU Cache with given capacity
     * @param capacity maximum number of items cache can hold
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Initialize dummy head and tail nodes
        head = new Node(0, 0);
        tail = new Node(0, 0);
        
        // Connect head and tail
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Retrieves value from cache if exists, also marks it as recently used
     * @param key key to look up
     * @return value if found, -1 otherwise
     */
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        // Get node from cache
        Node node = cache.get(key);
        
        // Move to front (most recently used position)
        moveToHead(node);
        
        return node.value;
    }
    
    /**
     * Adds or updates a key-value pair in the cache
     * If cache is full, removes least recently used item
     * @param key key to add or update
     * @param value value to store
     */
    public void put(int key, int value) {
        // Check if key already exists
        if (cache.containsKey(key)) {
            // Update existing node
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
            return;
        }
        
        // Check if cache is full
        if (cache.size() >= capacity) {
            // Remove least recently used item (from tail)
            Node lru = tail.prev;
            removeNode(lru);
            cache.remove(lru.key);
        }
        
        // Add new node
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addToHead(newNode);
    }
    
    /**
     * Helper method to remove a node from the doubly linked list
     */
    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        
        prev.next = next;
        next.prev = prev;
    }
    
    /**
     * Helper method to add a node at the head (most recently used position)
     */
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Helper method to move a node to the head (mark as recently used)
     */
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
    
    /**
     * Helper method to print the current state of the cache
     * for debugging or demonstration
     */
    public void printCache() {
        System.out.print("Current Cache (MRU to LRU): ");
        Node current = head.next;
        while (current != tail) {
            System.out.print("(" + current.key + "," + current.value + ") ");
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * Example usage of LRU Cache
     */
    public static void main(String[] args) {
        // Initialize cache with capacity 3
        LRUCache cache = new LRUCache(3);
        
        // Add elements
        cache.put(1, 100);
        cache.printCache(); // (1,100)
        
        cache.put(2, 200);
        cache.printCache(); // (2,200) (1,100)
        
        cache.put(3, 300);
        cache.printCache(); // (3,300) (2,200) (1,100)
        
        // Access an element (marks as recently used)
        System.out.println("Get key 1: " + cache.get(1));
        cache.printCache(); // (1,100) (3,300) (2,200)
        
        // Add a new element when cache is full
        cache.put(4, 400);
        cache.printCache(); // (4,400) (1,100) (3,300) - key 2 is evicted
        
        // Try to access evicted element
        System.out.println("Get key 2 (evicted): " + cache.get(2));
        
        // Update existing element
        cache.put(1, 150);
        cache.printCache(); // (1,150) (4,400) (3,300)
        
        // Add another element
        cache.put(5, 500);
        cache.printCache(); // (5,500) (1,150) (4,400) - key 3 is evicted
    }
}
