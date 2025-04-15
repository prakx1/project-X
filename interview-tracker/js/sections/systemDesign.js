// System Design section handler

function loadSystemDesign() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>System Design</h2>
        <p>Master system design concepts for technical interviews</p>
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
                <div class="progress" style="width: ${appState.progress['system-design']}%"></div>
            </div>
            <span>${appState.progress['system-design']}% Complete</span>
        </div>
    `;
    contentDiv.appendChild(progressCard);
    
    // Create topics grid
    const topicsGrid = document.createElement('div');
    topicsGrid.className = 'topics-grid';
    
    // Get system design topics
    const topics = topicData['system-design'].topics;
    
    // Create cards for each topic
    topics.forEach(topic => {
        const topicCard = document.createElement('div');
        topicCard.className = 'card topic-card';
        topicCard.innerHTML = `
            <div class="card-header">
                <h3 class="card-title">${topic.name}</h3>
                <div class="topic-status">
                    <span class="status ${getTopicStatusClass(topic.id)}">
                        ${getTopicStatus(topic.id)}
                    </span>
                </div>
            </div>
            <div class="card-body">
                <p>${topic.description}</p>
                ${topic.implementations && topic.implementations.length > 0 ? `
                <h4>Resources</h4>
                <ul class="implementation-list">
                    ${topic.implementations.map(impl => `
                        <li class="implementation-item" data-id="${impl.id}">
                            <i class="fas fa-file-alt"></i> ${impl.name}
                        </li>
                    `).join('')}
                </ul>
                ` : ''}
                <button class="btn btn-primary view-topic-btn" data-id="${topic.id}">Study This Topic</button>
            </div>
        `;
        topicsGrid.appendChild(topicCard);
    });
    
    contentDiv.appendChild(topicsGrid);
    
    // Add event listeners for view buttons
    document.querySelectorAll('.view-topic-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const topicId = this.getAttribute('data-id');
            loadSystemDesignDetail(topicId);
        });
    });
    
    // Add event listeners for implementation items
    document.querySelectorAll('.implementation-item').forEach(item => {
        item.addEventListener('click', function() {
            const implId = this.getAttribute('data-id');
            loadImplementationDetail(implId);
        });
    });
    
    // Add System Design Interview Tips
    const tipsCard = document.createElement('div');
    tipsCard.className = 'card';
    tipsCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">System Design Interview Tips</h3>
        </div>
        <div class="card-body">
            <h4>Step-by-Step Approach</h4>
            <ol>
                <li><strong>Clarify Requirements</strong>: Ask questions to understand the problem scope</li>
                <li><strong>Define API</strong>: Define the key API endpoints</li>
                <li><strong>Estimate Scale</strong>: Determine throughput, storage, bandwidth requirements</li>
                <li><strong>High-Level Design</strong>: Create a basic architecture with key components</li>
                <li><strong>Detailed Design</strong>: Dive into specific components and their interactions</li>
                <li><strong>Identify Bottlenecks</strong>: Address potential scaling issues and solutions</li>
            </ol>
            
            <h4>Common System Design Topics</h4>
            <ul>
                <li>URL Shortener</li>
                <li>Social Media Feed</li>
                <li>Video Streaming Service</li>
                <li>Rate Limiter</li>
                <li>Chat Application</li>
                <li>Distributed File System</li>
                <li>Web Crawler</li>
                <li>Notification System</li>
            </ul>
        </div>
    `;
    contentDiv.appendChild(tipsCard);
}

