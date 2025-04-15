# Time & Space Complexity Cheat Sheet

This reference sheet provides a comprehensive overview of time and space complexity for common data structures and algorithms that frequently appear in technical interviews.

## Big O Notation

Big O notation describes the upper bound (worst-case scenario) of time or space complexity.

| Notation    | Name         | Description                                     | Example                            |
|-------------|--------------|------------------------------------------------|------------------------------------|
| O(1)        | Constant     | Operations take the same time regardless of input size | Accessing an array element by index |
| O(log n)    | Logarithmic  | Time increases logarithmically with input size  | Binary search                      |
| O(n)        | Linear       | Time increases linearly with input size         | Linear search                      |
| O(n log n)  | Linearithmic | Combination of linear and logarithmic growth    | Merge sort, heap sort              |
| O(n²)       | Quadratic    | Time increases quadratically with input size    | Bubble sort, insertion sort        |
| O(n³)       | Cubic        | Time increases cubically with input size        | Simple matrix multiplication       |
| O(2ⁿ)       | Exponential  | Time doubles with each addition to input size   | Recursive Fibonacci                |
| O(n!)       | Factorial    | Time grows factorially with input size          | Brute force traveling salesman     |

## Data Structures

### Array

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access          | O(1)         | O(1)       | Direct indexing                   |
| Search          | O(n)         | O(n)       | Linear search                     |
| Search (sorted) | O(log n)     | O(log n)   | Binary search                     |
| Insert          | O(n)         | O(n)       | Need to shift elements            |
| Delete          | O(n)         | O(n)       | Need to shift elements            |

### ArrayList/Dynamic Array

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access          | O(1)         | O(1)       | Direct indexing                   |
| Search          | O(n)         | O(n)       | Linear search                     |
| Insert (end)    | O(1)*        | O(n)       | *Amortized is O(1), worst is O(n) when resizing |
| Insert (middle) | O(n)         | O(n)       | Need to shift elements            |
| Delete (end)    | O(1)         | O(1)       | No shifting needed                |
| Delete (middle) | O(n)         | O(n)       | Need to shift elements            |

### Linked List (Singly)

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access          | O(n)         | O(n)       | Must traverse from head           |
| Search          | O(n)         | O(n)       | Linear search                     |
| Insert (head)   | O(1)         | O(1)       | No traversal needed               |
| Insert (tail)   | O(1)*        | O(1)*      | *With tail pointer                |
| Insert (middle) | O(n)         | O(n)       | Must find position first          |
| Delete (head)   | O(1)         | O(1)       | No traversal needed               |
| Delete (tail)   | O(n)         | O(n)       | Must find previous node           |
| Delete (middle) | O(n)         | O(n)       | Must find previous node           |

### Linked List (Doubly)

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access          | O(n)         | O(n)       | Must traverse from head or tail   |
| Search          | O(n)         | O(n)       | Linear search                     |
| Insert (head)   | O(1)         | O(1)       | No traversal needed               |
| Insert (tail)   | O(1)         | O(1)       | With tail pointer                 |
| Insert (middle) | O(n)         | O(n)       | Must find position first          |
| Delete (head)   | O(1)         | O(1)       | No traversal needed               |
| Delete (tail)   | O(1)         | O(1)       | With tail pointer                 |
| Delete (middle) | O(n)         | O(n)       | Must find node first              |

### Stack

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access (top)    | O(1)         | O(1)       | Only top element accessible       |
| Access (other)  | O(n)         | O(n)       | Must pop elements to access       |
| Search          | O(n)         | O(n)       | Must pop elements to search       |
| Push            | O(1)         | O(1)       | Adding to top                     |
| Pop             | O(1)         | O(1)       | Removing from top                 |

### Queue

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access (front)  | O(1)         | O(1)       | Only front element accessible     |
| Access (other)  | O(n)         | O(n)       | Must dequeue elements to access   |
| Search          | O(n)         | O(n)       | Must dequeue elements to search   |
| Enqueue         | O(1)         | O(1)       | Adding to back                    |
| Dequeue         | O(1)         | O(1)       | Removing from front               |

### Hash Table

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access          | N/A          | N/A        | Direct access not supported       |
| Search          | O(1)         | O(n)       | Worst case with many collisions   |
| Insert          | O(1)         | O(n)       | Worst case with many collisions   |
| Delete          | O(1)         | O(n)       | Worst case with many collisions   |

