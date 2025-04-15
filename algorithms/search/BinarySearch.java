/**
 * Implementation of the Binary Search algorithm.
 * 
 * Binary Search is an efficient algorithm for finding a target value within a sorted array.
 * It works by repeatedly dividing the search interval in half.
 * 
 * Time Complexity:
 * - Best case: O(1) - Element found in the first comparison
 * - Average case: O(log n)
 * - Worst case: O(log n)
 * 
 * Space Complexity:
 * - Iterative approach: O(1)
 * - Recursive approach: O(log n) due to recursion stack
 */
public class BinarySearch {

    /**
     * Iterative implementation of Binary Search
     * @param arr Sorted array to search in
     * @param target Element to search for
     * @return Index of the target if found, -1 otherwise
     */
    public static <T extends Comparable<T>> int iterativeSearch(T[] arr, T target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            // Calculate middle index (avoiding integer overflow)
            int mid = left + (right - left) / 2;
            
            // Check if target is present at mid
            int comparisonResult = target.compareTo(arr[mid]);
            
            if (comparisonResult == 0) {
                return mid; // Element found
            } else if (comparisonResult < 0) {
                right = mid - 1; // Look in the left half
            } else {
                left = mid + 1; // Look in the right half
            }
        }
        
        return -1; // Element not found
    }
    
    /**
     * Recursive implementation of Binary Search
     * @param arr Sorted array to search in
     * @param target Element to search for
     * @return Index of the target if found, -1 otherwise
     */
    public static <T extends Comparable<T>> int recursiveSearch(T[] arr, T target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        return recursiveSearchHelper(arr, target, 0, arr.length - 1);
    }
    
    /**
     * Helper method for recursive binary search
     * @param arr Sorted array to search in
     * @param target Element to search for
     * @param left Left boundary index
     * @param right Right boundary index
     * @return Index of the target if found, -1 otherwise
     */
    private static <T extends Comparable<T>> int recursiveSearchHelper(T[] arr, T target, int left, int right) {
        // Base case: element not found
        if (left > right) {
            return -1;
        }
        
        // Calculate middle index (avoiding integer overflow)
        int mid = left + (right - left) / 2;
        
        // Check if target is present at mid
        int comparisonResult = target.compareTo(arr[mid]);
        
        if (comparisonResult == 0) {
            return mid; // Element found
        } else if (comparisonResult < 0) {
            // Look in the left half
            return recursiveSearchHelper(arr, target, left, mid - 1);
        } else {
            // Look in the right half
            return recursiveSearchHelper(arr, target, mid + 1, right);
        }
    }
    
    /**
     * Find the first occurrence of an element in a sorted array with duplicates
     * @param arr Sorted array to search in
     * @param target Element to search for
     * @return Index of the first occurrence of target if found, -1 otherwise
     */
    public static <T extends Comparable<T>> int findFirstOccurrence(T[] arr, T target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            // Calculate middle index (avoiding integer overflow)
            int mid = left + (right - left) / 2;
            
            // Check if target is present at mid
            int comparisonResult = target.compareTo(arr[mid]);
            
            if (comparisonResult == 0) {
                result = mid; // Found an occurrence, but continue searching to the left
                right = mid - 1;
            } else if (comparisonResult < 0) {
                right = mid - 1; // Look in the left half
            } else {
                left = mid + 1; // Look in the right half
            }
        }
        
        return result;
    }
    
    /**
     * Find the last occurrence of an element in a sorted array with duplicates
     * @param arr Sorted array to search in
     * @param target Element to search for
     * @return Index of the last occurrence of target if found, -1 otherwise
     */
    public static <T extends Comparable<T>> int findLastOccurrence(T[] arr, T target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            // Calculate middle index (avoiding integer overflow)
            int mid = left + (right - left) / 2;
            
            // Check if target is present at mid
            int comparisonResult = target.compareTo(arr[mid]);
            
            if (comparisonResult == 0) {
                result = mid; // Found an occurrence, but continue searching to the right
                left = mid + 1;
            } else if (comparisonResult < 0) {
                right = mid - 1; // Look in the left half
            } else {
                left = mid + 1; // Look in the right half
            }
        }
        
        return result;
    }
    
    /**
     * Find the element in a sorted array that is closest to the target
     * @param arr Sorted array to search in
     * @param target Element to find closest value to
     * @return Index of the element closest to target
     */
    public static <T extends Comparable<T> & Number> int findClosest(T[] arr, T target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        // If target is less than the first element
        if (target.compareTo(arr[0]) <= 0) {
            return 0;
        }
        
        // If target is greater than the last element
        if (target.compareTo(arr[arr.length - 1]) >= 0) {
            return arr.length - 1;
        }
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            // Calculate middle index (avoiding integer overflow)
            int mid = left + (right - left) / 2;
            
            // Check if target is present at mid
            int comparisonResult = target.compareTo(arr[mid]);
            
            if (comparisonResult == 0) {
                return mid; // Element found exactly
            } else if (comparisonResult < 0) {
                right = mid - 1; // Look in the left half
            } else {
                left = mid + 1; // Look in the right half
            }
        }
        
        // At this point, left > right
        // Return the closest element
        double diff1 = Math.abs(target.doubleValue() - arr[left].doubleValue());
        double diff2 = Math.abs(target.doubleValue() - arr[right].doubleValue());
        
        return (diff1 <= diff2) ? left : right;
    }
    
    /**
     * Search in a rotated sorted array
     * @param arr Rotated sorted array
     * @param target Element to search for
     * @return Index of target if found, -1 otherwise
     */
    public static <T extends Comparable<T>> int searchInRotatedArray(T[] arr, T target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid].compareTo(target) == 0) {
                return mid; // Element found
            }
            
            // Check if the left half is sorted
            if (arr[left].compareTo(arr[mid]) <= 0) {
                // Check if target is in the left half
                if (arr[left].compareTo(target) <= 0 && target.compareTo(arr[mid]) < 0) {
                    right = mid - 1; // Search left half
                } else {
                    left = mid + 1; // Search right half
                }
            }
            // Right half is sorted
            else {
                // Check if target is in the right half
                if (arr[mid].compareTo(target) < 0 && target.compareTo(arr[right]) <= 0) {
                    left = mid + 1; // Search right half
                } else {
                    right = mid - 1; // Search left half
                }
            }
        }
        
        return -1; // Element not found
    }
    
    /**
     * Main method with examples on how to use Binary Search
     */
    public static void main(String[] args) {
        // Example with sorted integers
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        System.out.println("Array: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
        
        // Iterative Binary Search
        int targetIndex = iterativeSearch(arr, 7);
        System.out.println("Iterative Search - Index of 7: " + targetIndex);
        
        // Recursive Binary Search
        targetIndex = recursiveSearch(arr, 3);
        System.out.println("Recursive Search - Index of 3: " + targetIndex);
        
        // Element not in array
        targetIndex = iterativeSearch(arr, 11);
        System.out.println("Search for 11 (not in array): " + targetIndex);
        
        // Example with duplicates
        Integer[] arrWithDuplicates = {1, 2, 2, 2, 3, 3, 4, 5, 5, 6};
        
        System.out.println("\nArray with duplicates: [1, 2, 2, 2, 3, 3, 4, 5, 5, 6]");
        
        // Find first occurrence
        int firstIndex = findFirstOccurrence(arrWithDuplicates, 2);
        System.out.println("First occurrence of 2: " + firstIndex);
        
        // Find last occurrence
        int lastIndex = findLastOccurrence(arrWithDuplicates, 2);
        System.out.println("Last occurrence of 2: " + lastIndex);
        
        // Find closest element
        int closestIndex = findClosest(arr, 7);
        System.out.println("\nElement closest to 7: " + arr[closestIndex]);
        
        closestIndex = findClosest(arr, 4);
        System.out.println("Element closest to 4: " + arr[closestIndex]);
        
        Double[] doubleArr = {1.1, 2.2, 3.3, 4.4, 5.5};
        int closestToThree = findClosest(doubleArr, 3.0);
        System.out.println("Element closest to 3.0 in [1.1, 2.2, 3.3, 4.4, 5.5]: " + doubleArr[closestToThree]);
        
        // Example with rotated sorted array
        Integer[] rotatedArr = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("\nRotated Array: [4, 5, 6, 7, 0, 1, 2]");
        
        int rotatedIndex = searchInRotatedArray(rotatedArr, 0);
        System.out.println("Index of 0 in rotated array: " + rotatedIndex);
        
        rotatedIndex = searchInRotatedArray(rotatedArr, 3);
        System.out.println("Index of 3 (not in array): " + rotatedIndex);
    }
}
