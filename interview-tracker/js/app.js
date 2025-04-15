// Main application logic for Interview Preparation Tracker

// Global state object to track user progress
const appState = {
    currentSection: 'dashboard',
    progress: {
        'data-structures': 0,
        'algorithms': 0,
        'java-concepts': 0,
        'system-design': 0,
        'behavioral': 0,
        'leetcode': 0
    },
    completedTopics: [],
    studyPlan: [],
    settings: {
        darkMode: false,
        reminderEnabled: true,
        targetDate: null
    }
};

// Initialize the application
function initApp() {
    loadSavedState();
    setupEventListeners();
    updateProgressBar();
    loadContent(appState.currentSection);
}

// Load saved state from localStorage
function loadSavedState() {
    const savedState = localStorage.getItem('interviewPrepState');
    if (savedState) {
        try {
            const parsedState = JSON.parse(savedState);
            Object.assign(appState, parsedState);
        } catch (error) {
            console.error('Error loading saved state:', error);
        }
    }
}

// Save current state to localStorage
function saveState() {
    localStorage.setItem('interviewPrepState', JSON.stringify(appState));
}

// Set up event listeners
function setupEventListeners() {
    // Navigation menu click events
    document.querySelectorAll('.nav-links li').forEach(item => {
        item.addEventListener('click', function() {
            const section = this.getAttribute('data-section');
            navigateTo(section);
        });
    });

    // Search functionality
    const searchInput = document.getElementById('search-input');
    const searchBtn = document.getElementById('search-btn');

    searchBtn.addEventListener('click', function() {
        performSearch(searchInput.value);
    });

    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            performSearch(searchInput.value);
        }
    });

    // Global event delegation for dynamic content
    document.addEventListener('click', function(e) {
        // Topic item click
        if (e.target.closest('.topic-item')) {
            const topicItem = e.target.closest('.topic-item');
            const topicId = topicItem.getAttribute('data-id');
            if (topicId) {
                loadTopicDetail(topicId);
            }
        }

        // Implementation item click
        if (e.target.closest('.implementation-item')) {
            const implItem = e.target.closest('.implementation-item');
            const implId = implItem.getAttribute('data-id');
            if (implId) {
                loadImplementationDetail(implId);
            }
        }

        // Topic status toggle
        if (e.target.closest('.topic-status-toggle')) {
            const toggle = e.target.closest('.topic-status-toggle');
            const topicId = toggle.getAttribute('data-topic-id');
            const status = toggle.value;
            updateTopicStatus(topicId, status);
        }

        // Save study plan
        if (e.target.id === 'save-study-plan') {
            saveStudyPlan();
        }

        // Save settings
        if (e.target.id === 'save-settings') {
            saveSettings();
        }
        
        // View topic button click
        if (e.target.closest('.view-topic-btn')) {
            const btn = e.target.closest('.view-topic-btn');
            const topicId = btn.getAttribute('data-id');
            if (topicId) {
                loadTopicDetail(topicId);
            }
        }
        
        // Close modal button
        if (e.target.closest('.close-modal')) {
            const modal = e.target.closest('.modal');
            if (modal) {
                document.body.removeChild(modal);
            }
        }
    });
}

// Navigate to a different section
function navigateTo(section) {
    // Update active navigation item
    document.querySelectorAll('.nav-links li').forEach(item => {
        item.classList.remove('active');
    });
    document.querySelector(`.nav-links li[data-section="${section}"]`).classList.add('active');

    // Update current section
    appState.currentSection = section;
    saveState();

    // Load the content for the section
    loadContent(section);
}

// Load content for a section
function loadContent(section) {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = ''; // Clear current content

    switch (section) {
        case 'dashboard':
            loadDashboard();
            break;
        case 'data-structures':
            loadDataStructures();
            break;
        case 'algorithms':
            loadAlgorithms();
            break;
        case 'java-concepts':
            loadJavaConcepts();
            break;
        case 'system-design':
            loadSystemDesign();
            break;
        case 'behavioral':
            loadBehavioral();
            break;
        case 'leetcode':
            loadLeetCode();
            break;
        case 'study-plan':
            loadStudyPlan();
            break;
        case 'settings':
            loadSettings();
            break;
        default:
            loadDashboard();
    }
}

