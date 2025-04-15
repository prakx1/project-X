import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of the N-Queens problem using Backtracking.
 * The N-Queens problem is placing N queens on an N×N chessboard 
 * such that no two queens attack each other.
 *
 * Time Complexity: O(N!), where N is the board size
 * Space Complexity: O(N) for the recursive call stack, plus O(N²) for storing solutions
 * 
 * Common Interview Use:
 * - Demonstrates understanding of backtracking algorithm
 * - Tests ability to prune search tree efficiently
 * - Checks understanding of recursive solutions
 */
public class NQueens {
    
    /**
     * Solves the N-Queens problem and returns all possible solutions
     * @param n size of the board
     * @return list of all solutions, each represented as a list of strings
     */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> results = new ArrayList<>();
        if (n <= 0) {
            return results;
        }
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        backtrack(board, 0, results);
        return results;
    }
    
    /**
     * Backtracking function to place queens row by row
     * @param board current board state
     * @param row current row to place a queen
     * @param results list to store all valid solutions
     */
    private static void backtrack(char[][] board, int row, List<List<String>> results) {
        // Base case: If all queens are placed, add solution to results
        if (row == board.length) {
            results.add(constructSolution(board));
            return;
        }
        
        // Try placing queen in each column of current row
        for (int col = 0; col < board.length; col++) {
            if (isValid(board, row, col)) {
                // Place queen
                board[row][col] = 'Q';
                
                // Recur for next row
                backtrack(board, row + 1, results);
                
                // Backtrack (remove queen)
                board[row][col] = '.';
            }
        }
    }
    
    /**
     * Checks if placing a queen at position (row, col) is valid
     * @param board current board state
     * @param row row to check
     * @param col column to check
     * @return true if placement is valid, false otherwise
     */
    private static boolean isValid(char[][] board, int row, int col) {
        int n = board.length;
        
        // Check column (no need to check row as we place one queen per row)
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }
        
        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        
        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Converts board representation to list of strings
     * @param board current board state
     * @return list of strings representation
     */
    private static List<String> constructSolution(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }
    
    /**
     * Optimized version using sets to track occupied positions
     * This is more efficient for larger board sizes
     * @param n size of the board
     * @return list of all solutions, each represented as a list of strings
     */
    public static List<List<String>> solveNQueensOptimized(int n) {
        List<List<String>> results = new ArrayList<>();
        if (n <= 0) {
            return results;
        }
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        // Arrays to track occupied positions
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1]; // top-left to bottom-right diagonals
        boolean[] diag2 = new boolean[2 * n - 1]; // top-right to bottom-left diagonals
        
        backtrackOptimized(board, 0, cols, diag1, diag2, results);
        return results;
    }
    
    /**
     * Optimized backtracking function using sets
     */
    private static void backtrackOptimized(char[][] board, int row, boolean[] cols, 
                                         boolean[] diag1, boolean[] diag2, 
                                         List<List<String>> results) {
        if (row == board.length) {
            results.add(constructSolution(board));
            return;
        }
        
        for (int col = 0; col < board.length; col++) {
            // Calculate diagonal indices
            int d1 = row - col + board.length - 1; // top-right to bottom-left
            int d2 = row + col; // top-left to bottom-right
            
            // Check if position is valid
            if (!cols[col] && !diag1[d1] && !diag2[d2]) {
                // Place queen and mark positions
                board[row][col] = 'Q';
                cols[col] = true;
                diag1[d1] = true;
                diag2[d2] = true;
                
                // Recur for next row
                backtrackOptimized(board, row + 1, cols, diag1, diag2, results);
                
                // Backtrack
                board[row][col] = '.';
                cols[col] = false;
                diag1[d1] = false;
                diag2[d2] = false;
            }
        }
    }
    
    /**
     * Pretty print a board configuration
     */
    private static void printBoard(List<String> board) {
        for (String row : board) {
            System.out.println(row);
        }
        System.out.println();
    }
    
    /**
     * Example usage of N-Queens solver
     */
    public static void main(String[] args) {
        // Small board for demonstration
        int n = 4;
        System.out.println("Solutions for " + n + "-Queens problem:");
        
        List<List<String>> solutions = solveNQueens(n);
        System.out.println("Total solutions: " + solutions.size());
        
        // Print all solutions
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("Solution " + (i + 1) + ":");
            printBoard(solutions.get(i));
        }
        
        // Compare with optimized version
        List<List<String>> optimizedSolutions = solveNQueensOptimized(n);
        System.out.println("Total solutions (optimized): " + optimizedSolutions.size());
        
        // Performance test with larger board
        n = 8;
        long startTime = System.currentTimeMillis();
        int numSolutions = solveNQueensOptimized(n).size();
        long endTime = System.currentTimeMillis();
        
        System.out.println("\nFor " + n + "-Queens problem:");
        System.out.println("Number of solutions: " + numSolutions);
        System.out.println("Time taken (optimized): " + (endTime - startTime) + "ms");
    }
}
