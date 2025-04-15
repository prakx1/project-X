// Algorithms section handler

function loadAlgorithms() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Algorithms</h2>
        <p>Master essential algorithms for technical interviews</p>
    `;
    contentDiv.appendChild(header);
    
    // Create progress card
    const progressCard = document.createElement('div');
    progressCard.className = 'card';
    progressCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Your Progress</h3>
        </div>
        <div class="card-body">
            <div class="progress-bar">
                <div class="progress" style="width: ${appState.progress['algorithms']}%"></div>
            </div>
            <span>${appState.progress['algorithms']}% Complete</span>
        </div>
    `;
    contentDiv.appendChild(progressCard);
    
    // Create algorithms by category
    const categories = [
        { id: 'algo-sorting', name: 'Sorting Algorithms', icon: 'sort-amount-down' },
        { id: 'algo-searching', name: 'Searching Algorithms', icon: 'search' },
        { id: 'algo-graph', name: 'Graph Algorithms', icon: 'project-diagram' },
        { id: 'algo-dp', name: 'Dynamic Programming', icon: 'table' },
        { id: 'algo-string', name: 'String Algorithms', icon: 'font' },
        { id: 'algo-backtracking', name: 'Backtracking', icon: 'undo' },
        { id: 'algo-greedy', name: 'Greedy Algorithms', icon: 'hand-holding-usd' },
        { id: 'algo-divide-conquer', name: 'Divide and Conquer', icon: 'object-group' }
    ];
    
    categories.forEach(category => {
        const topic = topicData['algorithms'].topics.find(t => t.id === category.id);
        if (!topic) return;
        
        const categoryCard = document.createElement('div');
        categoryCard.className = 'card';
        categoryCard.innerHTML = `
            <div class="card-header">
                <h3 class="card-title"><i class="fas fa-${category.icon}"></i> ${topic.name}</h3>
            </div>
            <div class="card-body">
                <p>${topic.description}</p>
                ${topic.implementations && topic.implementations.length > 0 ? `
                <ul class="implementation-list">
                    ${topic.implementations.map(impl => `
                        <li class="implementation-item" data-id="${impl.id}">
                            <i class="fas fa-code"></i> ${impl.name}
                        </li>
                    `).join('')}
                </ul>
                ` : '<p>No implementations available yet.</p>'}
                <button class="btn btn-primary view-topic-btn" data-id="${topic.id}">View Details</button>
            </div>
        `;
        contentDiv.appendChild(categoryCard);
        
        // Add event listener for View Details button
        const viewBtn = categoryCard.querySelector('.view-topic-btn');
        viewBtn.addEventListener('click', function() {
            const topicId = this.getAttribute('data-id');
            loadAlgorithmDetail(topicId);
        });
        
        // Add event listeners for implementation items
        const implItems = categoryCard.querySelectorAll('.implementation-item');
        implItems.forEach(item => {
            item.addEventListener('click', function() {
                const implId = this.getAttribute('data-id');
                loadImplementationDetail(implId);
            });
        });
    });
}

function loadAlgorithmDetail(topicId) {
    const topic = topicData['algorithms'].topics.find(t => t.id === topicId);
    if (!topic) return;
    
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create back button
    const backButton = document.createElement('button');
    backButton.className = 'btn btn-secondary back-button';
    backButton.innerHTML = '<i class="fas fa-arrow-left"></i> Back to Algorithms';
    backButton.addEventListener('click', () => loadAlgorithms());
    contentDiv.appendChild(backButton);
    
    // Create topic detail card
    const detailCard = document.createElement('div');
    detailCard.className = 'card';
    detailCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">${topic.name}</h3>
            <div class="topic-status">
                <select class="topic-status-toggle" data-topic-id="${topic.id}">
                    <option value="not-started" ${!appState.completedTopics.includes(topic.id) ? 'selected' : ''}>Not Started</option>
                    <option value="completed" ${appState.completedTopics.includes(topic.id) ? 'selected' : ''}>Completed</option>
                </select>
            </div>
        </div>
        <div class="card-body">
            <p>${topic.description}</p>
            
            ${topic.complexity ? `
            <h4>Time Complexity</h4>
            <table class="complexity-table">
                <thead>
                    <tr>
                        <th>Algorithm</th>
                        <th>Time Complexity</th>
                    </tr>
                </thead>
                <tbody>
                    ${Object.entries(topic.complexity).map(([algo, complexity]) => `
                        <tr>
                            <td>${formatAlgorithmName(algo)}</td>
                            <td>${complexity}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
            ` : ''}
            
            ${topic.implementations && topic.implementations.length > 0 ? `
            <h4>Implementations</h4>
            <ul class="implementation-list">
                ${topic.implementations.map(impl => `
                    <li class="implementation-item" data-id="${impl.id}">
                        <i class="fas fa-code"></i> ${impl.name}
                    </li>
                `).join('')}
            </ul>
            ` : '<p>No implementations available yet.</p>'}
            
            ${topic.resources && topic.resources.length > 0 ? `
            <h4>Additional Resources</h4>
            <ul>
                ${topic.resources.map(resource => `
                    <li><a href="${resource.url}" target="_blank">${resource.name}</a></li>
                `).join('')}
            </ul>
            ` : ''}
        </div>
    `;
    contentDiv.appendChild(detailCard);
    
    // Add event listener for status toggle
    const statusToggle = detailCard.querySelector('.topic-status-toggle');
    statusToggle.addEventListener('change', function() {
        updateTopicStatus(topic.id, this.value);
    });
    
    // Add event listeners for implementation items
    const implItems = detailCard.querySelectorAll('.implementation-item');
    implItems.forEach(item => {
        item.addEventListener('click', function() {
            const implId = this.getAttribute('data-id');
            loadImplementationDetail(implId);
        });
    });
}

// Helper function to format algorithm names
function formatAlgorithmName(algorithm) {
    // Convert camelCase to sentence case with proper capitalization
    return algorithm
        .replace(/([A-Z])/g, ' $1')
        .replace(/^./, str => str.toUpperCase());
}
