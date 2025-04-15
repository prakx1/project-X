// Data Structures section handler

function loadDataStructures() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Data Structures</h2>
        <p>Master the fundamental building blocks of computer science</p>
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
                <div class="progress" style="width: ${appState.progress['data-structures']}%"></div>
            </div>
            <span>${appState.progress['data-structures']}% Complete</span>
        </div>
    `;
    contentDiv.appendChild(progressCard);
    
    // Create topics list card
    const topicsCard = document.createElement('div');
    topicsCard.className = 'card';
    topicsCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Data Structures</h3>
        </div>
        <div class="card-body">
            <ul class="topic-list">
                ${generateDataStructureTopicsList()}
            </ul>
        </div>
    `;
    contentDiv.appendChild(topicsCard);
}

function generateDataStructureTopicsList() {
    const topics = topicData['data-structures'].topics;
    
    return topics.map(topic => `
        <li class="topic-item" data-id="${topic.id}">
            <div class="topic-name">
                <i class="fas fa-project-diagram"></i>
                <span>${topic.name}</span>
            </div>
            <div class="topic-status">
                <span class="status ${getTopicStatusClass(topic.id)}">
                    ${getTopicStatus(topic.id)}
                </span>
            </div>
        </li>
    `).join('');
}

function loadDataStructureDetail(topicId) {
    const topic = topicData['data-structures'].topics.find(t => t.id === topicId);
    if (!topic) return;
    
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create back button
    const backButton = document.createElement('button');
    backButton.className = 'btn btn-secondary back-button';
    backButton.innerHTML = '<i class="fas fa-arrow-left"></i> Back to Data Structures';
    backButton.addEventListener('click', () => loadDataStructures());
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
            <h4>Time & Space Complexity</h4>
            <table class="complexity-table">
                <thead>
                    <tr>
                        <th>Operation</th>
                        <th>Time Complexity</th>
                    </tr>
                </thead>
                <tbody>
                    ${Object.entries(topic.complexity).map(([op, complexity]) => `
                        <tr>
                            <td>${formatOperationName(op)}</td>
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

// Helper function to format operation names
function formatOperationName(operation) {
    return operation
        .replace(/([A-Z])/g, ' $1')
        .replace(/^./, str => str.toUpperCase());
}