### Binary Search Tree (BST)

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access          | O(log n)     | O(n)       | Worst case is unbalanced tree     |
| Search          | O(log n)     | O(n)       | Worst case is unbalanced tree     |
| Insert          | O(log n)     | O(n)       | Worst case is unbalanced tree     |
| Delete          | O(log n)     | O(n)       | Worst case is unbalanced tree     |

### Balanced Binary Search Tree (AVL, Red-Black)

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Access          | O(log n)     | O(log n)   | Self-balancing ensures log n      |
| Search          | O(log n)     | O(log n)   | Self-balancing ensures log n      |
| Insert          | O(log n)     | O(log n)   | Includes rebalancing time         |
| Delete          | O(log n)     | O(log n)   | Includes rebalancing time         |

### Heap (Priority Queue)

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Find Min/Max    | O(1)         | O(1)       | Always at the root                |
| Insert          | O(log n)     | O(log n)   | Need to restore heap property     |
| Delete Min/Max  | O(log n)     | O(log n)   | Need to restore heap property     |
| Build Heap      | O(n)         | O(n)       | Not O(n log n) due to optimization|

### Trie

| Operation       | Average Case | Worst Case | Notes                             |
|-----------------|--------------|------------|-----------------------------------|
| Search          | O(m)         | O(m)       | m is length of the key            |
| Insert          | O(m)         | O(m)       | m is length of the key            |
| Delete          | O(m)         | O(m)       | m is length of the key            |
| Prefix Search   | O(p + n)     | O(p + n)   | p is prefix length, n is matches  |

### Graph (Adjacency List)

| Operation             | Average Case | Worst Case | Notes                             |
|-----------------------|--------------|------------|-----------------------------------|
| Add Vertex            | O(1)         | O(1)       | Just adding to the list           |
| Add Edge              | O(1)         | O(1)       | Just adding to vertex's list      |
| Remove Vertex         | O(V + E)     | O(V + E)   | Must remove all connected edges   |
| Remove Edge           | O(E)         | O(E)       | Must find the edge first          |
| DFS/BFS (visit all)   | O(V + E)     | O(V + E)   | Must visit all vertices and edges |

### Graph (Adjacency Matrix)

| Operation             | Average Case | Worst Case | Notes                             |
|-----------------------|--------------|------------|-----------------------------------|
| Add Vertex            | O(V²)        | O(V²)      | Must resize the matrix            |
| Add Edge              | O(1)         | O(1)       | Direct access to the matrix       |
| Remove Vertex         | O(V²)        | O(V²)      | Must resize the matrix            |
| Remove Edge           | O(1)         | O(1)       | Direct access to the matrix       |
| DFS/BFS (visit all)   | O(V²)        | O(V²)      | Must check all potential edges    |

## Sorting Algorithms

| Algorithm       | Best Case  | Average Case | Worst Case | Space Complexity | Stable | Notes                       |
|-----------------|------------|--------------|------------|------------------|--------|------------------------------|
| Bubble Sort     | O(n)       | O(n²)        | O(n²)      | O(1)             | Yes    | Best case when already sorted|
| Selection Sort  | O(n²)      | O(n²)        | O(n²)      | O(1)             | No     | Always makes n² comparisons  |
| Insertion Sort  | O(n)       | O(n²)        | O(n²)      | O(1)             | Yes    | Best case when already sorted|
| Merge Sort      | O(n log n) | O(n log n)   | O(n log n) | O(n)             | Yes    | Consistent performance       |
| Quick Sort      | O(n log n) | O(n log n)   | O(n²)      | O(log n)         | No     | Worst case with bad pivot    |
| Heap Sort       | O(n log n) | O(n log n)   | O(n log n) | O(1)             | No     | In-place sort                |
| Counting Sort   | O(n + k)   | O(n + k)     | O(n + k)   | O(n + k)         | Yes    | k is the range of input      |
| Radix Sort      | O(nk)      | O(nk)        | O(nk)      | O(n + k)         | Yes    | k is the number of digits    |
| Bucket Sort     | O(n + k)   | O(n + k)     | O(n²)      | O(n + k)         | Yes    | k is the number of buckets   |

## Search Algorithms