// Helper function to load topic detail
function loadTopicDetail(topicId) {
    // Find which category the topic belongs to
    let category = null;
    let topic = null;
    
    for (const cat in topicData) {
        if (topicData[cat].topics) {
            const foundTopic = topicData[cat].topics.find(t => t.id === topicId);
            if (foundTopic) {
                category = cat;
                topic = foundTopic;
                break;
            }
        }
    }
    
    if (!category || !topic) {
        console.error('Topic not found:', topicId);
        return;
    }
    
    // Call the appropriate function to load the topic detail
    switch (category) {
        case 'data-structures':
            loadDataStructureDetail(topicId);
            break;
        case 'algorithms':
            loadAlgorithmDetail(topicId);
            break;
        case 'java-concepts':
            loadJavaConceptDetail(topicId);
            break;
        case 'system-design':
            loadSystemDesignDetail(topicId);
            break;
        case 'behavioral':
            // For behavioral, we don't have specific topic details
            loadBehavioral();
            break;
        default:
            console.error('Unknown category:', category);
    }
}

// Load the dashboard view
function loadDashboard() {
    const contentDiv = document.getElementById('main-content');
    
    // Create dashboard header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Dashboard</h2>
        <p>Track your interview preparation progress</p>
    `;
    contentDiv.appendChild(header);
    
    // Create stats grid
    const statsGrid = document.createElement('div');
    statsGrid.className = 'stats-grid';
    
    // Calculate overall progress
    const totalProgress = calculateOverallProgress();
    
    // Add stat cards
    statsGrid.innerHTML = `
        <div class="stat-card">
            <i class="fas fa-chart-line"></i>
            <div class="stat-value">${totalProgress}%</div>
            <div class="stat-title">Overall Progress</div>
        </div>
        <div class="stat-card">
            <i class="fas fa-check-circle"></i>
            <div class="stat-value">${appState.completedTopics.length}</div>
            <div class="stat-title">Topics Completed</div>
        </div>
        <div class="stat-card">
            <i class="fas fa-code"></i>
            <div class="stat-value">${topicData.leetcode.problems.length}</div>
            <div class="stat-title">LeetCode Problems</div>
        </div>
        <div class="stat-card">
            <i class="fas fa-calendar-check"></i>
            <div class="stat-value">${calculateDaysUntilTarget()}</div>
            <div class="stat-title">Days Until Target</div>
        </div>
    `;
    contentDiv.appendChild(statsGrid);
    
    // Add progress by category card
    const progressCard = document.createElement('div');
    progressCard.className = 'card';
    progressCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Progress by Category</h3>
        </div>
        <div class="card-body">
            <canvas id="progress-chart" height="250"></canvas>
        </div>
    `;
    contentDiv.appendChild(progressCard);
    
    // Create progress chart
    setTimeout(() => {
        createProgressChart();
    }, 100);
    
    // Recent activity card
    const recentActivityCard = document.createElement('div');
    recentActivityCard.className = 'card';
    recentActivityCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Next Topics to Study</h3>
        </div>
        <div class="card-body">
            <ul class="topic-list" id="recommended-topics">
                ${generateRecommendedTopics()}
            </ul>
        </div>
    `;
    contentDiv.appendChild(recentActivityCard);
}

// Calculate overall progress across all categories
function calculateOverallProgress() {
    const categories = Object.keys(appState.progress);
    if (categories.length === 0) return 0;
    
    const sum = categories.reduce((total, category) => {
        return total + appState.progress[category];
    }, 0);
    
    return Math.round(sum / categories.length);
}

// Calculate days until target interview date
function calculateDaysUntilTarget() {
    if (!appState.settings.targetDate) return "--";
    
    const today = new Date();
    const targetDate = new Date(appState.settings.targetDate);
    const timeDiff = targetDate - today;
    const daysLeft = Math.ceil(timeDiff / (1000 * 3600 * 24));
    
    return daysLeft > 0 ? daysLeft : "0";
}

// Generate HTML for recommended topics
function generateRecommendedTopics() {
    // Get topics that are not yet completed
    const allTopics = [];
    
    // Collect topics from all categories
    for (const category in topicData) {
        if (topicData[category].topics) {
            topicData[category].topics.forEach(topic => {
                if (!appState.completedTopics.includes(topic.id)) {
                    allTopics.push({
                        ...topic,
                        category
                    });
                }
            });
        }
    }
    
    // Sort by priority (if available) or randomly select
    allTopics.sort((a, b) => (a.priority || 5) - (b.priority || 5));
    
    // Get top 5 recommended topics
    const recommendedTopics = allTopics.slice(0, 5);
    
    // Generate HTML
    if (recommendedTopics.length === 0) {
        return '<li class="topic-item">Great job! You\'ve completed all topics!</li>';
    }
    
    return recommendedTopics.map(topic => `
        <li class="topic-item" data-id="${topic.id}">
            <div class="topic-name">
                <i class="fas fa-${getCategoryIcon(topic.category)}"></i>
                <span>${topic.name}</span>
            </div>
            <div class="topic-status">
                <span class="status status-not-started">Not Started</span>
            </div>
        </li>
    `).join('');
}

// Get icon for category
function getCategoryIcon(category) {
    const icons = {
        'data-structures': 'project-diagram',
        'algorithms': 'cogs',
        'java-concepts': 'java',
        'system-design': 'sitemap',
        'behavioral': 'users',
        'leetcode': 'code'
    };
    
    return icons[category] || 'bookmark';
}

// Create progress chart
function createProgressChart() {
    const ctx = document.getElementById('progress-chart');
    if (!ctx) return;
    
    const ctxContext = ctx.getContext('2d');
    
    // Prepare data
    const categories = Object.keys(appState.progress);
    const progressValues = categories.map(cat => appState.progress[cat]);
    
    // Format category labels
    const labels = categories.map(cat => {
        return cat.split('-').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
    });
    
    // Create chart
    new Chart(ctxContext, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Progress (%)',
                data: progressValues,
                backgroundColor: [
                    'rgba(74, 111, 165, 0.7)',
                    'rgba(22, 96, 136, 0.7)',
                    'rgba(79, 195, 161, 0.7)',
                    'rgba(236, 107, 86, 0.7)',
                    'rgba(240, 180, 89, 0.7)',
                    'rgba(155, 89, 182, 0.7)'
                ],
                borderColor: [
                    'rgba(74, 111, 165, 1)',
                    'rgba(22, 96, 136, 1)',
                    'rgba(79, 195, 161, 1)',
                    'rgba(236, 107, 86, 1)',
                    'rgba(240, 180, 89, 1)',
                    'rgba(155, 89, 182, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100,
                    title: {
                        display: true,
                        text: 'Completion (%)'
                    }
                }
            }
        }
    });
}

// Update the progress bar based on overall progress
function updateProgressBar() {
    const progress = calculateOverallProgress();
    const progressBar = document.getElementById('overall-progress');
    const progressText = document.getElementById('progress-percentage');
    
    if (progressBar && progressText) {
        progressBar.style.width = `${progress}%`;
        progressText.textContent = `${progress}%`;
    }
}

// Perform search across all topics
function performSearch(query) {
    if (!query || query.trim() === '') return;
    
    query = query.toLowerCase().trim();
    const results = [];
    
    // Search in all categories
    for (const category in topicData) {
        if (topicData[category].topics) {
            topicData[category].topics.forEach(topic => {
                if (topic.name.toLowerCase().includes(query) || 
                    (topic.description && topic.description.toLowerCase().includes(query))) {
                    results.push({
                        ...topic,
                        category
                    });
                }
            });
        }
    }
    
    // Display search results
    displaySearchResults(query, results);
}

// Display search results
function displaySearchResults(query, results) {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create search results header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Search Results</h2>
        <p>Found ${results.length} results for "${query}"</p>
    `;
    contentDiv.appendChild(header);
    
    // Create results card
    const resultsCard = document.createElement('div');
    resultsCard.className = 'card';
    
    // Generate results list
    let resultsHTML = '';
    if (results.length === 0) {
        resultsHTML = '<p>No results found. Try a different search term.</p>';
    } else {
        resultsHTML = `
            <ul class="topic-list">
                ${results.map(topic => `
                    <li class="topic-item" data-id="${topic.id}">
                        <div class="topic-name">
                            <i class="fas fa-${getCategoryIcon(topic.category)}"></i>
                            <span>${topic.name}</span>
                            <small class="category-label">${formatCategoryName(topic.category)}</small>
                        </div>
                        <div class="topic-status">
                            <span class="status ${getTopicStatusClass(topic.id)}">
                                ${getTopicStatus(topic.id)}
                            </span>
                        </div>
                    </li>
                `).join('')}
            </ul>
        `;
    }
    
    resultsCard.innerHTML = resultsHTML;
    contentDiv.appendChild(resultsCard);
}

