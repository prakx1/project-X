/**
 * Implementation of the QuickSort algorithm.
 * 
 * QuickSort is a divide-and-conquer algorithm that works by:
 * 1. Selecting a 'pivot' element from the array
 * 2. Partitioning the array around the pivot (elements less than pivot go to left, greater go to right)
 * 3. Recursively applying the above steps to the sub-arrays
 * 
 * Time Complexity:
 * - Best case: O(n log n)
 * - Average case: O(n log n)
 * - Worst case: O(n²) - occurs when the pivot selection is poor (e.g., already sorted array)
 * 
 * Space Complexity: O(log n) for recursion stack
 */
public class QuickSort {

    /**
     * Sort an array using QuickSort algorithm
     * @param arr Array to be sorted
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Recursive QuickSort method
     * @param arr Array to be sorted
     * @param low Starting index
     * @param high Ending index
     */
    private static <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) {
            // Find pivot element such that
            // elements smaller than pivot are on the left
            // elements greater than pivot are on the right
            int pivotIndex = partition(arr, low, high);

            // Recursively sort elements before and after pivot
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Partition the array and return the pivot index
     * @param arr Array to be partitioned
     * @param low Starting index
     * @param high Ending index
     * @return Index of the pivot element
     */
    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        // Choose the rightmost element as pivot
        T pivot = arr[high];
        
        // Index of smaller element
        int i = low - 1;
        
        // Traverse the array and move elements smaller than pivot to the left
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr[j].compareTo(pivot) < 0) {
                i++;
                
                // Swap arr[i] and arr[j]
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Swap arr[i+1] and arr[high] (put the pivot in its correct position)
        T temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    /**
     * Optimized version of QuickSort using median-of-three pivot selection
     * to avoid worst-case behavior on already sorted arrays
     * @param arr Array to be sorted
     */
    public static <T extends Comparable<T>> void optimizedSort(T[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        optimizedQuickSort(arr, 0, arr.length - 1);
    }
    
    /**
     * Recursive method for optimized QuickSort
     * @param arr Array to be sorted
     * @param low Starting index
     * @param high Ending index
     */
    private static <T extends Comparable<T>> void optimizedQuickSort(T[] arr, int low, int high) {
        // Switch to insertion sort for small arrays (optimization)
        if (high - low < 10) {
            insertionSort(arr, low, high);
            return;
        }
        
        if (low < high) {
            // Use median-of-three pivot selection
            medianOfThree(arr, low, high);
            
            // Partition the array and get the pivot index
            int pivotIndex = optimizedPartition(arr, low, high);
            
            // Recursively sort elements before and after pivot
            optimizedQuickSort(arr, low, pivotIndex - 1);
            optimizedQuickSort(arr, pivotIndex + 1, high);
        }
    }
    
    /**
     * Median-of-three pivot selection: choose the median of the first, middle, and last elements
     * @param arr Array to find median in
     * @param low Starting index
     * @param high Ending index
     */
    private static <T extends Comparable<T>> void medianOfThree(T[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        
        // Sort the first, middle, and last elements
        if (arr[mid].compareTo(arr[low]) < 0) swap(arr, low, mid);
        if (arr[high].compareTo(arr[low]) < 0) swap(arr, low, high);
        if (arr[high].compareTo(arr[mid]) < 0) swap(arr, mid, high);
        
        // Place the pivot (median) at the end
        swap(arr, mid, high - 1);
    }
    
    /**
     * Optimized partition using the median-of-three pivot
     * @param arr Array to be partitioned
     * @param low Starting index
     * @param high Ending index
     * @return Index of the pivot element
     */
    private static <T extends Comparable<T>> int optimizedPartition(T[] arr, int low, int high) {
        // Pivot is at high-1 after median-of-three
        T pivot = arr[high - 1];
        int i = low;
        int j = high - 1;
        
        while (true) {
            // Move i right until we find an element >= pivot
            while (arr[++i].compareTo(pivot) < 0);
            
            // Move j left until we find an element <= pivot
            while (j > low && arr[--j].compareTo(pivot) > 0);
            
            if (i >= j) break;
            
            // Swap elements at i and j
            swap(arr, i, j);
        }
        
        // Put pivot in its final place
        swap(arr, i, high - 1);
        return i;
    }
    
    /**
     * Swap two elements in an array
     * @param arr Array containing elements
     * @param i First element index
     * @param j Second element index
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * Insertion sort for small subarrays
     * @param arr Array to be sorted
     * @param low Starting index
     * @param high Ending index
     */
    private static <T extends Comparable<T>> void insertionSort(T[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            T key = arr[i];
            int j = i - 1;
            
            while (j >= low && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            
            arr[j + 1] = key;
        }
    }

    /**
     * Main method with example usage of QuickSort
     */
    public static void main(String[] args) {
        // Example with integers
        Integer[] arr = {10, 7, 8, 9, 1, 5, 3, 2, 6, 4};
        
        System.out.println("Original array:");
        printArray(arr);
        
        // Sort the array
        QuickSort.sort(arr);
        
        System.out.println("\nSorted array:");
        printArray(arr);
        
        // Example with strings
        String[] strArr = {"banana", "apple", "orange", "grape", "mango"};
        
        System.out.println("\nOriginal string array:");
        printArray(strArr);
        
        // Sort the string array
        QuickSort.optimizedSort(strArr);
        
        System.out.println("\nSorted string array:");
        printArray(strArr);
        
        // Benchmark comparison: Regular QuickSort vs Optimized QuickSort
        // Benchmark with large sorted array (worst case for regular QuickSort)
        benchmarkComparison(1000);
    }
    
    /**
     * Benchmark to compare regular QuickSort vs Optimized QuickSort
     * @param size Size of the array to test
     */
    private static void benchmarkComparison(int size) {
        // Create a sorted array (worst case for regular QuickSort)
        Integer[] arr1 = new Integer[size];
        Integer[] arr2 = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr1[i] = i;
            arr2[i] = i;
        }
        
        System.out.println("\nBenchmarking with sorted array of size " + size + ":");
        
        // Test regular QuickSort
        long startTime = System.nanoTime();
        QuickSort.sort(arr1);
        long endTime = System.nanoTime();
        System.out.println("Regular QuickSort time: " + (endTime - startTime) / 1000000.0 + " ms");
        
        // Test optimized QuickSort
        startTime = System.nanoTime();
        QuickSort.optimizedSort(arr2);
        endTime = System.nanoTime();
        System.out.println("Optimized QuickSort time: " + (endTime - startTime) / 1000000.0 + " ms");
    }
    
    /**
     * Utility method to print an array
     * @param arr Array to print
     */
    private static <T> void printArray(T[] arr) {
        for (T element : arr) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
