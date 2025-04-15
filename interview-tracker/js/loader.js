// Loader file to combine all section handlers and utilities

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
                <div class="code-container">
                    <pre><code class="language-${implementation.language || 'java'}" id="implementation-code">Loading code...</code></pre>
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
    
    // Try to load code from the file path
    try {
        const code = await loadCodeFromPath(implementation.path);
        document.getElementById('implementation-code').textContent = code;
        
        // Highlight code if highlight.js is available
        if (window.hljs) {
            hljs.highlightElement(document.getElementById('implementation-code'));
        }
    } catch (error) {
        document.getElementById('implementation-code').textContent = 
            `// Error loading code from ${implementation.path}\n// ${error.message}`;
    }
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