// Format category name for display
function formatCategoryName(category) {
    return category.split('-').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}

// Get topic status (Not Started, In Progress, Completed)
function getTopicStatus(topicId) {
    if (appState.completedTopics.includes(topicId)) {
        return 'Completed';
    }
    
    // Check if in progress (implementation pending)
    return 'Not Started';
}

// Get CSS class for topic status
function getTopicStatusClass(topicId) {
    if (appState.completedTopics.includes(topicId)) {
        return 'status-completed';
    }
    
    // Check if in progress (implementation pending)
    return 'status-not-started';
}

/**
 * Utility functions for loading implementation details
 */
async function loadImplementationDetail(implId) {
    // Find the implementation in the topicData
    let implementation = null;
    let foundTopic = null;
    let categoryName = '';
    
    // Search through all categories and topics
    for (const category in topicData) {
        if (topicData[category].topics) {
            for (const topic of topicData[category].topics) {
                if (topic.implementations) {
                    const impl = topic.implementations.find(i => i.id === implId);
                    if (impl) {
                        implementation = impl;
                        foundTopic = topic;
                        categoryName = category;
                        break;
                    }
                }
            }
        }
        
        if (implementation) break;
    }
    
    if (!implementation) {
        console.error(`Implementation not found: ${implId}`);
        return;
    }
    
    // Create modal for viewing the implementation
    const modal = document.createElement('div');
    modal.className = 'modal implementation-modal';
    
    // Set modal header and loading message
    modal.innerHTML = `
        <div class="modal-content large-modal">
            <div class="modal-header">
                <h3>${implementation.name}</h3>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
                <div class="implementation-info">
                    <p><strong>Topic:</strong> ${foundTopic.name}</p>
                    <p><strong>Category:</strong> ${formatCategoryName(categoryName)}</p>
                    <p><strong>Path:</strong> ${implementation.path}</p>
                </div>
                <div class="simple-code-container">
                    <code id="code-content">Loading code...</code>
                </div>
                <button class="btn btn-primary mark-completed-btn" data-topic-id="${foundTopic.id}">
                    Mark Topic as Completed
                </button>
            </div>
        </div>
    `;
    
    // Add modal to page
    document.body.appendChild(modal);
    
    // Add event listener for close button
    modal.querySelector('.close-modal').addEventListener('click', function() {
        document.body.removeChild(modal);
    });
    
    // Add event listener for mark completed button
    modal.querySelector('.mark-completed-btn').addEventListener('click', function() {
        const topicId = this.getAttribute('data-topic-id');
        updateTopicStatus(topicId, 'completed');
        this.textContent = 'Topic Marked as Completed';
        this.disabled = true;
    });
    
    try {
        // Load code content
        const code = await loadCodeFromPath(implementation.path);
        
        // Format code with simple HTML escaping
        const formattedCode = code
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;')
            .replace(/\n/g, '<br>')
            .replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;')
            .replace(/  /g, '&nbsp;&nbsp;');
        
        document.getElementById('code-content').innerHTML = formattedCode;
    } catch (error) {
        document.getElementById('code-content').innerHTML = 
            `// Error loading code from ${implementation.path}<br>// ${error.message}`;
    }
}

