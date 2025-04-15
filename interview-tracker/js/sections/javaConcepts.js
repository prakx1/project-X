// Java Concepts section handler

function loadJavaConcepts() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Java Concepts</h2>
        <p>Master core Java concepts essential for technical interviews</p>
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
                <div class="progress" style="width: ${appState.progress['java-concepts']}%"></div>
            </div>
            <span>${appState.progress['java-concepts']}% Complete</span>
        </div>
    `;
    contentDiv.appendChild(progressCard);
    
    // Create topics list card
    const topicsCard = document.createElement('div');
    topicsCard.className = 'card';
    topicsCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Java Topics</h3>
        </div>
        <div class="card-body">
            <ul class="topic-list">
                ${generateJavaConceptsList()}
            </ul>
        </div>
    `;
    contentDiv.appendChild(topicsCard);
}

function generateJavaConceptsList() {
    const topics = topicData['java-concepts'].topics;
    
    return topics.map(topic => `
        <li class="topic-item" data-id="${topic.id}">
            <div class="topic-name">
                <i class="fab fa-java"></i>
                <span>${topic.name}</span>
            </div>
            <div class="topic-status">
                <span class="status ${getTopicStatusClass(topic.id)}">
                    ${getTopicStatus(topic.id)}
                </span>
                <button class="btn btn-sm btn-primary view-topic-btn" data-id="${topic.id}">View</button>
            </div>
        </li>
    `).join('');
}

