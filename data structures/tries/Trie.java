/**
 * Implementation of a Trie (Prefix Tree) data structure.
 * 
 * A Trie is a tree-like data structure that is used to store a dynamic set of strings.
 * Tries are commonly used for efficient retrieval of keys in a dataset of strings,
 * especially for autocomplete, spell-checking, and prefix-based searches.
 * 
 * Time Complexity:
 * - Insert: O(m) where m is the length of the key
 * - Search: O(m) where m is the length of the key
 * - Delete: O(m) where m is the length of the key
 * - Prefix Search: O(p + n) where p is the length of the prefix and n is the number of words with that prefix
 * 
 * Space Complexity: O(ALPHABET_SIZE * m * n) where m is the average length of words and n is the number of words
 */
import java.util.*;

public class Trie {
    
    // Node class for Trie
    private static class TrieNode {
        private TrieNode[] children;
        private boolean isEndOfWord;
        private int count; // Count of words with this prefix
        
        // Constructor
        public TrieNode() {
            children = new TrieNode[26]; // English lowercase letters only
            isEndOfWord = false;
            count = 0;
        }
    }
    
    private final TrieNode root;
    
    /**
     * Constructor to initialize the Trie.
     */
    public Trie() {
        root = new TrieNode();
    }
    
    /**
     * Inserts a word into the trie.
     * 
     * @param word the word to insert
     */
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        
        TrieNode current = root;
        