/**
 * Custom syntax highlighting for Java code
 */
function customSyntaxHighlight(code) {
    // Add line numbers
    const lines = code.split('\n');
    const numberedLines = lines.map((line, index) => {
        const lineNumber = index + 1;
        return `<div class="code-line"><span class="line-number">${lineNumber}</span><span class="line-content">${highlightJavaSyntax(line)}</span></div>`;
    }).join('');
    
    return `<div class="custom-code">${numberedLines}</div>`;
}

/**
 * Simple Java syntax highlighter
 */
function highlightJavaSyntax(line) {
    // Escape HTML
    let html = line.replace(/</g, '&lt;').replace(/>/g, '&gt;');
    
    // Java keywords
    const keywords = [
        'abstract', 'assert', 'boolean', 'break', 'byte', 'case', 'catch', 'char', 'class', 'const', 
        'continue', 'default', 'do', 'double', 'else', 'enum', 'extends', 'final', 'finally', 'float', 
        'for', 'goto', 'if', 'implements', 'import', 'instanceof', 'int', 'interface', 'long', 'native', 
        'new', 'package', 'private', 'protected', 'public', 'return', 'short', 'static', 'strictfp', 
        'super', 'switch', 'synchronized', 'this', 'throw', 'throws', 'transient', 'try', 'void', 
        'volatile', 'while', 'true', 'false', 'null'
    ];
    
    // Highlight keywords
    keywords.forEach(keyword => {
        const regex = new RegExp(`\\b${keyword}\\b`, 'g');
        html = html.replace(regex, `<span class="keyword">${keyword}</span>`);
    });
    
    // Highlight strings
    html = html.replace(/"([^"]*)"/g, '<span class="string">"$1"</span>');
    
    // Highlight comments
    if (html.includes('//')) {
        const commentIndex = html.indexOf('//');
        const beforeComment = html.substring(0, commentIndex);
        const comment = html.substring(commentIndex);
        html = beforeComment + '<span class="comment">' + comment + '</span>';
    }
    
    // Highlight method names (simple approach)
    html = html.replace(/(\w+)\s*\(/g, '<span class="method">$1</span>(');
    
    // Highlight annotations
    html = html.replace(/@(\w+)/g, '<span class="annotation">@$1</span>');
    
    return html;
}

