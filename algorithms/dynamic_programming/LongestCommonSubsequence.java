/**
 * Implementation of the Longest Common Subsequence (LCS) problem using Dynamic Programming.
 * 
 * The LCS problem is finding the longest subsequence common to two sequences.
 * A subsequence is a sequence that can be derived from another sequence
 * by deleting some or no elements without changing the order of the remaining elements.
 * 
 * Time Complexity: O(m*n) where m and n are the lengths of the input strings
 * Space Complexity: O(m*n)
 * 
 * Common Interview Uses:
 * - String similarity and difference detection
 * - DNA sequence alignment
 * - Version control systems for file comparison
 */
public class LongestCommonSubsequence {
    
    /**
     * Finds the length of the longest common subsequence of two strings
     * @param text1 first string
     * @param text2 second string
     * @return length of the longest common subsequence
     */
    public static int longestCommonSubsequenceLength(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // dp[i][j] represents the length of LCS for text1[0...i-1] and text2[0...j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Build the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // If current characters match, add 1 to the LCS length of previous characters
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // If current characters don't match, take the maximum LCS from either
                    // removing the current character from text1 or from text2
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Finds the actual longest common subsequence of two strings
     * @param text1 first string
     * @param text2 second string
     * @return the longest common subsequence as a string
     */
    public static String longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return "";
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // dp[i][j] represents the length of LCS for text1[0...i-1] and text2[0...j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Build the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Reconstruct the LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Current characters are part of LCS
                lcs.insert(0, text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Move in the direction of larger LCS
                i--;
            } else {
                j--;
            }
        }
        
        return lcs.toString();
    }
    
    /**
     * Space-optimized solution for finding the length of LCS
     * This reduces space complexity to O(min(m,n))
     */
    public static int longestCommonSubsequenceLengthOptimized(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        
        // Ensure text1 is the shorter string to optimize space
        if (text1.length() > text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // We only need two rows of the dp table
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[i] = prev[i - 1] + 1;
                } else {
                    curr[i] = Math.max(prev[i], curr[i - 1]);
                }
            }
            
            // Swap rows for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
            
            // Clear current row for next use
            for (int i = 0; i <= m; i++) {
                curr[i] = 0;
            }
        }
        
        return prev[m];
    }
    
    /**
     * Example usage of Longest Common Subsequence
     */
    public static void main(String[] args) {
        // Example 1
        String text1 = "ABCBDAB";
        String text2 = "BDCABA";
        
        int lcsLength = longestCommonSubsequenceLength(text1, text2);
        String lcs = longestCommonSubsequence(text1, text2);
        
        System.out.println("Text 1: " + text1);
        System.out.println("Text 2: " + text2);
        System.out.println("Length of LCS: " + lcsLength);
        System.out.println("LCS: " + lcs);
        
        // Verify with optimized solution
        int optimizedLength = longestCommonSubsequenceLengthOptimized(text1, text2);
        System.out.println("Length of LCS (optimized): " + optimizedLength);
        
        // Example 2
        text1 = "AGGTAB";
        text2 = "GXTXAYB";
        
        lcsLength = longestCommonSubsequenceLength(text1, text2);
        lcs = longestCommonSubsequence(text1, text2);
        
        System.out.println("\nText 1: " + text1);
        System.out.println("Text 2: " + text2);
        System.out.println("Length of LCS: " + lcsLength);
        System.out.println("LCS: " + lcs);
        
        // Example 3: Edge cases
        System.out.println("\nEdge Cases:");
        System.out.println("Empty strings: " + longestCommonSubsequenceLength("", "ABC"));
        System.out.println("No common characters: " + longestCommonSubsequenceLength("ABC", "DEF"));
        System.out.println("Identical strings: " + longestCommonSubsequenceLength("ABCD", "ABCD"));
    }
}