        // Convert to lowercase to ensure consistency
        word = word.toLowerCase();
        
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            
            // Handle only English lowercase letters
            if (index < 0 || index >= 26) {
                continue;
            }
            
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            
            current = current.children[index];
            current.count++; // Increment count for this prefix
        }
        
        current.isEndOfWord = true;
    }
    
    /**
     * Searches for a word in the trie.
     * 
     * @param word the word to search for
     * @return true if the word is found, false otherwise
     */
    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        TrieNode node = searchNode(word.toLowerCase());
        return node != null && node.isEndOfWord;
    }
    
    /**
     * Checks if there is any word in the trie that starts with the given prefix.
     * 
     * @param prefix the prefix to check
     * @return true if there is a word with the given prefix, false otherwise
     */
    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return false;
        }
        
        TrieNode node = searchNode(prefix.toLowerCase());
        return node != null;
    }
    
    /**
     * Counts the number of words that start with the given prefix.
     * 
     * @param prefix the prefix to count
     * @return the number of words with the given prefix
     */
    public int countWordsWithPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return 0;
        }
        
        TrieNode node = searchNode(prefix.toLowerCase());
        return node != null ? node.count : 0;
    }
    
    /**
     * Deletes a word from the trie.
     * 
     * @param word the word to delete
     * @return true if the word was deleted, false if it doesn't exist
     */
    public boolean delete(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        return delete(root, word.toLowerCase(), 0);
    }
    
    /**
     * Helper method to find a node in the trie that corresponds to the given word.
     * 
     * @param word the word to search for
     * @return the node corresponding to the last character of the word, or null if not found
     */
    private TrieNode searchNode(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            
            // Handle only English lowercase letters
            if (index < 0 || index >= 26) {
                return null;
            }
            
            if (current.children[index] == null) {
                return null;
            }
            
            current = current.children[index];
        }
        
        return current;
    }
    
    /**
     * Helper method for recursive deletion of a word from the trie.
     * 
     * @param current the current node
     * @param word the word to delete
     * @param depth the current depth in the trie
     * @return true if the word was deleted, false otherwise
     */
    private boolean delete(TrieNode current, String word, int depth) {
        // If we've reached the end of the word
        if (depth == word.length()) {
            // Word not found
            if (!current.isEndOfWord) {
                return false;
            }
            
            // Mark as not end of word
            current.isEndOfWord = false;
            
            // Return true if this node has no children, indicating it can be deleted
            return isEmpty(current);
        }
        
        char c = word.charAt(depth);
        int index = c - 'a';
        
        // If character is not in the valid range or node doesn't exist
        if (index < 0 || index >= 26 || current.children[index] == null) {
            return false;
        }
        
        // Recursively delete from the child node
        boolean shouldDeleteChild = delete(current.children[index], word, depth + 1);
        
        // If child can be deleted
        if (shouldDeleteChild) {
            current.children[index] = null;
            current.count--; // Decrement count for this prefix
            
            // Return true if this node can also be deleted
            return !current.isEndOfWord && isEmpty(current);
        }
        
        current.count--; // Decrement count for this prefix
        return false;
    }
    
    /**
     * Checks if a node has any children.
     * 
     * @param node the node to check
     * @return true if the node has no children, false otherwise
     */
    private boolean isEmpty(TrieNode node) {
        for (TrieNode child : node.children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Gets all words in the trie with the given prefix.
     * 
     * @param prefix the prefix to search for
     * @return a list of words with the given prefix
     */
    public List<String> getWordsWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        
        if (prefix == null) {
            return result;
        }
        
        prefix = prefix.toLowerCase();
        TrieNode prefixNode = searchNode(prefix);
        
        if (prefixNode != null) {
            getWordsWithPrefix(prefixNode, prefix, result);
        }
        
        return result;
    }
    
    /**
     * Helper method to recursively get all words with a given prefix.
     * 
     * @param node the current node
     * @param prefix the current prefix
     * @param result the list to store words
     */
    private void getWordsWithPrefix(TrieNode node, String prefix, List<String> result) {
        if (node.isEndOfWord) {
            result.add(prefix);
        }
        
        for (char c = 'a'; c <= 'z'; c++) {
            int index = c - 'a';
            if (node.children[index] != null) {
                getWordsWithPrefix(node.children[index], prefix + c, result);
            }
        }
    }
    
    /**
     * Gets the longest common prefix of all words in the trie.
     * 
     * @return the longest common prefix
     */
    public String getLongestCommonPrefix() {
        StringBuilder prefix = new StringBuilder();
        TrieNode current = root;
        
        while (true) {
            // Count non-null children
            int nonNullCount = 0;
            int nonNullIndex = -1;
            
            for (int i = 0; i < 26; i++) {
                if (current.children[i] != null) {
                    nonNullCount++;
                    nonNullIndex = i;
                }
            }
            
            // If no children or more than one child, we've reached the end of common prefix
            if (nonNullCount != 1 || current.isEndOfWord) {
                break;
            }
            
            // Append the character and move to the next node
            prefix.append((char) (nonNullIndex + 'a'));
            current = current.children[nonNullIndex];
        }
        
        return prefix.toString();
    }
    
    /**
     * Returns all words in the trie.
     * 
     * @return a list of all words in the trie
     */
    public List<String> getAllWords() {
        return getWordsWithPrefix("");
    }
    
    /**
     * Main method with examples on how to use the Trie
     */
    public static void main(String[] args) {
        // Example usage
        Trie trie = new Trie();
        
        // Insert words
        trie.insert("apple");
        trie.insert("application");
        trie.insert("appreciate");
        trie.insert("book");
        trie.insert("banana");
        trie.insert("ball");
        
        // Search for words
        System.out.println("Search for 'apple': " + trie.search("apple"));     // true
        System.out.println("Search for 'app': " + trie.search("app"));         // false
        System.out.println("Search for 'ball': " + trie.search("ball"));       // true
        System.out.println("Search for 'balloon': " + trie.search("balloon")); // false
        
        // Check if words start with a prefix
        System.out.println("\nPrefix 'app' exists: " + trie.startsWith("app"));    // true
        System.out.println("Prefix 'ban' exists: " + trie.startsWith("ban"));      // true
        System.out.println("Prefix 'car' exists: " + trie.startsWith("car"));      // false
        
        // Count words with prefix
        System.out.println("\nWords with prefix 'app': " + trie.countWordsWithPrefix("app"));   // 3
        System.out.println("Words with prefix 'ba': " + trie.countWordsWithPrefix("ba"));       // 2
        System.out.println("Words with prefix 'b': " + trie.countWordsWithPrefix("b"));         // 3
        
        // Get words with prefix
        System.out.println("\nWords with prefix 'app': " + trie.getWordsWithPrefix("app"));
        System.out.println("Words with prefix 'ba': " + trie.getWordsWithPrefix("ba"));
        System.out.println("Words with prefix 'b': " + trie.getWordsWithPrefix("b"));
        
        // Get longest common prefix
        System.out.println("\nLongest common prefix: " + trie.getLongestCommonPrefix());
        
        // Delete a word
        System.out.println("\nDelete 'apple': " + trie.delete("apple"));   // true
        System.out.println("Search for 'apple': " + trie.search("apple")); // false
        System.out.println("Prefix 'app' exists: " + trie.startsWith("app")); // true (since 'application' and 'appreciate' still exist)
        
        // Get all words after deletion
        System.out.println("\nAll words after deletion: " + trie.getAllWords());
        
        // Autocomplete example
        System.out.println("\nAutocomplete example:");
        Trie autocomplete = new Trie();
        String[] words = {"hello", "help", "helper", "helpful", "helping", "hell", "helmet", "helium"};
        
        for (String word : words) {
            autocomplete.insert(word);
        }
        
        System.out.println("Autocomplete for 'hel': " + autocomplete.getWordsWithPrefix("hel"));
        System.out.println("Autocomplete for 'help': " + autocomplete.getWordsWithPrefix("help"));
    }
}
