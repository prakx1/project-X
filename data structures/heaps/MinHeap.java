/**
 * Implementation of a Min Heap data structure.
 * 
 * A Min Heap is a complete binary tree where the value of each node is less than or equal
 * to the values of its children. The root element is always the minimum element in the heap.
 * 
 * Time Complexity:
 * - Insert: O(log n)
 * - Extract minimum: O(log n)
 * - Get minimum: O(1)
 * - Heapify: O(n)
 * 
 * Space Complexity: O(n)
 * 
 * @param <T> the type of elements in this heap
 */
public class MinHeap<T extends Comparable<T>> {
    
    // Array to store heap elements
    private Object[] heap;
    
    // Current size of the heap
    private int size;
    
    // Maximum capacity of the heap
    private int capacity;
    
    // Default initial capacity
    private static final int DEFAULT_CAPACITY = 10;
    
    /**
     * Constructs a min heap with default capacity.
     */
    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }
    
    /**
     * Constructs a min heap with specified capacity.
     * 
     * @param capacity the initial capacity for the heap
     */
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Object[capacity];
    }
    
    /**
     * Constructs a min heap from an array of elements.
     * 
     * @param array the array of elements to build the heap from
     */
    public MinHeap(T[] array) {
        this.size = array.length;
        this.capacity = Math.max(size * 2, DEFAULT_CAPACITY);
        this.heap = new Object[capacity];
        
        // Copy elements to the heap
        System.arraycopy(array, 0, heap, 0, size);
        
        // Heapify the array
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }
    
    /**
     * Returns the current size of the heap.
     * 
     * @return the number of elements in the heap
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the heap is empty.
     * 
     * @return true if the heap contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Gets the minimum element from the heap without removing it.
     * 
     * @return the minimum element
     * @throws IllegalStateException if the heap is empty
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return (T) heap[0];
    }
    
    /**
     * Inserts a new element into the heap.
     * 
     * @param element the element to add
     */
    public void insert(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        
        // If heap is full, resize it
        if (size == capacity) {
            resize();
        }
        
        // Insert at the end and bubble up
        heap[size] = element;
        siftUp(size);
        size++;
    }
    
    /**
     * Removes and returns the minimum element from the heap.
     * 
     * @return the minimum element
     * @throws IllegalStateException if the heap is empty
     */
    @SuppressWarnings("unchecked")
    public T extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        
        T min = (T) heap[0];
        
        // Replace root with last element and sift down
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        
        if (size > 0) {
            siftDown(0);
        }
        
        return min;
    }
    
    /**
     * Deletes an element at the specified index.
     * 
     * @param index the index of the element to delete
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        
        // Replace with last element
        heap[index] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        
        // If not the last element, sift up or down as needed
        if (size > 0 && index < size) {
            siftUp(index);
            siftDown(index);
        }
    }
    
    /**
     * Updates (decreases) the value of an element at the specified index.
     * 
     * @param index the index of the element to update
     * @param newValue the new value
     * @throws IndexOutOfBoundsException if the index is out of range
     * @throws IllegalArgumentException if the new value is greater than the current value
     */
    @SuppressWarnings("unchecked")
    public void decreaseKey(int index, T newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        
        T currentValue = (T) heap[index];
        if (newValue.compareTo(currentValue) > 0) {
            throw new IllegalArgumentException("New value must be less than current value");
        }
        
        heap[index] = newValue;
        siftUp(index);
    }
    
    /**
     * Clears all elements from the heap.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }
    
    /**
     * Builds a heap from an array of elements.
     * 
     * @param array the array to build the heap from
     */
    public void buildHeap(T[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        
        // Reset heap
        this.size = array.length;
        this.capacity = Math.max(size * 2, DEFAULT_CAPACITY);
        this.heap = new Object[capacity];
        
        // Copy elements
        System.arraycopy(array, 0, heap, 0, size);
        
        // Heapify
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }
    
    /**
     * Sifts an element up to maintain heap property.
     * 
     * @param index the index of the element to sift up
     */
    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        int parentIndex = getParentIndex(index);
        
        // If element is smaller than its parent, swap and continue sifting up
        while (index > 0 && ((T) heap[index]).compareTo((T) heap[parentIndex]) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }
    
    /**
     * Sifts an element down to maintain heap property.
     * 
     * @param index the index of the element to sift down
     */
    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        int smallest = index;
        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);
        
        // Find the smallest among node and its children
        if (leftChild < size && ((T) heap[leftChild]).compareTo((T) heap[smallest]) < 0) {
            smallest = leftChild;
        }
        
        if (rightChild < size && ((T) heap[rightChild]).compareTo((T) heap[smallest]) < 0) {
            smallest = rightChild;
        }
        
        // If one of the children is smaller, swap and continue sifting down
        if (smallest != index) {
            swap(index, smallest);
            siftDown(smallest);
        }
    }
    
    /**
     * Swaps two elements in the heap.
     * 
     * @param i the index of the first element
     * @param j the index of the second element
     */
    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    /**
     * Resizes the heap when it becomes full.
     */
    private void resize() {
        int newCapacity = capacity * 2;
        Object[] newHeap = new Object[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
        capacity = newCapacity;
    }
    
    /**
     * Gets the parent index of a node.
     * 
     * @param index the index of the node
     * @return the index of the parent node
     */
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }
    
    /**
     * Gets the left child index of a node.
     * 
     * @param index the index of the node
     * @return the index of the left child
     */
    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }
    
    /**
     * Gets the right child index of a node.
     * 
     * @param index the index of the node
     * @return the index of the right child
     */
    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }
    
    /**
     * Returns a string representation of the heap.
     * 
     * @return a string representation of the heap
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Performs heap sort on an array using a min heap.
     * Note: This will sort the array in descending order since we're using a min heap.
     * 
     * @param array the array to be sorted
     * @param <E> the type of elements in the array
     */
    public static <E extends Comparable<E>> void heapSort(E[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        MinHeap<E> minHeap = new MinHeap<>(array);
        
        // Extract all elements in sorted order (ascending)
        for (int i = 0; i < array.length; i++) {
            array[i] = minHeap.extractMin();
        }
    }
    
    /**
     * Main method with examples on how to use the MinHeap
     */
    public static void main(String[] args) {
        // Example usage
        MinHeap<Integer> minHeap = new MinHeap<>();
        
        // Insert elements
        minHeap.insert(5);
        minHeap.insert(3);
        minHeap.insert(8);
        minHeap.insert(1);
        minHeap.insert(10);
        minHeap.insert(2);
        
        System.out.println("Min Heap: " + minHeap);
        System.out.println("Size: " + minHeap.size());
        System.out.println("Minimum element: " + minHeap.peek());
        
        // Extract minimum elements
        System.out.println("\nExtracting elements in order:");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.extractMin() + " ");
        }
        System.out.println();
        
        // Build heap from array
        Integer[] array = {7, 4, 9, 2, 6, 8, 1, 3};
        minHeap.buildHeap(array);
        
        System.out.println("\nHeap built from array: " + minHeap);
        
        // Heap sort
        Integer[] arrayToSort = {7, 4, 9, 2, 6, 8, 1, 3};
        System.out.println("\nArray before sorting: " + java.util.Arrays.toString(arrayToSort));
        
        MinHeap.heapSort(arrayToSort);
        System.out.println("Array after sorting: " + java.util.Arrays.toString(arrayToSort));
        
        // Priority Queue example (k smallest elements)
        Integer[] largeArray = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
        int k = 4;
        
        System.out.println("\nFinding " + k + " smallest elements from: " + java.util.Arrays.toString(largeArray));
        
        MinHeap<Integer> priorityQueue = new MinHeap<>(largeArray);
        System.out.print(k + " smallest elements: ");
        for (int i = 0; i < k; i++) {
            System.out.print(priorityQueue.extractMin() + " ");
        }
        System.out.println();
    }
}
