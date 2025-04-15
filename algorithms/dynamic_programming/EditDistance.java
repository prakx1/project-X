/**
 * Implementation of the Edit Distance (Levenshtein Distance) problem using Dynamic Programming.
 * 
 * The Edit Distance between two strings is the minimum number of operations required
 * to convert one string to another. The allowed operations are:
 * 1. Insert a character
 * 2. Delete a character
 * 3. Replace a character
 * 
 * Time Complexity: O(m*n) where m and n are the lengths of the input strings
 * Space Complexity: O(m*n) for the standard implementation, O(min(m,n)) for the optimized version
 * 
 * Common Interview Uses:
 * - Spell checkers
 * - DNA sequence alignment
 * - Plagiarism detection
 * - Autocorrect features
 */
public class EditDistance {
    
    /**
     * Calculates the edit distance between two strings using a 2D DP table
     * @param word1 first string
     * @param word2 second string
     * @return minimum number of operations required to convert word1 to word2
     */
    public static int minDistance(String word1, String word2) {
        // Handle edge cases
        if (word1 == null || word2 == null) {
            return 0;
        }
        
        if (word1.equals(word2)) {
            return 0;
        }
        
        int m = word1.length();
        int n = word2.length();
        
        // If one of the strings is empty, the distance is the length of the other string
        if (m == 0) return n;
        if (n == 0) return m;
        
        // Create a 2D DP table
        // dp[i][j] represents the edit distance between word1[0...i-1] and word2[0...j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Initialize first row and column
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // Converting word1[0...i-1] to empty string requires i deletions
        }
        
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j; // Converting empty string to word2[0...j-1] requires j insertions
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If current characters are the same, no operation needed
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Take the minimum of the three operations:
                    // 1. Replace: dp[i-1][j-1] + 1
                    // 2. Insert: dp[i][j-1] + 1
                    // 3. Delete: dp[i-1][j] + 1
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j]));
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Space-optimized version of the edit distance algorithm
     * This uses only O(min(m,n)) space
     * @param word1 first string
     * @param word2 second string
     * @return minimum number of operations required to convert word1 to word2
     */
    public static int minDistanceOptimized(String word1, String word2) {
        // Handle edge cases
        if (word1 == null || word2 == null) {
            return 0;
        }
        
        if (word1.equals(word2)) {
            return 0;
        }
        
        // Ensure word1 is the shorter string to optimize space
        if (word1.length() > word2.length()) {
            String temp = word1;
            word1 = word2;
            word2 = temp;
        }
        
        int m = word1.length();
        int n = word2.length();
        
        // If one of the strings is empty, the distance is the length of the other string
        if (m == 0) return n;
        
        // Use two arrays instead of a 2D table
        int[] prevRow = new int[m + 1];
        int[] currRow = new int[m + 1];
        
        // Initialize the first row
        for (int i = 0; i <= m; i++) {
            prevRow[i] = i;
        }
        
        // Fill the DP arrays
        for (int j = 1; j <= n; j++) {
            currRow[0] = j; // First element of current row
            
            for (int i = 1; i <= m; i++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    currRow[i] = prevRow[i - 1];
                } else {
                    currRow[i] = 1 + Math.min(prevRow[i - 1], Math.min(currRow[i - 1], prevRow[i]));
                }
            }
            
            // Swap rows for the next iteration
            int[] temp = prevRow;
            prevRow = currRow;
            currRow = temp;
        }
        
        return prevRow[m];
    }
    
    /**
     * Reconstructs the sequence of operations needed to transform word1 to word2
     * @param word1 first string
     * @param word2 second string
     * @return a list of operations represented as strings
     */
    public static String reconstructOperations(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return "Invalid input";
        }
        
        int m = word1.length();
        int n = word2.length();
        
        // Create a 2D DP table
        int[][] dp = new int[m + 1][n + 1];
        
        // Initialize first row and column
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j]));
                }
            }
        }
        
        // Reconstruct the path from bottom-right to top-left
        StringBuilder operations = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && word1.charAt(i - 1) == word2.charAt(j - 1)) {
                // Characters match, move diagonally
                operations.insert(0, "Keep " + word1.charAt(i - 1) + "\n");
                i--;
                j--;
            } else if (j > 0 && (i == 0 || dp[i][j] == dp[i][j - 1] + 1)) {
                // Insert operation
                operations.insert(0, "Insert " + word2.charAt(j - 1) + "\n");
                j--;
            } else if (i > 0 && (j == 0 || dp[i][j] == dp[i - 1][j] + 1)) {
                // Delete operation
                operations.insert(0, "Delete " + word1.charAt(i - 1) + "\n");
                i--;
            } else {
                // Replace operation
                operations.insert(0, "Replace " + word1.charAt(i - 1) + " with " + word2.charAt(j - 1) + "\n");
                i--;
                j--;
            }
        }
        
        return operations.toString();
    }
    
    /**
     * Example usage of Edit Distance algorithm
     */
    public static void main(String[] args) {
        // Example 1
        String word1 = "kitten";
        String word2 = "sitting";
        
        int distance = minDistance(word1, word2);
        int optimizedDistance = minDistanceOptimized(word1, word2);
        
        System.out.println("Edit distance between '" + word1 + "' and '" + word2 + "': " + distance);
        System.out.println("Edit distance (optimized): " + optimizedDistance);
        
        System.out.println("\nRequired operations:");
        System.out.println(reconstructOperations(word1, word2));
        
        // Example 2
        word1 = "sunday";
        word2 = "saturday";
        
        distance = minDistance(word1, word2);
        
        System.out.println("\nEdit distance between '" + word1 + "' and '" + word2 + "': " + distance);
        System.out.println("\nRequired operations:");
        System.out.println(reconstructOperations(word1, word2));
        
        // Example 3: Edge cases
        System.out.println("\nEdge Cases:");
        System.out.println("Empty string to 'abc': " + minDistance("", "abc"));
        System.out.println("'abc' to Empty string: " + minDistance("abc", ""));
        System.out.println("Identical strings 'abc' and 'abc': " + minDistance("abc", "abc"));
        
        // Performance comparison for larger strings
        String longWord1 = "pneumonoultramicroscopicsilicovolcanoconiosis";
        String longWord2 = "pseudopseudohypoparathyroidism";
        
        long startTime = System.currentTimeMillis();
        distance = minDistance(longWord1, longWord2);
        long endTime = System.currentTimeMillis();
        
        System.out.println("\nPerformance Comparison:");
        System.out.println("Standard DP: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        optimizedDistance = minDistanceOptimized(longWord1, longWord2);
        endTime = System.currentTimeMillis();
        
        System.out.println("Optimized DP: " + (endTime - startTime) + "ms");
        System.out.println("Edit distance: " + distance + " (should match: " + optimizedDistance + ")");
    }
}
