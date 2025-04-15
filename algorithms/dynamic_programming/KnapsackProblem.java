/**
 * Implementation of the 0/1 Knapsack Problem using Dynamic Programming.
 * 
 * The 0/1 Knapsack Problem: Given weights and values of n items, put these items in a knapsack
 * of capacity W to get the maximum total value. You cannot break an item, either pick it completely
 * or don't pick it (0/1 property).
 * 
 * Time Complexity: O(n * W) where n is the number of items and W is the capacity of the knapsack
 * Space Complexity: O(n * W)
 */
public class KnapsackProblem {

    /**
     * Solves the 0/1 Knapsack problem using bottom-up dynamic programming
     * 
     * @param values array of values of each item
     * @param weights array of weights of each item
     * @param capacity maximum capacity of the knapsack
     * @return maximum value that can be put in the knapsack
     */
    public static int knapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;
        
        // Input validation
        if (n == 0 || capacity <= 0 || weights.length != n) {
            return 0;
        }
        
        // Create a 2D table for memoization
        // dp[i][j] represents the maximum value that can be obtained 
        // with i items and j capacity
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Fill the dp table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    // Base case: no items or no capacity
                    dp[i][w] = 0;
                } else if (weights[i - 1] <= w) {
                    // Current item can fit in the knapsack
                    // Take maximum of including or excluding the current item
                    dp[i][w] = Math.max(
                        values[i - 1] + dp[i - 1][w - weights[i - 1]],  // Include current item
                        dp[i - 1][w]  // Exclude current item
                    );
                } else {
                    // Current item cannot fit in the knapsack, so exclude it
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        
        return dp[n][capacity];
    }
    
    /**
     * Solves the 0/1 Knapsack problem using bottom-up dynamic programming with space optimization
     * 
     * @param values array of values of each item
     * @param weights array of weights of each item
     * @param capacity maximum capacity of the knapsack
     * @return maximum value that can be put in the knapsack
     */
    public static int knapsackOptimized(int[] values, int[] weights, int capacity) {
        int n = values.length;
        
        // Input validation
        if (n == 0 || capacity <= 0 || weights.length != n) {
            return 0;
        }
        
        // Since we only need the previous row to calculate the current row,
        // we can optimize space by using a 1D array
        int[] dp = new int[capacity + 1];
        
        // Fill the dp array
        for (int i = 0; i < n; i++) {
            // Process the items in reverse order to avoid using the updated values
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
            }
        }
        
        return dp[capacity];
    }
    
    /**
     * Solves the 0/1 Knapsack problem and also returns the selected items
     * 
     * @param values array of values of each item
     * @param weights array of weights of each item
     * @param capacity maximum capacity of the knapsack
     * @return array containing [maximum value, array of selected items]
     */
    public static Object[] knapsackWithItems(int[] values, int[] weights, int capacity) {
        int n = values.length;
        
        // Input validation
        if (n == 0 || capacity <= 0 || weights.length != n) {
            return new Object[]{0, new boolean[0]};
        }
        
        // Create a 2D table for memoization
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Fill the dp table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        values[i - 1] + dp[i - 1][w - weights[i - 1]],
                        dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        
        // Reconstruct the solution (which items were selected)
        boolean[] selectedItems = new boolean[n];
        int w = capacity;
        
        for (int i = n; i > 0 && w > 0; i--) {
            // If the value comes from including this item
            if (dp[i][w] != dp[i - 1][w]) {
                selectedItems[i - 1] = true;
                w -= weights[i - 1];
            }
        }
        
        return new Object[]{dp[n][capacity], selectedItems};
    }
    
    /**
     * Solves the Unbounded Knapsack problem where we can use an item multiple times
     * 
     * @param values array of values of each item
     * @param weights array of weights of each item
     * @param capacity maximum capacity of the knapsack
     * @return maximum value that can be put in the knapsack
     */
    public static int unboundedKnapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;
        
        // Input validation
        if (n == 0 || capacity <= 0 || weights.length != n) {
            return 0;
        }
        
        // Create a 1D array for memoization
        // dp[i] represents the maximum value with capacity i
        int[] dp = new int[capacity + 1];
        
        // Fill the dp array
        for (int w = 0; w <= capacity; w++) {
            for (int i = 0; i < n; i++) {
                if (weights[i] <= w) {
                    dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
                }
            }
        }
        
        return dp[capacity];
    }
    
    /**
     * Solves the Fractional Knapsack problem where we can take fractions of items
     * 
     * @param values array of values of each item
     * @param weights array of weights of each item
     * @param capacity maximum capacity of the knapsack
     * @return maximum value that can be put in the knapsack
     */
    public static double fractionalKnapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;
        
        // Input validation
        if (n == 0 || capacity <= 0 || weights.length != n) {
            return 0;
        }
        
        // Create an array of items with value-to-weight ratio
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        
        // Sort items by value-to-weight ratio in descending order
        java.util.Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));
        
        double totalValue = 0;
        int remainingCapacity = capacity;
        
        // Greedy approach: Take items with highest value-to-weight ratio first
        for (Item item : items) {
            if (remainingCapacity >= item.weight) {
                // Take the whole item
                totalValue += item.value;
                remainingCapacity -= item.weight;
            } else {
                // Take a fraction of the item
                totalValue += item.value * ((double) remainingCapacity / item.weight);
                break;
            }
        }
        
        return totalValue;
    }
    
    /**
     * Helper class for fractional knapsack
     */
    private static class Item {
        int value;
        int weight;
        double ratio; // value-to-weight ratio
        
        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
            this.ratio = (double) value / weight;
        }
    }
    
    /**
     * Main method with examples on how to use the Knapsack algorithms
     */
    public static void main(String[] args) {
        // Example data
        int[] values = {60, 100, 120};
        int[] weights = {10, 20, 30};
        int capacity = 50;
        
        // 0/1 Knapsack
        int maxValue = knapsack(values, weights, capacity);
        System.out.println("0/1 Knapsack - Maximum value: " + maxValue);
        
        // 0/1 Knapsack with space optimization
        int maxValueOptimized = knapsackOptimized(values, weights, capacity);
        System.out.println("0/1 Knapsack (Optimized) - Maximum value: " + maxValueOptimized);
        
        // 0/1 Knapsack with item selection
        Object[] result = knapsackWithItems(values, weights, capacity);
        int maxValueWithItems = (int) result[0];
        boolean[] selectedItems = (boolean[]) result[1];
        
        System.out.println("0/1 Knapsack with item selection - Maximum value: " + maxValueWithItems);
        System.out.print("Selected items: ");
        for (int i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i]) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
        
        // Unbounded Knapsack
        int maxValueUnbounded = unboundedKnapsack(values, weights, capacity);
        System.out.println("Unbounded Knapsack - Maximum value: " + maxValueUnbounded);
        
        // Fractional Knapsack
        double maxValueFractional = fractionalKnapsack(values, weights, capacity);
        System.out.println("Fractional Knapsack - Maximum value: " + maxValueFractional);
        
        // Larger example
        int[] values2 = {10, 40, 30, 50, 35, 25, 15, 20};
        int[] weights2 = {5, 20, 15, 25, 17, 12, 8, 10};
        int capacity2 = 60;
        
        System.out.println("\nLarger example - 0/1 Knapsack:");
        Object[] result2 = knapsackWithItems(values2, weights2, capacity2);
        System.out.println("Maximum value: " + result2[0]);
        boolean[] selected = (boolean[]) result2[1];
        
        System.out.println("Selected items (value, weight):");
        int totalWeight = 0;
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                System.out.println("Item " + (i + 1) + ": (" + values2[i] + ", " + weights2[i] + ")");
                totalWeight += weights2[i];
            }
        }
        System.out.println("Total weight: " + totalWeight);
    }
}
