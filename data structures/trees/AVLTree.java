/**
 * Implementation of an AVL (Adelson-Velsky and Landis) Tree.
 * 
 * AVL Tree is a self-balancing binary search tree where the difference between 
 * heights of left and right subtrees (balance factor) for any node cannot be more than 1.
 * 
 * Time Complexity:
 * - Search: O(log n)
 * - Insert: O(log n)
 * - Delete: O(log n)
 * 
 * Space Complexity: O(n) for the tree, O(log n) for recursive operations
 * 
 * @param <T> the type of elements in this tree
 */
public class AVLTree<T extends Comparable<T>> {
    
    /**
     * Node class for AVL Tree
     */
    private class Node {
        T data;
        Node left;
        Node right;
        int height; // Height of the node
        
        public Node(T data) {
            this.data = data;
            this.height = 1; // New node is added at leaf with height 1
        }
    }
    
    private Node root;
    
    /**
     * Constructs an empty AVL tree.
     */
    public AVLTree() {
        this.root = null;
    }
    
    /**
     * Inserts a new element into the AVL tree.
     * 
     * @param data the data to insert
     */
    public void insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }
        root = insert(root, data);
    }
    
    /**
     * Recursive helper method to insert an element.
     * 
     * @param node the root of the current subtree
     * @param data the data to insert
     * @return the new root of the subtree after insertion and rebalancing
     */
    private Node insert(Node node, T data) {
        // 1. Perform standard BST insert
        if (node == null) {
            return new Node(data);
        }
        
        int compareResult = data.compareTo(node.data);
        
        if (compareResult < 0) {
            node.left = insert(node.left, data);
        } else if (compareResult > 0) {
            node.right = insert(node.right, data);
        } else {
            // Duplicate values not allowed (can modify this behavior if needed)
            return node;
        }
        
        // 2. Update height of current node
        node.height = 1 + Math.max(height(node.left), height(node.right));
        
        // 3. Get the balance factor and rebalance if needed
        int balance = getBalance(node);
        
        // Left Left Case (Single Right Rotation)
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }
        
        // Right Right Case (Single Left Rotation)
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }
        
        // Left Right Case (Double Rotation: Left then Right)
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Right Left Case (Double Rotation: Right then Left)
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        // No imbalance
        return node;
    }
    
    /**
     * Searches for an element in the AVL tree.
     * 
     * @param data the data to search for
     * @return true if the element is found, false otherwise
     */
    public boolean search(T data) {
        return search(root, data);
    }
    
    /**
     * Recursive helper method to search for an element.
     * 
     * @param node the root of the current subtree
     * @param data the data to search for
     * @return true if the element is found, false otherwise
     */
    private boolean search(Node node, T data) {
        if (node == null) {
            return false;
        }
        
        int compareResult = data.compareTo(node.data);
        
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return search(node.left, data);
        } else {
            return search(node.right, data);
        }
    }
    
    /**
     * Deletes an element from the AVL tree.
     * 
     * @param data the data to delete
     */
    public void delete(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot delete null value");
        }
        root = delete(root, data);
    }
    
    /**
     * Recursive helper method to delete an element.
     * 
     * @param node the root of the current subtree
     * @param data the data to delete
     * @return the new root of the subtree after deletion and rebalancing
     */
    private Node delete(Node node, T data) {
        if (node == null) {
            return null;
        }
        
        int compareResult = data.compareTo(node.data);
        
        // Find the node to delete
        if (compareResult < 0) {
            node.left = delete(node.left, data);
        } else if (compareResult > 0) {
            node.right = delete(node.right, data);
        } else {
            // Node found, perform deletion
            
            // Case 1: Node has no children or only one child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            // Case 2: Node has two children
            // Find inorder successor (smallest in right subtree)
            node.data = findMin(node.right);
            
            // Delete the inorder successor
            node.right = delete(node.right, node.data);
        }
        
        // If tree had only one node, return null after deletion
        if (node == null) {
            return null;
        }
        
        // Update height of current node
        node.height = 1 + Math.max(height(node.left), height(node.right));
        
        // Get balance factor and rebalance if needed
        int balance = getBalance(node);
        
        // Left Left Case (Single Right Rotation)
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        
        // Left Right Case (Double Rotation: Left then Right)
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Right Right Case (Single Left Rotation)
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        
        // Right Left Case (Double Rotation: Right then Left)
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }
    
    /**
     * Finds the minimum value in a subtree.
     * 
     * @param node the root of the subtree
     * @return the minimum value
     */
    private T findMin(Node node) {
        T minValue = node.data;
        while (node.left != null) {
            minValue = node.left.data;
            node = node.left;
        }
        return minValue;
    }
    
    /**
     * Gets the height of a node (null nodes have height 0).
     * 
     * @param node the node to get height for
     * @return the height of the node, or 0 if node is null
     */
    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }
    
    /**
     * Gets the balance factor of a node.
     * Balance factor = height of left subtree - height of right subtree
     * 
     * @param node the node to get balance factor for
     * @return the balance factor
     */
    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }
    
    /**
     * Performs a right rotation on a subtree.
     * 
     *     y                x
     *    / \              / \
     *   x   T3   -->     T1  y
     *  / \                  / \
     * T1  T2               T2  T3
     * 
     * @param y the root of the subtree to rotate
     * @return the new root after rotation
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        
        // Perform rotation
        x.right = y;
        y.left = T2;
        
        // Update heights
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        
        // Return new root
        return x;
    }
    
    /**
     * Performs a left rotation on a subtree.
     * 
     *   x                 y
     *  / \               / \
     * T1  y     -->     x   T3
     *    / \           / \
     *   T2  T3        T1  T2
     * 
     * @param x the root of the subtree to rotate
     * @return the new root after rotation
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        
        // Perform rotation
        y.left = x;
        x.right = T2;
        
        // Update heights
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        
        // Return new root
        return y;
    }
    
    /**
     * Gets the height of the AVL tree.
     * 
     * @return the height of the tree
     */
    public int getHeight() {
        return height(root);
    }
    
    /**
     * Checks if the AVL tree is empty.
     * 
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Performs an inorder traversal of the AVL tree.
     */
    public void inorder() {
        System.out.print("Inorder Traversal: ");
        inorder(root);
        System.out.println();
    }
    
    /**
     * Recursive helper method for inorder traversal.
     * 
     * @param node the root of the current subtree
     */
    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }
    
    /**
     * Performs a level order traversal of the AVL tree.
     */
    public void levelOrder() {
        System.out.print("Level Order Traversal: ");
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(node.data + " ");
            
            if (node.left != null) {
                queue.add(node.left);
            }
            
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        
        System.out.println();
    }
    
    /**
     * Checks if the tree is a valid AVL tree (balanced and BST properties).
     * 
     * @return true if the tree is a valid AVL tree, false otherwise
     */
    public boolean isValidAVL() {
        return isValidAVL(root);
    }
    
    /**
     * Recursive helper method to check if a subtree is a valid AVL tree.
     * 
     * @param node the root of the subtree
     * @return true if the subtree is a valid AVL tree, false otherwise
     */
    private boolean isValidAVL(Node node) {
        if (node == null) {
            return true;
        }
        
        // Check balance factor
        int balance = getBalance(node);
        if (Math.abs(balance) > 1) {
            return false;
        }
        
        // Check BST property and recurse
        return isBST(node, null, null) && isValidAVL(node.left) && isValidAVL(node.right);
    }
    
    /**
     * Checks if a subtree is a valid binary search tree.
     * 
     * @param node the root of the subtree
     * @param min the minimum allowed value for nodes in this subtree
     * @param max the maximum allowed value for nodes in this subtree
     * @return true if the subtree is a valid BST, false otherwise
     */
    private boolean isBST(Node node, T min, T max) {
        if (node == null) {
            return true;
        }
        
        // Check if current node's value is within allowed range
        if ((min != null && node.data.compareTo(min) <= 0) || 
            (max != null && node.data.compareTo(max) >= 0)) {
            return false;
        }
        
        // Recurse for left and right subtrees with updated range
        return isBST(node.left, min, node.data) && isBST(node.right, node.data, max);
    }
    
    /**
     * Main method with examples on how to use the AVL Tree
     */
    public static void main(String[] args) {
        // Example usage
        AVLTree<Integer> avlTree = new AVLTree<>();
        
        // Insert elements
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);  // Will trigger single left rotation
        avlTree.insert(40);
        avlTree.insert(50);  // Will trigger single left rotation
        avlTree.insert(25);  // Will trigger right-left rotation
        
        // Print traversals
        avlTree.inorder();      // Should print: 10 20 25 30 40 50
        avlTree.levelOrder();   // Should print tree level by level
        
        System.out.println("Height of tree: " + avlTree.getHeight());
        System.out.println("Is valid AVL tree: " + avlTree.isValidAVL());
        
        // Search operations
        System.out.println("Search for 25: " + avlTree.search(25));  // true
        System.out.println("Search for 55: " + avlTree.search(55));  // false
        
        // Delete operations
        System.out.println("\nAfter deleting 20:");
        avlTree.delete(20);
        avlTree.inorder();      // Should print: 10 25 30 40 50
        System.out.println("Is valid AVL tree: " + avlTree.isValidAVL());
        
        System.out.println("\nAfter deleting 30:");
        avlTree.delete(30);
        avlTree.inorder();      // Should print: 10 25 40 50
        System.out.println("Height of tree: " + avlTree.getHeight());
        System.out.println("Is valid AVL tree: " + avlTree.isValidAVL());
        
        // Demo of tree balancing
        System.out.println("\nDemo of tree balancing with sequential inserts:");
        AVLTree<Integer> sequentialTree = new AVLTree<>();
        
        // Insert elements in sequence would create a skewed tree in regular BST
        // but AVL tree will maintain balance
        for (int i = 1; i <= 10; i++) {
            sequentialTree.insert(i);
        }
        
        sequentialTree.levelOrder();  // Should show a balanced tree
        System.out.println("Height of balanced tree with 10 elements: " + sequentialTree.getHeight());
        System.out.println("Is valid AVL tree: " + sequentialTree.isValidAVL());
    }
}