/**
 * Update topic status (completed or not started)
 */
function updateTopicStatus(topicId, status) {
    if (status === 'completed') {
        // Add to completed topics if not already there
        if (!appState.completedTopics.includes(topicId)) {
            appState.completedTopics.push(topicId);
        }
    } else {
        // Remove from completed topics
        appState.completedTopics = appState.completedTopics.filter(id => id !== topicId);
    }
    
    // Update progress for the topic's category
    updateCategoryProgress(topicId);
    
    // Save state
    saveState();
    
    // Update UI if necessary
    updateProgressBar();
}

/**
 * Update progress for a category based on completed topics
 */
function updateCategoryProgress(topicId) {
    // Find which category the topic belongs to
    let category = null;
    
    for (const cat in topicData) {
        if (topicData[cat].topics) {
            if (topicData[cat].topics.some(topic => topic.id === topicId)) {
                category = cat;
                break;
            }
        }
    }
    
    if (!category) return;
    
    // Calculate progress for the category
    const totalTopics = topicData[category].topics.length;
    const completedTopics = topicData[category].topics.filter(topic => 
        appState.completedTopics.includes(topic.id)
    ).length;
    
    const progress = totalTopics > 0 ? Math.floor((completedTopics / totalTopics) * 100) : 0;
    
    // Update progress in app state
    appState.progress[category] = progress;
}

// Utility functions from loader.js
function loadCodeFromPath(path) {
    // Since we can't actually fetch the files from the filesystem through HTTP,
    // we'll generate simulated content based on the path
    return new Promise((resolve) => {
        // Extract the filename from the path
        const parts = path.split('/');
        const filename = parts[parts.length - 1];
        
        // Generate sample code based on the path
        let code = '';
        
        if (path.includes('LinkedList')) {
            code = `/**
 * Singly Linked List Implementation
 */
public class SinglyLinkedList<T> {
    private static class Node<T> {
        private T data;
        private Node<T> next;
        
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }
    
    // More methods would be here...
}`;
        } else if (path.includes('Tree')) {
            code = `/**
 * Binary Tree Implementation
 */
public class BinaryTree<T> {
    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        
        public Node(T data) {
            this.data = data;
        }
    }
    
    // Implementation would be here...
}`;
        } else {
            code = `/**
 * Implementation of ${filename}
 * This is a simulated view of the file at:
 * ${path}
 */
public class ${filename.replace('.java', '')} {
    // Implementation would be here...
    
    public static void main(String[] args) {
        System.out.println("Example implementation");
    }
}`;
        }
        
        setTimeout(() => resolve(code), 300); // Add a small delay for realism
    });
}

function loadJsonData(path) {
    return fetch(path)
        .then(response => response.json())
        .catch(error => {
            throw new Error(`Failed to load JSON data from ${path}: ${error.message}`);
        });
}