function loadJavaConceptDetail(topicId) {
    const topic = topicData['java-concepts'].topics.find(t => t.id === topicId);
    if (!topic) return;
    
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create back button
    const backButton = document.createElement('button');
    backButton.className = 'btn btn-secondary back-button';
    backButton.innerHTML = '<i class="fas fa-arrow-left"></i> Back to Java Concepts';
    backButton.addEventListener('click', () => loadJavaConcepts());
    contentDiv.appendChild(backButton);
    
    // Create topic detail card
    const detailCard = document.createElement('div');
    detailCard.className = 'card';
    
    // Header with topic name and completion status
    let cardContent = `
        <div class="card-header">
            <h3 class="card-title">${topic.name}</h3>
            <div class="topic-status">
                <select class="topic-status-toggle" data-topic-id="${topic.id}">
                    <option value="not-started" ${!appState.completedTopics.includes(topic.id) ? 'selected' : ''}>Not Started</option>
                    <option value="completed" ${appState.completedTopics.includes(topic.id) ? 'selected' : ''}>Completed</option>
                </select>
            </div>
        </div>
    `;
    
    // Card body with description and implementations
    cardContent += `
        <div class="card-body">
            <p>${topic.description || 'Learn key concepts and implementations of ' + topic.name + ' in Java.'}</p>
    `;
    
    // Add implementations if available
    if (topic.implementations && topic.implementations.length > 0) {
        cardContent += `
            <h4>Implementations</h4>
            <ul class="implementation-list">
                ${topic.implementations.map(impl => `
                    <li class="implementation-item" data-id="${impl.id}">
                        <i class="fas fa-code"></i> ${impl.name}
                    </li>
                `).join('')}
            </ul>
        `;
    } else {
        cardContent += '<p>No implementations available yet.</p>';
    }
    
    // Add resources if available
    if (topic.resources && topic.resources.length > 0) {
        cardContent += `
            <h4>Additional Resources</h4>
            <ul>
                ${topic.resources.map(resource => `
                    <li><a href="${resource.url}" target="_blank">${resource.name}</a></li>
                `).join('')}
            </ul>
        `;
    }
    
    // Add special content for specific topics
    if (topicId === 'java-oop') {
        cardContent += `
            <h4>OOP Principles</h4>
            <div class="oop-principles">
                <div class="principle-card">
                    <h5>Encapsulation</h5>
                    <p>Bundling data with methods that operate on that data, restricting direct access to some object's components.</p>
                    <p><strong>Key concepts:</strong> Access modifiers, getters/setters, immutability</p>
                </div>
                <div class="principle-card">
                    <h5>Inheritance</h5>
                    <p>Mechanism where one class acquires the properties and behaviors of another class.</p>
                    <p><strong>Key concepts:</strong> extends keyword, super keyword, method overriding</p>
                </div>
                <div class="principle-card">
                    <h5>Polymorphism</h5>
                    <p>Ability of an object to take on many forms, providing different implementations of methods.</p>
                    <p><strong>Key concepts:</strong> Method overloading, method overriding, dynamic binding</p>
                </div>
                <div class="principle-card">
                    <h5>Abstraction</h5>
                    <p>Hiding implementation details and showing only functionality to the user.</p>
                    <p><strong>Key concepts:</strong> Abstract classes, interfaces, abstract methods</p>
                </div>
            </div>
        `;
    } else if (topicId === 'java-concurrency') {
        cardContent += `
            <h4>Key Concurrency Concepts</h4>
            <ul>
                <li><strong>Thread creation</strong> - Extending Thread vs. implementing Runnable</li>
                <li><strong>Synchronization</strong> - synchronized keyword, locks, atomic variables</li>
                <li><strong>Thread communication</strong> - wait(), notify(), join()</li>
                <li><strong>Thread pools</strong> - ExecutorService framework</li>
                <li><strong>Concurrent collections</strong> - ConcurrentHashMap, CopyOnWriteArrayList</li>
                <li><strong>CompletableFuture</strong> - Asynchronous programming</li>
            </ul>
        `;
    } else if (topicId === 'java-collections') {
        cardContent += `
            <h4>Java Collections Framework</h4>
            <div class="collections-diagram">
                <img src="https://www.baeldung.com/wp-content/uploads/2020/01/Java-Collections-Framework.png" alt="Java Collections Framework Hierarchy">
            </div>
            <table class="complexity-table">
                <thead>
                    <tr>
                        <th>Collection</th>
                        <th>Implementation</th>
                        <th>Insert</th>
                        <th>Access</th>
                        <th>Search</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>List</td>
                        <td>ArrayList</td>
                        <td>O(1)*</td>
                        <td>O(1)</td>
                        <td>O(n)</td>
                        <td>O(n)</td>
                    </tr>
                    <tr>
                        <td>List</td>
                        <td>LinkedList</td>
                        <td>O(1)</td>
                        <td>O(n)</td>
                        <td>O(n)</td>
                        <td>O(1)**</td>
                    </tr>
                    <tr>
                        <td>Set</td>
                        <td>HashSet</td>
                        <td>O(1)</td>
                        <td>N/A</td>
                        <td>O(1)</td>
                        <td>O(1)</td>
                    </tr>
                    <tr>
                        <td>Set</td>
                        <td>TreeSet</td>
                        <td>O(log n)</td>
                        <td>N/A</td>
                        <td>O(log n)</td>
                        <td>O(log n)</td>
                    </tr>
                    <tr>
                        <td>Map</td>
                        <td>HashMap</td>
                        <td>O(1)</td>
                        <td>O(1)</td>
                        <td>O(1)</td>
                        <td>O(1)</td>
                    </tr>
                    <tr>
                        <td>Map</td>
                        <td>TreeMap</td>
                        <td>O(log n)</td>
                        <td>O(log n)</td>
                        <td>O(log n)</td>
                        <td>O(log n)</td>
                    </tr>
                </tbody>
            </table>
            <p><small>* Amortized time, ** When position is known</small></p>
        `;
    }
    
    cardContent += '</div>'; // Close card-body
    detailCard.innerHTML = cardContent;
    contentDiv.appendChild(detailCard);
    
    // Add event listener for status toggle
    const statusToggle = detailCard.querySelector('.topic-status-toggle');
    if (statusToggle) {
        statusToggle.addEventListener('change', function() {
            updateTopicStatus(topic.id, this.value);
        });
    }
    
    // Add event listeners for implementation items
    const implItems = detailCard.querySelectorAll('.implementation-item');
    implItems.forEach(item => {
        item.addEventListener('click', function() {
            const implId = this.getAttribute('data-id');
            loadImplementationDetail(implId);
        });
    });
}
