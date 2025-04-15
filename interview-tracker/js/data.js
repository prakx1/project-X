// Data structure for the Interview Preparation Tracker
// This file contains all the topics, implementations, and other content

const topicData = {
    'data-structures': {
        name: 'Data Structures',
        description: 'Fundamental data structures used in computer science and technical interviews',
        topics: [
            {
                id: 'ds-arrays',
                name: 'Arrays',
                description: 'A collection of elements stored at contiguous memory locations',
                complexity: {
                    access: 'O(1)',
                    search: 'O(n)',
                    insert: 'O(n)',
                    delete: 'O(n)'
                },
                implementations: [
                    {
                        id: 'impl-arrays-basic',
                        name: 'Basic Array Operations',
                        path: '/Volumes/WorkStation/project-X/data structures/arrays',
                        language: 'java'
                    }
                ],
                resources: [
                    {
                        name: 'Array Data Structure',
                        url: 'https://www.geeksforgeeks.org/array-data-structure/'
                    }
                ]
            },
            {
                id: 'ds-linked-lists',
                name: 'Linked Lists',
                description: 'Linear data structure where elements are not stored at contiguous locations',
                complexity: {
                    access: 'O(n)',
                    search: 'O(n)',
                    insert: 'O(1) at head, O(n) elsewhere',
                    delete: 'O(1) at head, O(n) elsewhere'
                },
                implementations: [
                    {
                        id: 'impl-singly-linked-list',
                        name: 'Singly Linked List',
                        path: '/Volumes/WorkStation/project-X/data structures/linked_lists/SinglyLinkedList.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-doubly-linked-list',
                        name: 'Doubly Linked List',
                        path: '/Volumes/WorkStation/project-X/data structures/linked lists/DoublyLinkedList.java',
                        language: 'java'
                    }
                ],
                resources: [
                    {
                        name: 'Linked List Data Structure',
                        url: 'https://www.geeksforgeeks.org/data-structures/linked-list/'
                    }
                ]
            },
            {
                id: 'ds-stacks',
                name: 'Stacks',
                description: 'Last In First Out (LIFO) data structure',
                complexity: {
                    access: 'O(n)',
                    search: 'O(n)',
                    insert: 'O(1) at top',
                    delete: 'O(1) at top'
                },
                implementations: [
                    {
                        id: 'impl-stack-array',
                        name: 'Stack using Array',
                        path: '/Volumes/WorkStation/project-X/data structures/stack',
                        language: 'java'
                    },
                    {
                        id: 'impl-stack-linkedlist',
                        name: 'Stack using Linked List',
                        path: '/Volumes/WorkStation/project-X/data structures/stack',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-queues',
                name: 'Queues',
                description: 'First In First Out (FIFO) data structure',
                complexity: {
                    access: 'O(n)',
                    search: 'O(n)',
                    insert: 'O(1) at rear',
                    delete: 'O(1) at front'
                },
                implementations: [
                    {
                        id: 'impl-queue-array',
                        name: 'Queue using Array',
                        path: '/Volumes/WorkStation/project-X/data structures/queue',
                        language: 'java'
                    },
                    {
                        id: 'impl-queue-linkedlist',
                        name: 'Queue using Linked List',
                        path: '/Volumes/WorkStation/project-X/data structures/queue',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-trees',
                name: 'Trees',
                description: 'Hierarchical data structure with a root value and subtrees as children',
                complexity: {
                    access: 'O(log n) average, O(n) worst',
                    search: 'O(log n) average, O(n) worst',
                    insert: 'O(log n) average, O(n) worst',
                    delete: 'O(log n) average, O(n) worst'
                },
                implementations: [
                    {
                        id: 'impl-binary-tree',
                        name: 'Binary Tree',
                        path: '/Volumes/WorkStation/project-X/data structures/trees',
                        language: 'java'
                    },
                    {
                        id: 'impl-binary-search-tree',
                        name: 'Binary Search Tree',
                        path: '/Volumes/WorkStation/project-X/data structures/trees/BinarySearchTree.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-avl-tree',
                        name: 'AVL Tree',
                        path: '/Volumes/WorkStation/project-X/data structures/trees/AVLTree.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-heaps',
                name: 'Heaps',
                description: 'A special tree-based data structure that satisfies the heap property',
                complexity: {
                    findMin: 'O(1)',
                    insert: 'O(log n)',
                    deleteMin: 'O(log n)',
                    buildHeap: 'O(n)'
                },
                implementations: [
                    {
                        id: 'impl-binary-heap',
                        name: 'Binary Heap',
                        path: '/Volumes/WorkStation/project-X/data structures/heap',
                        language: 'java'
                    },
                    {
                        id: 'impl-priority-queue',
                        name: 'Priority Queue',
                        path: '/Volumes/WorkStation/project-X/data structures/heap',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-hash-tables',
                name: 'Hash Tables',
                description: 'Data structure that implements an associative array',
                complexity: {
                    search: 'O(1) average, O(n) worst',
                    insert: 'O(1) average, O(n) worst',
                    delete: 'O(1) average, O(n) worst'
                },
                implementations: [
                    {
                        id: 'impl-hash-table',
                        name: 'Hash Table Implementation',
                        path: '/Volumes/WorkStation/project-X/data structures/hash_tables',
                        language: 'java'
                    },
                    {
                        id: 'impl-lru-cache',
                        name: 'LRU Cache',
                        path: '/Volumes/WorkStation/project-X/data structures/hash_tables/LRUCache.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-graphs',
                name: 'Graphs',
                description: 'Non-linear data structure consisting of vertices and edges',
                complexity: {
                    BFS: 'O(V + E)',
                    DFS: 'O(V + E)',
                    dijkstra: 'O(E log V)'
                },
                implementations: [
                    {
                        id: 'impl-graph-adjacency-list',
                        name: 'Graph (Adjacency List)',
                        path: '/Volumes/WorkStation/project-X/data structures/graphs/Graph.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-graph-adjacency-matrix',
                        name: 'Graph (Adjacency Matrix)',
                        path: '/Volumes/WorkStation/project-X/data structures/graphs',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-trie',
                name: 'Trie',
                description: 'Tree-like data structure that stores a dynamic set of strings',
                complexity: {
                    search: 'O(m) where m is key length',
                    insert: 'O(m) where m is key length',
                    delete: 'O(m) where m is key length'
                },
                implementations: [
                    {
                        id: 'impl-trie',
                        name: 'Trie Implementation',
                        path: '/Volumes/WorkStation/project-X/data structures/tries/Trie.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-segment-tree',
                name: 'Segment Tree',
                description: 'A tree data structure for storing intervals or segments',
                complexity: {
                    construction: 'O(n)',
                    query: 'O(log n)',
                    update: 'O(log n)'
                },
                implementations: [
                    {
                        id: 'impl-segment-tree',
                        name: 'Segment Tree',
                        path: '/Volumes/WorkStation/project-X/data structures/trees/SegmentTree.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'ds-union-find',
                name: 'Union Find (Disjoint Set)',
                description: 'A data structure that keeps track of elements partitioned into disjoint sets',
                complexity: {
                    find: 'O(α(n)) almost constant',
                    union: 'O(α(n)) almost constant'
                },
                implementations: [
                    {
                        id: 'impl-union-find',
                        name: 'Union Find Implementation',
                        path: '/Volumes/WorkStation/project-X/data structures/disjoint_set/UnionFind.java',
                        language: 'java'
                    }
                ]
            }
        ]
    },
    'algorithms': {
        name: 'Algorithms',
        description: 'Common algorithms used in computer science and technical interviews',
        topics: [
            {
                id: 'algo-sorting',
                name: 'Sorting Algorithms',
                description: 'Algorithms for arranging elements in a specific order',
                complexity: {
                    bubbleSort: 'O(n²)',
                    selectionSort: 'O(n²)',
                    insertionSort: 'O(n²)',
                    mergeSort: 'O(n log n)',
                    quickSort: 'O(n log n) average, O(n²) worst',
                    heapSort: 'O(n log n)'
                },
                implementations: [
                    {
                        id: 'impl-bubble-sort',
                        name: 'Bubble Sort',
                        path: '/Volumes/WorkStation/project-X/algorithms/sort',
                        language: 'java'
                    },
                    {
                        id: 'impl-quick-sort',
                        name: 'Quick Sort',
                        path: '/Volumes/WorkStation/project-X/algorithms/sort/QuickSort.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'algo-searching',
                name: 'Searching Algorithms',
                description: 'Algorithms for finding elements in a data structure',
                complexity: {
                    linearSearch: 'O(n)',
                    binarySearch: 'O(log n)'
                },
                implementations: [
                    {
                        id: 'impl-binary-search',
                        name: 'Binary Search',
                        path: '/Volumes/WorkStation/project-X/algorithms/search/BinarySearch.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'algo-graph',
                name: 'Graph Algorithms',
                description: 'Algorithms for traversing and processing graphs',
                complexity: {
                    BFS: 'O(V + E)',
                    DFS: 'O(V + E)',
                    dijkstra: 'O(E log V)',
                    bellmanFord: 'O(V·E)',
                    floydWarshall: 'O(V³)'
                },
                implementations: [
                    {
                        id: 'impl-dijkstra',
                        name: 'Dijkstra\'s Algorithm',
                        path: '/Volumes/WorkStation/project-X/algorithms/graph/DijkstraAlgorithm.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-kruskal',
                        name: 'Kruskal\'s MST Algorithm',
                        path: '/Volumes/WorkStation/project-X/algorithms/graph/KruskalMST.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-topological-sort',
                        name: 'Topological Sort',
                        path: '/Volumes/WorkStation/project-X/algorithms/graph/TopologicalSort.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'algo-dp',
                name: 'Dynamic Programming',
                description: 'Technique for solving complex problems by breaking them down into simpler subproblems',
                complexity: {
                    varies: 'Depends on the problem'
                },
                implementations: [
                    {
                        id: 'impl-knapsack',
                        name: 'Knapsack Problem',
                        path: '/Volumes/WorkStation/project-X/algorithms/dynamic_programming/KnapsackProblem.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-lcs',
                        name: 'Longest Common Subsequence',
                        path: '/Volumes/WorkStation/project-X/algorithms/dynamic_programming/LongestCommonSubsequence.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-edit-distance',
                        name: 'Edit Distance',
                        path: '/Volumes/WorkStation/project-X/algorithms/dynamic_programming/EditDistance.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'algo-string',
                name: 'String Algorithms',
                description: 'Algorithms for string processing and pattern matching',
                complexity: {
                    naivePatternMatching: 'O(n·m)',
                    KMP: 'O(n+m)',
                    rabinKarp: 'O(n+m) average, O(n·m) worst'
                },
                implementations: [
                    {
                        id: 'impl-kmp',
                        name: 'KMP Algorithm',
                        path: '/Volumes/WorkStation/project-X/algorithms/string/KMPStringMatching.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-rabin-karp',
                        name: 'Rabin-Karp Algorithm',
                        path: '/Volumes/WorkStation/project-X/algorithms/string/RabinKarpStringMatching.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'algo-backtracking',
                name: 'Backtracking',
                description: 'Algorithm for finding all solutions by exploring all potential candidates',
                complexity: {
                    varies: 'Usually exponential'
                },
                implementations: [
                    {
                        id: 'impl-n-queens',
                        name: 'N-Queens Problem',
                        path: '/Volumes/WorkStation/project-X/algorithms/backtracking/NQueens.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'algo-greedy',
                name: 'Greedy Algorithms',
                description: 'Algorithms that make the locally optimal choice at each stage',
                complexity: {
                    varies: 'Depends on the problem'
                },
                implementations: []
            },
            {
                id: 'algo-divide-conquer',
                name: 'Divide and Conquer',
                description: 'Technique of breaking a problem into subproblems, solving them, and combining the results',
                complexity: {
                    varies: 'Typically O(n log n)'
                },
                implementations: []
            }
        ]
    },
    'java-concepts': {
        name: 'Java Concepts',
        description: 'Core Java concepts and features important for technical interviews',
        topics: [
            {
                id: 'java-oop',
                name: 'Object-Oriented Programming',
                description: 'Core principles of OOP in Java',
                implementations: [
                    {
                        id: 'impl-java-classes',
                        name: 'Classes and Objects',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/ClassesAndObjects.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-java-inheritance',
                        name: 'Inheritance',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/InheritanceExamples.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-java-polymorphism',
                        name: 'Polymorphism',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/PolymorphismExamples.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-java-encapsulation',
                        name: 'Encapsulation',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/EncapsulationExamples.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-java-abstraction',
                        name: 'Abstraction',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/AbstractionExamples.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-java-interfaces',
                        name: 'Interfaces and Abstract Classes',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/InterfacesAndAbstractClasses.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-java-relationships',
                        name: 'Class Relationships',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/RelationshipExamples.java',
                        language: 'java'
                    },
                    {
                        id: 'impl-java-solid',
                        name: 'SOLID Principles',
                        path: '/Volumes/WorkStation/project-X/java/core_concepts/SOLIDPrinciples.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'java-collections',
                name: 'Collections Framework',
                description: 'Java Collections Framework and data structures',
                implementations: []
            },
            {
                id: 'java-concurrency',
                name: 'Concurrency',
                description: 'Multithreading and concurrency in Java',
                implementations: [
                    {
                        id: 'impl-java-concurrency',
                        name: 'Concurrency Examples',
                        path: '/Volumes/WorkStation/project-X/java/concurrency/ConcurrencyExamples.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'java-design-patterns',
                name: 'Design Patterns',
                description: 'Common design patterns implemented in Java',
                implementations: [
                    {
                        id: 'impl-java-design-patterns',
                        name: 'Design Patterns',
                        path: '/Volumes/WorkStation/project-X/java/design_patterns/DesignPatterns.java',
                        language: 'java'
                    }
                ]
            },
            {
                id: 'java-exceptions',
                name: 'Exception Handling',
                description: 'Exception handling in Java',
                implementations: []
            },
            {
                id: 'java-generics',
                name: 'Generics',
                description: 'Generic programming in Java',
                implementations: []
            }
        ]
    },
    'system-design': {
        name: 'System Design',
        description: 'System design concepts and examples for technical interviews',
        topics: [
            {
                id: 'sd-basics',
                name: 'System Design Basics',
                description: 'Fundamental concepts in system design',
                implementations: [
                    {
                        id: 'impl-sd-patterns',
                        name: 'System Design Patterns',
                        path: '/Volumes/WorkStation/project-X/system design/SystemDesignPatterns.md',
                        language: 'markdown'
                    }
                ]
            },
            {
                id: 'sd-scalability',
                name: 'Scalability',
                description: 'Horizontal and vertical scaling, load balancing',
                implementations: []
            },
            {
                id: 'sd-microservices',
                name: 'Microservices',
                description: 'Microservices architecture and design',
                implementations: []
            },
            {
                id: 'sd-distributed',
                name: 'Distributed Systems',
                description: 'Distributed systems concepts and design',
                implementations: []
            },
            {
                id: 'sd-databases',
                name: 'Database Design',
                description: 'SQL, NoSQL, sharding, replication',
                implementations: []
            },
            {
                id: 'sd-caching',
                name: 'Caching',
                description: 'Caching strategies and implementations',
                implementations: []
            },
            {
                id: 'sd-examples',
                name: 'System Design Examples',
                description: 'Examples of system design problems',
                implementations: []
            }
        ]
    },
    'behavioral': {
        name: 'Behavioral',
        description: 'Behavioral interview preparation and common questions',
        topics: [
            {
                id: 'behavioral-prep',
                name: 'Behavioral Interview Preparation',
                description: 'Preparation guide for behavioral interviews',
                implementations: [
                    {
                        id: 'impl-behavioral-prep',
                        name: 'Behavioral Interview Prep',
                        path: '/Volumes/WorkStation/project-X/others/BehavioralInterviewPrep.md',
                        language: 'markdown'
                    }
                ]
            },
            {
                id: 'behavioral-star',
                name: 'STAR Method',
                description: 'Situation, Task, Action, Result framework for answering behavioral questions',
                implementations: []
            }
        ]
    },
    'leetcode': {
        name: 'LeetCode Problems',
        description: 'Common LeetCode problems and solutions',
        problems: [
            {
                id: 'lc-two-sum',
                name: 'Two Sum',
                difficulty: 'Easy',
                link: 'https://leetcode.com/problems/two-sum/',
                tags: ['Array', 'Hash Table'],
                solution: null
            },
            {
                id: 'lc-add-two-numbers',
                name: 'Add Two Numbers',
                difficulty: 'Medium',
                link: 'https://leetcode.com/problems/add-two-numbers/',
                tags: ['Linked List', 'Math'],
                solution: null
            },
            {
                id: 'lc-longest-substring',
                name: 'Longest Substring Without Repeating Characters',
                difficulty: 'Medium',
                link: 'https://leetcode.com/problems/longest-substring-without-repeating-characters/',
                tags: ['String', 'Sliding Window'],
                solution: null
            }
        ]
    }
};

// Helper function to load code from a file path
async function loadCodeFromPath(path) {
    try {
        // In a real application, this would make an API call to get the file content
        // For simplicity, we'll just return a placeholder
        return "// Code would be loaded from " + path;
    } catch (error) {
        console.error('Error loading code:', error);
        return "// Error loading code from " + path;
    }
}

// Get all topics across all categories
function getAllTopics() {
    const allTopics = [];
    
    for (const category in topicData) {
        if (topicData[category].topics) {
            allTopics.push(...topicData[category].topics.map(topic => ({
                ...topic,
                category
            })));
        }
    }
    
    return allTopics;
}

// Generated recommended topics for user based on progress
function getRecommendedTopics(completedTopicIds, count = 5) {
    const allTopics = getAllTopics();
    const incompleteTopics = allTopics.filter(topic => !completedTopicIds.includes(topic.id));
    
    // Sort by priority (if available) or randomly
    const sortedTopics = incompleteTopics.sort((a, b) => {
        // Prioritize topics with implementations
        const aHasImpl = a.implementations && a.implementations.length > 0;
        const bHasImpl = b.implementations && b.implementations.length > 0;
        
        if (aHasImpl && !bHasImpl) return -1;
        if (!aHasImpl && bHasImpl) return 1;
        
        // Random sort for equal priority
        return Math.random() - 0.5;
    });
    
    return sortedTopics.slice(0, count);
}

// Generate a study plan based on user preferences
function generateStudyPlan(targetDate, completedTopicIds) {
    const now = new Date();
    const target = new Date(targetDate);
    const daysUntilTarget = Math.ceil((target - now) / (1000 * 60 * 60 * 24));
    
    if (daysUntilTarget <= 0) {
        return {
            error: "Target date must be in the future"
        };
    }
    
    const allTopics = getAllTopics();
    const incompleteTopics = allTopics.filter(topic => !completedTopicIds.includes(topic.id));
    
    // If no topics left to study
    if (incompleteTopics.length === 0) {
        return {
            message: "All topics completed! You're ready for your interview.",
            plan: []
        };
    }
    
    // Determine how many topics to study per day
    const topicsPerDay = Math.ceil(incompleteTopics.length / daysUntilTarget);
    const studyPlan = [];
    
    // Group topics by category for better organization
    const topicsByCategory = {};
    incompleteTopics.forEach(topic => {
        if (!topicsByCategory[topic.category]) {
            topicsByCategory[topic.category] = [];
        }
        topicsByCategory[topic.category].push(topic);
    });
    
    // Create a balanced schedule across categories
    const categories = Object.keys(topicsByCategory);
    let currentDay = new Date(now);
    let topicIndex = 0;
    
    while (topicIndex < incompleteTopics.length) {
        const dayTopics = [];
        
        // Add topics for this day, balanced across categories if possible
        for (let i = 0; i < topicsPerDay && topicIndex < incompleteTopics.length; i++) {
            const categoryIndex = i % categories.length;
            const category = categories[categoryIndex];
            
            if (topicsByCategory[category] && topicsByCategory[category].length > 0) {
                dayTopics.push(topicsByCategory[category].shift());
                topicIndex++;
            }
        }
        
        if (dayTopics.length > 0) {
            studyPlan.push({
                date: new Date(currentDay),
                topics: dayTopics
            });
        }
        
        // Move to next day
        currentDay.setDate(currentDay.getDate() + 1);
    }
    
    return {
        message: `Created study plan with ${studyPlan.length} days`,
        plan: studyPlan
    };
}
