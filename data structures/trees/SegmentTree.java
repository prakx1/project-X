/**
 * Segment Tree implementation for range queries and updates.
 * 
 * Time Complexity:
 * - Construction: O(n)
 * - Query: O(log n)
 * - Update: O(log n)
 * 
 * Space Complexity: O(n)
 * 
 * Common interview uses:
 * - Range sum queries
 * - Range minimum/maximum queries
 * - Range GCD/LCM queries
 */
public class SegmentTree {
    
    private int[] tree;
    private int n;
    
    /**
     * Builds a segment tree for range sum queries
     * @param arr input array
     */
    public SegmentTree(int[] arr) {
        n = arr.length;
        // Height of segment tree
        int height = (int) (Math.ceil(Math.log(n) / Math.log(2)));
        // Maximum size of segment tree
        int maxSize = 2 * (int) Math.pow(2, height) - 1;
        
        tree = new int[maxSize];
        buildTree(arr, 0, n - 1, 0);
    }
    
    /**
     * Recursive function to build the segment tree
     */
    private int buildTree(int[] arr, int start, int end, int treeNode) {
        // If there is only one element in the array
        if (start == end) {
            tree[treeNode] = arr[start];
            return arr[start];
        }
        
        // Recursively build left and right children
        int mid = start + (end - start) / 2;
        int left = buildTree(arr, start, mid, 2 * treeNode + 1);
        int right = buildTree(arr, mid + 1, end, 2 * treeNode + 2);
        
        // Store the sum of left and right children
        tree[treeNode] = left + right;
        return tree[treeNode];
    }
    
    /**
     * Returns sum of elements in range [queryStart...queryEnd]
     */
    public int getSum(int queryStart, int queryEnd) {
        // Check for invalid input
        if (queryStart < 0 || queryEnd >= n || queryStart > queryEnd) {
            throw new IllegalArgumentException("Invalid range query");
        }
        
        return getSumUtil(0, n - 1, queryStart, queryEnd, 0);
    }
    
    /**
     * Helper method to get sum in range [queryStart...queryEnd]
     */
    private int getSumUtil(int start, int end, int queryStart, int queryEnd, int treeNode) {
        // If segment of this node is completely inside the given range
        if (queryStart <= start && queryEnd >= end) {
            return tree[treeNode];
        }
        
        // If segment of this node is outside the given range
        if (end < queryStart || start > queryEnd) {
            return 0;
        }
        
        // If segment of this node is partially inside and partially outside the given range
        int mid = start + (end - start) / 2;
        int leftSum = getSumUtil(start, mid, queryStart, queryEnd, 2 * treeNode + 1);
        int rightSum = getSumUtil(mid + 1, end, queryStart, queryEnd, 2 * treeNode + 2);
        
        return leftSum + rightSum;
    }
    
    /**
     * Updates value at index 'idx' to 'newValue'
     */
    public void update(int idx, int newValue) {
        // Check for invalid input
        if (idx < 0 || idx >= n) {
            throw new IllegalArgumentException("Invalid index");
        }
        
        updateUtil(0, n - 1, idx, newValue, 0);
    }
    
    /**
     * Helper method to update value at index 'idx' to 'newValue'
     */
    private void updateUtil(int start, int end, int idx, int newValue, int treeNode) {
        // If leaf node (base case)
        if (start == end) {
            tree[treeNode] = newValue;
            return;
        }
        
        // If idx is not in this range
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            updateUtil(start, mid, idx, newValue, 2 * treeNode + 1);
        } else {
            updateUtil(mid + 1, end, idx, newValue, 2 * treeNode + 2);
        }
        
        // Update the parent node after child is updated
        tree[treeNode] = tree[2 * treeNode + 1] + tree[2 * treeNode + 2];
    }
    
    /**
     * Example usage of Segment Tree for range sum queries
     */
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        SegmentTree segTree = new SegmentTree(arr);
        
        System.out.println("Sum of values in range [1, 3]: " + segTree.getSum(1, 3)); // 15
        
        // Update element at index 1 to 10
        segTree.update(1, 10);
        
        System.out.println("Updated sum of values in range [1, 3]: " + segTree.getSum(1, 3)); // 22
        
        // Query for different ranges
        System.out.println("Sum of values in range [0, 5]: " + segTree.getSum(0, 5)); // 43
        System.out.println("Sum of values in range [2, 5]: " + segTree.getSum(2, 5)); // 32
    }
}
