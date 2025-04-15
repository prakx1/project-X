/**
 * Implementation of a Singly Linked List data structure.
 * Time Complexity:
 * - Insertion at beginning: O(1)
 * - Insertion at end (with tail pointer): O(1)
 * - Insertion at middle: O(n)
 * - Deletion: O(n)
 * - Search: O(n)
 * 
 * Space Complexity: O(n)
 */
public class SinglyLinkedList<T> {
    
    // Node class represents each element in the linked list
    public static class Node<T> {
        T data;
        Node<T> next;
        
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    // Head and tail pointers for the linked list
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    // Constructor
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Checks if the linked list is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }
    
    /**
     * Returns the current size of the linked list
     * @return number of elements in the list
     */
    public int size() {
        return size;
    }
    
    /**
     * Adds an element to the beginning of the list
     * @param data the element to add
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }
    
    /**
     * Adds an element to the end of the list
     * @param data the element to add
     */
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    /**
     * Adds an element at a specific position in the list
     * @param data the element to add
     * @param index the position where to add
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void add(T data, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        
        if (index == 0) {
            addFirst(data);
            return;
        }
        
        if (index == size) {
            addLast(data);
            return;
        }
        
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        Node<T> newNode = new Node<>(data);
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }
    
    /**
     * Removes the first element of the list
     * @return the removed element
     * @throws RuntimeException if the list is empty
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        
        T removedData = head.data;
        head = head.next;
        size--;
        
        if (isEmpty()) {
            tail = null;
        }
        
        return removedData;
    }
    
    /**
     * Removes the last element of the list
     * @return the removed element
     * @throws RuntimeException if the list is empty
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        
        if (head == tail) {
            return removeFirst();
        }
        
        Node<T> current = head;
        while (current.next != tail) {
            current = current.next;
        }
        
        T removedData = tail.data;
        tail = current;
        tail.next = null;
        size--;
        
        return removedData;
    }
    
    /**
     * Removes an element at a specific position
     * @param index the position to remove from
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        
        if (index == 0) {
            return removeFirst();
        }
        
        if (index == size - 1) {
            return removeLast();
        }
        
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        T removedData = current.next.data;
        current.next = current.next.next;
        size--;
        
        return removedData;
    }
    
    /**
     * Gets an element at a specific position
     * @param index the position to get from
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        
        return current.data;
    }
    
    /**
     * Searches for an element in the list
     * @param data the element to search for
     * @return the index of the element, or -1 if not found
     */
    public int indexOf(T data) {
        Node<T> current = head;
        int index = 0;
        
        while (current != null) {
            if ((data == null && current.data == null) || 
                (data != null && data.equals(current.data))) {
                return index;
            }
            current = current.next;
            index++;
        }
        
        return -1;
    }
    
    /**
     * Reverses the linked list iteratively
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public void reverse() {
        if (isEmpty() || head == tail) {
            return;
        }
        
        Node<T> prev = null;
        Node<T> current = head;
        Node<T> next = null;
        tail = head;
        
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        head = prev;
    }
    
    /**
     * Detects if there is a cycle in the linked list using Floyd's Cycle-Finding Algorithm
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @return true if a cycle is detected, false otherwise
     */
    public boolean hasCycle() {
        if (isEmpty() || head.next == null) {
            return false;
        }
        
        Node<T> slow = head;
        Node<T> fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns a string representation of the linked list
     * @return string showing all elements in the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Main method with examples on how to use the SinglyLinkedList
     */
    public static void main(String[] args) {
        // Example usage
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        
        // Add elements
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addFirst(0);
        list.add(4, 4);
        
        System.out.println("Original list: " + list);
        System.out.println("Size: " + list.size());
        
        // Access elements
        System.out.println("Element at index 2: " + list.get(2));
        System.out.println("Index of element 3: " + list.indexOf(3));
        
        // Remove elements
        System.out.println("Removed first element: " + list.removeFirst());
        System.out.println("Removed last element: " + list.removeLast());
        System.out.println("Removed element at index 1: " + list.remove(1));
        
        System.out.println("List after removals: " + list);
        
        // Reverse the list
        list.reverse();
        System.out.println("Reversed list: " + list);
    }
}