function loadSystemDesignDetail(topicId) {
    const topic = topicData['system-design'].topics.find(t => t.id === topicId);
    if (!topic) return;
    
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create back button
    const backButton = document.createElement('button');
    backButton.className = 'btn btn-secondary back-button';
    backButton.innerHTML = '<i class="fas fa-arrow-left"></i> Back to System Design';
    backButton.addEventListener('click', () => loadSystemDesign());
    contentDiv.appendChild(backButton);
    
    // Create topic detail card
    const detailCard = document.createElement('div');
    detailCard.className = 'card';
    
    // Header with topic name and status
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
            <p>${topic.description}</p>
    `;
    
    // Add system design specific content based on topic
    if (topicId === 'sd-scalability') {
        cardContent += `
            <h4>Key Scalability Concepts</h4>
            
            <h5>Horizontal vs. Vertical Scaling</h5>
            <div class="scaling-comparison">
                <div class="scaling-type">
                    <h6>Horizontal Scaling (Scale Out)</h6>
                    <p>Adding more machines to the resource pool</p>
                    <ul>
                        <li>Advantages: Improved resilience, virtually unlimited scaling</li>
                        <li>Disadvantages: Distributed system complexity, data consistency challenges</li>
                    </ul>
                </div>
                <div class="scaling-type">
                    <h6>Vertical Scaling (Scale Up)</h6>
                    <p>Adding more power (CPU, RAM) to existing machines</p>
                    <ul>
                        <li>Advantages: Simpler to implement, no distributed complexity</li>
                        <li>Disadvantages: Hardware limits, single point of failure</li>
                    </ul>
                </div>
            </div>
            
            <h5>Load Balancing</h5>
            <p>Distributes incoming traffic across multiple servers to prevent overload</p>
            <ul>
                <li><strong>Algorithms</strong>: Round Robin, Least Connections, Weighted Round Robin</li>
                <li><strong>Benefits</strong>: Improved responsiveness, availability, and fault tolerance</li>
                <li><strong>Implementations</strong>: ELB, NGINX, HAProxy</li>
            </ul>
            
            <h5>Database Scaling</h5>
            <ul>
                <li><strong>Replication</strong>: Master-slave, master-master configurations</li>
                <li><strong>Sharding</strong>: Horizontally partitioning data across multiple databases</li>
                <li><strong>Denormalization</strong>: Adding redundant data to avoid joins</li>
                <li><strong>NoSQL</strong>: Using non-relational databases for specific use cases</li>
            </ul>
        `;
    } else if (topicId === 'sd-databases') {
        cardContent += `
            <h4>Database Design Concepts</h4>
            
            <h5>CAP Theorem</h5>
            <p>A distributed system can only provide two of three guarantees:</p>
            <ul>
                <li><strong>Consistency</strong>: All nodes see the same data at the same time</li>
                <li><strong>Availability</strong>: Every request receives a response</li>
                <li><strong>Partition Tolerance</strong>: System continues to operate despite network partitions</li>
            </ul>
            
            <h5>SQL vs. NoSQL</h5>
            <table class="complexity-table">
                <thead>
                    <tr>
                        <th>Feature</th>
                        <th>SQL</th>
                        <th>NoSQL</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Schema</td>
                        <td>Fixed, predefined</td>
                        <td>Dynamic, flexible</td>
                    </tr>
                    <tr>
                        <td>Scaling</td>
                        <td>Vertical (primarily)</td>
                        <td>Horizontal (easily)</td>
                    </tr>
                    <tr>
                        <td>ACID Properties</td>
                        <td>Supported</td>
                        <td>Often relaxed for performance</td>
                    </tr>
                    <tr>
                        <td>Use Cases</td>
                        <td>Complex queries, transactions</td>
                        <td>High throughput, variable data</td>
                    </tr>
                    <tr>
                        <td>Examples</td>
                        <td>MySQL, PostgreSQL</td>
                        <td>MongoDB, Cassandra, Redis</td>
                    </tr>
                </tbody>
            </table>
            
            <h5>Database Sharding Strategies</h5>
            <ul>
                <li><strong>Horizontal Sharding</strong>: Partitioning based on a shard key</li>
                <li><strong>Vertical Sharding</strong>: Splitting tables by columns</li>
                <li><strong>Directory-Based Sharding</strong>: Using a lookup service</li>
                <li><strong>Challenges</strong>: Joins across shards, resharding, hotspots</li>
            </ul>
        `;
    } else if (topicId === 'sd-caching') {
        cardContent += `
            <h4>Caching Strategies</h4>
            
            <h5>Common Caching Patterns</h5>
            <ul>
                <li><strong>Cache-Aside</strong>: Application checks cache first, loads from DB if not found</li>
                <li><strong>Read-Through</strong>: Cache handles the DB interaction if cache miss</li>
                <li><strong>Write-Through</strong>: Data written to cache and DB simultaneously</li>
                <li><strong>Write-Behind</strong>: Data written to cache, then asynchronously to DB</li>
                <li><strong>Refresh-Ahead</strong>: Cache proactively refreshes before expiration</li>
            </ul>
            
            <h5>Cache Eviction Policies</h5>
            <ul>
                <li><strong>LRU (Least Recently Used)</strong>: Evicts items that haven't been accessed recently</li>
                <li><strong>LFU (Least Frequently Used)</strong>: Evicts items accessed least frequently</li>
                <li><strong>FIFO (First In First Out)</strong>: Evicts oldest items first</li>
                <li><strong>TTL (Time To Live)</strong>: Evicts items after a specified time period</li>
            </ul>
            
            <h5>Caching Implementations</h5>
            <ul>
                <li><strong>Client-Side</strong>: Browser cache, mobile app cache</li>
                <li><strong>CDN</strong>: Edge caching for static content</li>
                <li><strong>Application Cache</strong>: In-memory caching (e.g., Caffeine, Guava)</li>
                <li><strong>Distributed Cache</strong>: Redis, Memcached</li>
                <li><strong>Database Cache</strong>: Query cache, buffer pool</li>
            </ul>
            
            <h5>Cache Consistency Challenges</h5>
            <ul>
                <li><strong>Stale Data</strong>: Cache invalidation strategies (TTL, event-based)</li>
                <li><strong>Thundering Herd</strong>: Many simultaneous requests if cache entry expires</li>
                <li><strong>Cache Penetration</strong>: Queries for non-existent data bypass cache</li>
                <li><strong>Cache Breakdown</strong>: Hot key expiration causing system overload</li>
            </ul>
        `;
    } else if (topicId === 'sd-microservices') {
        cardContent += `
            <h4>Microservices Architecture</h4>
            
            <h5>Key Principles</h5>
            <ul>
                <li><strong>Single Responsibility</strong>: Each service focuses on a specific business capability</li>
                <li><strong>Independence</strong>: Services can be developed, deployed, and scaled independently</li>
                <li><strong>Decentralization</strong>: Decentralized data management and governance</li>
                <li><strong>Resilience</strong>: Failure of one service doesn't crash the entire system</li>
                <li><strong>Observability</strong>: Monitoring, logging, and tracing across services</li>
            </ul>
            
            <h5>Communication Patterns</h5>
            <ul>
                <li><strong>Synchronous</strong>: REST, gRPC, GraphQL</li>
                <li><strong>Asynchronous</strong>: Message queues (RabbitMQ, Kafka), event streaming</li>
                <li><strong>Service Mesh</strong>: Infrastructure layer for service-to-service communication</li>
            </ul>
            
            <h5>Microservices Challenges</h5>
            <ul>
                <li><strong>Distributed Transactions</strong>: Maintaining data consistency across services</li>
                <li><strong>Service Discovery</strong>: Finding and connecting to dynamic service instances</li>
                <li><strong>Configuration Management</strong>: Managing configurations across services</li>
                <li><strong>Testing</strong>: Testing interactions between services</li>
                <li><strong>Operational Complexity</strong>: Monitoring and debugging distributed systems</li>
            </ul>
            
            <h5>Design Patterns</h5>
            <ul>
                <li><strong>API Gateway</strong>: Single entry point for clients</li>
                <li><strong>Circuit Breaker</strong>: Prevents cascading failures</li>
                <li><strong>Service Registry</strong>: Keeps track of service instances</li>
                <li><strong>CQRS</strong>: Separates read and write operations</li>
                <li><strong>Event Sourcing</strong>: Stores state changes as events</li>
                <li><strong>Saga</strong>: Manages distributed transactions</li>
            </ul>
        `;
    }
    
    // Add implementations if available
    if (topic.implementations && topic.implementations.length > 0) {
        cardContent += `
            <h4>Resources</h4>
            <ul class="implementation-list">
                ${topic.implementations.map(impl => `
                    <li class="implementation-item" data-id="${impl.id}">
                        <i class="fas fa-file-alt"></i> ${impl.name}
                    </li>
                `).join('')}
            </ul>
        `;
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
