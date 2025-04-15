/**
 * Implementation of Knuth-Morris-Pratt (KMP) string matching algorithm.
 * 
 * KMP is an efficient string searching algorithm that uses information about 
 * the pattern itself to minimize comparisons.
 * 
 * Time Complexity: O(n + m) where n is text length and m is pattern length
 * Space Complexity: O(m) for the LPS (Longest Prefix Suffix) array
 * 
 * Common Interview Uses:
 * - Pattern matching in strings
 * - Finding all occurrences of a pattern in text
 * - Implementing text editors' "find" functionality
 */
public class KMPStringMatching {
    
    /**
     * Searches for all occurrences of pattern in text using KMP algorithm
     * @param text the main text
     * @param pattern the pattern to search for
     * @return array of starting indices of all matches
     */
    public static int[] search(String text, String pattern) {
        if (text == null || pattern == null || pattern.length() > text.length()) {
            return new int[0];
        }
        
        int n = text.length();
        int m = pattern.length();
        
        // Preprocess pattern to compute LPS array
        int[] lps = computeLPSArray(pattern);
        
        // Variables to track matches
        int i = 0; // index for text
        int j = 0; // index for pattern
        
        // Array to store all starting positions of matches
        int[] matches = new int[n]; // Worst case: all positions match
        int matchCount = 0;
        
        while (i < n) {
            // Current characters match
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            
            // Full pattern matched
            if (j == m) {
                matches[matchCount++] = i - j; // Store the starting index
                j = lps[j - 1]; // Look for next match
            } 
            // Characters don't match
            else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1]; // Use LPS to skip comparisons
                } else {
                    i++; // No potential prefix, move to next character in text
                }
            }
        }
        
        // Create result array with exact size
        int[] result = new int[matchCount];
        System.arraycopy(matches, 0, result, 0, matchCount);
        return result;
    }
    
    /**
     * Computes Longest Prefix Suffix (LPS) array for the pattern
     * lps[i] = length of the longest proper prefix of pattern[0...i]
     *          that is also a suffix of pattern[0...i]
     * @param pattern the pattern string
     * @return LPS array
     */
    private static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        
        // Length of the previous longest prefix & suffix
        int len = 0;
        int i = 1;
        
        // lps[0] is always 0
        lps[0] = 0;
        
        // Calculate lps[i] for i = 1 to m-1
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // Use the value computed earlier to avoid matching characters 
                    // that we know will match
                    len = lps[len - 1];
                } else {
                    // No proper prefix found
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        return lps;
    }
    
    /**
     * Example usage of KMP algorithm
     */
    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        
        int[] matches = search(text, pattern);
        
        if (matches.length == 0) {
            System.out.println("Pattern not found in text");
        } else {
            System.out.println("Pattern found at indices:");
            for (int idx : matches) {
                System.out.println(idx);
            }
        }
        
        // Another example
        text = "AAAAABAAABA";
        pattern = "AAAA";
        matches = search(text, pattern);
        
        System.out.println("\nPattern '" + pattern + "' found at indices:");
        for (int idx : matches) {
            System.out.println(idx);
        }
        
        // Edge cases
        System.out.println("\nEdge cases:");
        
        // Empty pattern
        matches = search("Hello, world!", "");
        System.out.println("Empty pattern matches: " + matches.length);
        
        // Pattern longer than text
        matches = search("abc", "abcdef");
        System.out.println("Pattern longer than text matches: " + matches.length);
    }
}
