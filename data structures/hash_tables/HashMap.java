/**
 * Custom implementation of a HashMap data structure.
 * 
 * This implementation uses separate chaining to handle collisions.
 * 
 * Time Complexity:
 * - Put operation: O(1) average case, O(n) worst case
 * - Get operation: O(1) average case, O(n) worst case
 * - Remove operation: O(1) average case, O(n) worst case
 * 
 * Space Complexity: O(n) where n is the number of key-value pairs
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashMap<K, V> {
    
    // Node class for linked list in each bucket
    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;
        
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    
    private Node<K, V>[] buckets;
    private int size;
    private final float loadFactor;
    
    /**
     * Constructs an empty HashMap with the default initial capacity (16)
     * and the default load factor (0.75).
     */
    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    
    /**
     * Constructs an empty HashMap with the specified initial capacity
     * and the default load factor (0.75).
     * 
     * @param initialCapacity the initial capacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }
    
    /**
     * Constructs an empty HashMap with the specified initial capacity
     * and load factor.
     * 
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        
        this.loadFactor = loadFactor;
        this.buckets = (Node<K, V>[]) new Node[initialCapacity];
        this.size = 0;
    }
    
    /**
     * Returns the number of key-value mappings in this map.
     * 
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns true if this map contains no key-value mappings.
     * 
     * @return true if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     * 
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         null if this map contains no mapping for the key
     */
    public V get(K key) {
        int index = getIndex(key, buckets.length);
        Node<K, V> node = buckets[index];
        
        while (node != null) {
            if (isEqual(key, node.key)) {
                return node.value;
            }
            node = node.next;
        }
        
        return null;
    }
    
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     * 
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there
     *         was no mapping for key
     */
    public V put(K key, V value) {
        if (needsResize()) {
            resize();
        }
        
        return putInternal(key, value, buckets);
    }
    
    /**
     * Internal method to put a key-value pair in the specified buckets array.
     * 
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @param buckets the buckets array to use
     * @return the previous value associated with key, or null if there
     *         was no mapping for key
     */
    private V putInternal(K key, V value, Node<K, V>[] buckets) {
        int index = getIndex(key, buckets.length);
        Node<K, V> node = buckets[index];
        
        // Check if key already exists
        while (node != null) {
            if (isEqual(key, node.key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }
        
        // Key doesn't exist, add new node at the beginning of the chain
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        
        return null;
    }
    
    /**
     * Removes the mapping for the specified key from this map if present.
     * 
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there
     *         was no mapping for key
     */
    public V remove(K key) {
        int index = getIndex(key, buckets.length);
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;
        
        while (current != null) {
            if (isEqual(key, current.key)) {
                if (prev == null) {
                    // Remove head of chain
                    buckets[index] = current.next;
                } else {
                    // Remove from middle or end of chain
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        
        return null;
    }
    
    /**
     * Removes all of the mappings from this map.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }
    
    /**
     * Returns true if this map contains a mapping for the specified key.
     * 
     * @param key The key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    /**
     * Returns an array of all keys in this map.
     * 
     * @return an array containing all the keys in this map
     */
    @SuppressWarnings("unchecked")
    public K[] keys() {
        K[] keys = (K[]) new Object[size];
        int index = 0;
        
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                keys[index++] = current.key;
                current = current.next;
            }
        }
        
        return keys;
    }
    
    /**
     * Returns an array of all values in this map.
     * 
     * @return an array containing all the values in this map
     */
    @SuppressWarnings("unchecked")
    public V[] values() {
        V[] values = (V[]) new Object[size];
        int index = 0;
        
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                values[index++] = current.value;
                current = current.next;
            }
        }
        
        return values;
    }
    
    /**
     * Returns the index in the buckets array for the specified key.
     * 
     * @param key the key
     * @param length the length of the buckets array
     * @return the index for the key
     */
    private int getIndex(K key, int length) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % length;
    }
    
    /**
     * Checks if two keys are equal, handling null keys.
     * 
     * @param key1 the first key
     * @param key2 the second key
     * @return true if the keys are equal
     */
    private boolean isEqual(K key1, K key2) {
        if (key1 == null) {
            return key2 == null;
        }
        return key1.equals(key2);
    }
    
    /**
     * Checks if the map needs to be resized.
     * 
     * @return true if the map needs to be resized
     */
    private boolean needsResize() {
        return size >= buckets.length * loadFactor;
    }
    
    /**
     * Resizes the backing array to twice its current size.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = buckets.length * 2;
        if (newCapacity > MAXIMUM_CAPACITY) {
            newCapacity = MAXIMUM_CAPACITY;
        }
        
        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[newCapacity];
        
        // Rehash all existing entries
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                Node<K, V> next = current.next;
                int newIndex = getIndex(current.key, newCapacity);
                
                // Insert at the beginning of the chain
                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;
                
                current = next;
            }
        }
        
        buckets = newBuckets;
    }
    
    /**
     * Returns a string representation of this map.
     * 
     * @return a string representation of this map
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.key).append("=").append(current.value);
                first = false;
                current = current.next;
            }
        }
        
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Main method with examples on how to use the HashMap
     */
    public static void main(String[] args) {
        // Example usage
        HashMap<String, Integer> map = new HashMap<>();
        
        // Add elements
        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);
        map.put("Dave", 40);
        
        System.out.println("HashMap: " + map);
        System.out.println("Size: " + map.size());
        
        // Get elements
        System.out.println("Alice's age: " + map.get("Alice"));
        System.out.println("Bob's age: " + map.get("Bob"));
        
        // Check if key exists
        System.out.println("Contains key 'Charlie': " + map.containsKey("Charlie"));
        System.out.println("Contains key 'Eve': " + map.containsKey("Eve"));
        
        // Update a value
        map.put("Alice", 26);
        System.out.println("Alice's new age: " + map.get("Alice"));
        
        // Remove an element
        System.out.println("Removing Dave: " + map.remove("Dave"));
        System.out.println("HashMap after removal: " + map);
        
        // Handle null keys
        map.put(null, 100);
        System.out.println("Value for null key: " + map.get(null));
        
        // Test collision handling (using objects with same hashcode)
        map.put("Ea", 50); // "Ea" and "FB" have the same hashcode
        map.put("FB", 60);
        System.out.println("Value for 'Ea': " + map.get("Ea"));
        System.out.println("Value for 'FB': " + map.get("FB"));
    }
}
