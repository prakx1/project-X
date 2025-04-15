import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Rabin-Karp string matching algorithm.
 * 
 * Rabin-Karp algorithm uses hashing to find an exact match of a pattern string in a text.
 * It is particularly useful when searching for multiple patterns in a text.
 * 
 * Time Complexity: 
 * - Average and best case: O(n + m) where n is text length and m is pattern length
 * - Worst case: O(n*m) but rare in practice
 * 
 * Space Complexity: O(1) for basic implementation
 * 
 * Common Interview Uses:
 * - String matching and substring search
 * - Plagiarism detection
 * - Multiple pattern matching
 */
public class RabinKarpStringMatching {
    
    // A large prime number used for hash calculation to avoid overflow
    private static final int PRIME = 101;
    
    /**
     * Searches for all occurrences of a pattern in text using Rabin-Karp algorithm
     * @param text the text to search in
     * @param pattern the pattern to search for
     * @return list of starting indices where pattern occurs in text
     */
    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        
        if (text == null || pattern == null || pattern.length() > text.length()) {
            return matches;
        }
        
        int n = text.length();
        int m = pattern.length();
        
        // Calculate d^(m-1) for use in rolling hash
        // d is the number of characters in the alphabet
        int d = 256; // ASCII character set
        int h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % PRIME;
        }
        
        // Calculate initial hash values for pattern and first window of text
        int patternHash = 0;
        int textHash = 0;
        
        for (int i = 0; i < m; i++) {
            patternHash = (d * patternHash + pattern.charAt(i)) % PRIME;
            textHash = (d * textHash + text.charAt(i)) % PRIME;
        }
        
        // Slide the pattern over text one by one
        for (int i = 0; i <= n - m; i++) {
            // Check if hash values match
            if (patternHash == textHash) {
                // Check for actual match character by character
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                
                if (match) {
                    matches.add(i);
                }
            }
            
            // Calculate hash value for next window of text
            if (i < n - m) {
                // Remove leading digit, add trailing digit
                textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % PRIME;
                
                // Handle negative hash values
                if (textHash < 0) {
                    textHash += PRIME;
                }
            }
        }
        
        return matches;
    }
    
    /**
     * Implementation optimized for multiple pattern search
     * @param text the text to search in
     * @param patterns array of patterns to search for
     * @return map of patterns to their occurrences
     */
    public static List<PatternMatch> searchMultiplePatterns(String text, String[] patterns) {
        List<PatternMatch> allMatches = new ArrayList<>();
        
        if (text == null || patterns == null || patterns.length == 0) {
            return allMatches;
        }
        
        for (String pattern : patterns) {
            List<Integer> occurrences = search(text, pattern);
            if (!occurrences.isEmpty()) {
                allMatches.add(new PatternMatch(pattern, occurrences));
            }
        }
        
        return allMatches;
    }
    
    /**
     * Helper class to store pattern matches
     */
    public static class PatternMatch {
        public String pattern;
        public List<Integer> positions;
        
        public PatternMatch(String pattern, List<Integer> positions) {
            this.pattern = pattern;
            this.positions = positions;
        }
        
        @Override
        public String toString() {
            return "Pattern: \"" + pattern + "\" found at positions: " + positions;
        }
    }
    
    /**
     * Example usage of Rabin-Karp algorithm
     */
    public static void main(String[] args) {
        // Single pattern example
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        
        List<Integer> matches = search(text, pattern);
        
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        if (matches.isEmpty()) {
            System.out.println("Pattern not found in text");
        } else {
            System.out.println("Pattern found at positions: " + matches);
        }
        
        // Multiple pattern example
        String[] patterns = {"ABAB", "DAB", "CAB"};
        List<PatternMatch> multiMatches = searchMultiplePatterns(text, patterns);
        
        System.out.println("\nMultiple pattern search results:");
        for (PatternMatch match : multiMatches) {
            System.out.println(match);
        }
        
        // Performance comparison with naive search
        String longText = generateRandomString(10000);
        String shortPattern = generateRandomString(5);
        
        long startTime = System.currentTimeMillis();
        List<Integer> rabinKarpMatches = search(longText, shortPattern);
        long endTime = System.currentTimeMillis();
        
        System.out.println("\nPerformance test:");
        System.out.println("Rabin-Karp found " + rabinKarpMatches.size() + 
                           " matches in " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        List<Integer> naiveMatches = naiveSearch(longText, shortPattern);
        endTime = System.currentTimeMillis();
        
        System.out.println("Naive search found " + naiveMatches.size() + 
                           " matches in " + (endTime - startTime) + "ms");
    }
    
    /**
     * Naive string matching algorithm for comparison
     */
    private static List<Integer> naiveSearch(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        
        int n = text.length();
        int m = pattern.length();
        
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            
            if (j == m) {
                matches.add(i);
            }
        }
        
        return matches;
    }
    
    /**
     * Helper method to generate random string for testing
     */
    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) ('A' + (int) (Math.random() * 26));
            sb.append(c);
        }
        return sb.toString();
    }
}