| Algorithm       | Best Case | Average Case | Worst Case | Notes                       |
|-----------------|-----------|--------------|------------|------------------------------|
| Linear Search   | O(1)      | O(n)         | O(n)       | Best case when first element |
| Binary Search   | O(1)      | O(log n)     | O(log n)   | Requires sorted array        |
| Jump Search     | O(1)      | O(√n)        | O(√n)      | Requires sorted array        |
| Interpolation   | O(1)      | O(log log n) | O(n)       | Assumes uniform distribution |
| Exponential     | O(1)      | O(log n)     | O(log n)   | Useful for unbounded arrays  |

## Graph Algorithms

| Algorithm                     | Time Complexity | Space Complexity | Notes                                |
|-------------------------------|-----------------|------------------|--------------------------------------|
| Breadth-First Search (BFS)    | O(V + E)        | O(V)             | Shortest path in unweighted graph    |
| Depth-First Search (DFS)      | O(V + E)        | O(V)             | Space for recursion stack            |
| Dijkstra's Algorithm          | O(E log V)      | O(V)             | Single-source shortest path          |
| Bellman-Ford Algorithm        | O(V·E)          | O(V)             | Works with negative weights          |
| Floyd-Warshall Algorithm      | O(V³)           | O(V²)            | All-pairs shortest path              |
| Prim's Algorithm              | O(E log V)      | O(V)             | Minimum spanning tree                |
| Kruskal's Algorithm           | O(E log E)      | O(V)             | Minimum spanning tree                |
| Topological Sort              | O(V + E)        | O(V)             | Only for directed acyclic graphs     |
| Strongly Connected Components | O(V + E)        | O(V)             | Tarjan's or Kosaraju's algorithm    |

## Dynamic Programming Problems

| Problem                      | Time Complexity | Space Complexity | Notes                              |
|------------------------------|-----------------|------------------|-------------------------------------|
| Fibonacci Numbers            | O(n)            | O(1)             | With dynamic programming            |
| Knapsack Problem             | O(n·W)          | O(n·W)           | n items, W capacity                 |
| Longest Common Subsequence   | O(m·n)          | O(m·n)           | Strings of length m and n           |
| Longest Increasing Subsequence | O(n²) or O(n log n) | O(n)       | Depending on implementation         |
| Matrix Chain Multiplication  | O(n³)           | O(n²)            | For n matrices                      |
| Edit Distance                | O(m·n)          | O(m·n)           | Strings of length m and n           |
| Coin Change Problem          | O(n·amount)     | O(amount)        | n types of coins                    |
| Rod Cutting                  | O(n²)           | O(n)             | Rod of length n                     |

## Common Interview Problem Patterns

### Sliding Window Technique
- **Time Complexity**: O(n) where n is the size of the input
- **Examples**: Maximum sum subarray of size k, Longest substring without repeating characters

### Two Pointer Technique
- **Time Complexity**: O(n) typically
- **Examples**: Container with most water, 3Sum, Remove duplicates from sorted array

### Fast and Slow Pointers
- **Time Complexity**: O(n)
- **Examples**: Linked list cycle detection, Middle of linked list

### Binary Search Variations
- **Time Complexity**: O(log n)
- **Examples**: Search in rotated sorted array, Find first and last position in sorted array

### Backtracking
- **Time Complexity**: Often exponential O(b^d) where b is branching factor, d is depth
- **Examples**: N-Queens, Sudoku Solver, Permutations

### Divide and Conquer
- **Time Complexity**: Often O(n log n)
- **Examples**: Merge sort, Quick sort, Count inversions

### Greedy Algorithms
- **Time Complexity**: Varies, often O(n log n) due to sorting
- **Examples**: Activity selection, Huffman coding, Minimum coin change

### Bit Manipulation
- **Time Complexity**: Often O(1) or O(log n)
- **Examples**: Single number, Counting bits, Power of two

## Memory Usage by Data Type in Java

| Data Type | Size (bytes) |
|-----------|--------------|
| boolean   | 1            |
| byte      | 1            |
| char      | 2            |
| short     | 2            |
| int       | 4            |
| float     | 4            |
| long      | 8            |
| double    | 8            |
| Object reference | 4 or 8 (depends on JVM) |

## Tips for Optimizing Algorithms
1. Choose the right data structure for the problem
2. Precompute or cache results when possible
3. Use appropriate algorithms based on input size and constraints
4. Consider space-time tradeoffs
5. Optimize the most frequent operations
6. Be aware of constant factors in real-world performance
7. Consider early termination when the answer is found
