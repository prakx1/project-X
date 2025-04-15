/**
 * Implementation of a Binary Search Tree (BST) data structure.
 * A BST is a binary tree where for each node:
 * - All nodes in the left subtree have values less than the node's value
 * - All nodes in the right subtree have values greater than the node's value
 * 
 * Time Complexity:
 * - Search: O(log n) average, O(n) worst case
 * - Insertion: O(log n) average, O(n) worst case
 * - Deletion: O(log n) average, O(n) worst case
 * 
 * Space Complexity: O(n) for the tree, O(h) for recursive operations where h is height of the tree
 */
public class BinarySearchTree<T extends Comparable<T>> {
    
    // Node class represents each element in the binary search tree
    public static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;
        
        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    // Root of the binary search tree
    private Node<T> root;
    
    // Constructor
    public BinarySearchTree() {
        this.root = null;
    }
    
    /**
     * Checks if the tree is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Inserts a new value into the BST
     * @param data the value to insert
     */
    public void insert(T data) {
        root = insertRec(root, data);
    }
    
    /**
     * Helper method for recursive insertion
     * @param root the current subtree root
     * @param data the value to insert
     * @return the updated subtree root
     */
    private Node<T> insertRec(Node<T> root, T data) {
        // Base case: empty tree or leaf node
        if (root == null) {
            return new Node<>(data);
        }
        
        // Compare and insert in correct subtree
        int cmp = data.compareTo(root.data);
        if (cmp < 0) {
            // Insert in left subtree
            root.left = insertRec(root.left, data);
        } else if (cmp > 0) {
            // Insert in right subtree
            root.right = insertRec(root.right, data);
        }
        // If equal, BST usually doesn't allow duplicates (can modify for multi-BST)
        
        return root;
    }
    
    /**
     * Searches for a value in the BST
     * @param data the value to search for
     * @return true if found, false otherwise
     */
    public boolean search(T data) {
        return searchRec(root, data);
    }
    
    /**
     * Helper method for recursive search
     * @param root the current subtree root
     * @param data the value to search for
     * @return true if found, false otherwise
     */
    private boolean searchRec(Node<T> root, T data) {
        // Base case: empty tree or leaf node reached without finding value
        if (root == null) {
            return false;
        }
        
        // Compare and search in correct subtree
        int cmp = data.compareTo(root.data);
        if (cmp == 0) {
            return true; // Found
        } else if (cmp < 0) {
            return searchRec(root.left, data); // Search in left subtree
        } else {
            return searchRec(root.right, data); // Search in right subtree
        }
    }
    
    /**
     * Deletes a value from the BST
     * @param data the value to delete
     */
    public void delete(T data) {
        root = deleteRec(root, data);
    }
    
    /**
     * Helper method for recursive deletion
     * @param root the current subtree root
     * @param data the value to delete
     * @return the updated subtree root
     */
    private Node<T> deleteRec(Node<T> root, T data) {
        // Base case: empty tree
        if (root == null) {
            return null;
        }
        
        // Find the node to delete
        int cmp = data.compareTo(root.data);
        if (cmp < 0) {
            // Delete from left subtree
            root.left = deleteRec(root.left, data);
        } else if (cmp > 0) {
            // Delete from right subtree
            root.right = deleteRec(root.right, data);
        } else {
            // Node found, perform deletion based on node type
            
            // Case 1: Leaf node (no children)
            if (root.left == null && root.right == null) {
                return null;
            }
            
            // Case 2: Node with one child
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            
            // Case 3: Node with two children
            // Find the minimum value in the right subtree (inorder successor)
            root.data = findMin(root.right);
            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data);
        }
        
        return root;
    }
    
    /**
     * Finds the minimum value in a subtree
     * @param node the root of the subtree
     * @return the minimum value
     */
    private T findMin(Node<T> node) {
        T minValue = node.data;
        while (node.left != null) {
            minValue = node.left.data;
            node = node.left;
        }
        return minValue;
    }
    
    /**
     * Performs an inorder traversal of the BST (sorted order)
     */
    public void inorderTraversal() {
        System.out.print("Inorder Traversal: ");
        inorderRec(root);
        System.out.println();
    }
    
    /**
     * Helper method for recursive inorder traversal
     * @param root the current subtree root
     */
    private void inorderRec(Node<T> root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
    
    /**
     * Performs a preorder traversal of the BST (root, left, right)
     */
    public void preorderTraversal() {
        System.out.print("Preorder Traversal: ");
        preorderRec(root);
        System.out.println();
    }
    
    /**
     * Helper method for recursive preorder traversal
     * @param root the current subtree root
     */
    private void preorderRec(Node<T> root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }
    
    /**
     * Performs a postorder traversal of the BST (left, right, root)
     */
    public void postorderTraversal() {
        System.out.print("Postorder Traversal: ");
        postorderRec(root);
        System.out.println();
    }
    
    /**
     * Helper method for recursive postorder traversal
     * @param root the current subtree root
     */
    private void postorderRec(Node<T> root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.data + " ");
        }
    }
    
    /**
     * Finds the height of the tree
     * @return the height of the tree
     */
    public int height() {
        return heightRec(root);
    }
    
    /**
     * Helper method for recursive height calculation
     * @param root the current subtree root
     * @return the height of the subtree
     */
    private int heightRec(Node<T> root) {
        if (root == null) {
            return -1;
        }
        
        int leftHeight = heightRec(root.left);
        int rightHeight = heightRec(root.right);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    /**
     * Main method with examples on how to use the BinarySearchTree
     */
    public static void main(String[] args) {
        // Example usage
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        
        // Insert elements
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);
        
        // Print traversals
        bst.inorderTraversal();   // Should print: 20 30 40 50 60 70 80
        bst.preorderTraversal();  // Should print: 50 30 20 40 70 60 80
        bst.postorderTraversal(); // Should print: 20 40 30 60 80 70 50
        
        // Search for elements
        System.out.println("Search for 40: " + bst.search(40)); // true
        System.out.println("Search for 100: " + bst.search(100)); // false
        
        // Tree height
        System.out.println("Tree height: " + bst.height()); // 2
        
        // Delete elements
        System.out.println("\nAfter deleting 20 (leaf node):");
        bst.delete(20);
        bst.inorderTraversal(); // Should print: 30 40 50 60 70 80
        
        System.out.println("After deleting 30 (node with one child):");
        bst.delete(30);
        bst.inorderTraversal(); // Should print: 40 50 60 70 80
        
        System.out.println("After deleting 50 (root node with two children):");
        bst.delete(50);
        bst.inorderTraversal(); // Should print: 40 60 70 80
    }
}
