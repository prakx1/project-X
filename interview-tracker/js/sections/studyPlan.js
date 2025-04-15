// Study Plan section handler

function loadStudyPlan() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Study Plan</h2>
        <p>Create and track your technical interview preparation plan</p>
    `;
    contentDiv.appendChild(header);
    
    // Create target date card
    const targetDateCard = document.createElement('div');
    targetDateCard.className = 'card';
    targetDateCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Target Interview Date</h3>
        </div>
        <div class="card-body">
            <div class="form-group">
                <label for="target-date">When is your target interview date?</label>
                <input type="date" id="target-date" value="${appState.settings.targetDate || ''}">
            </div>
            <p id="days-until-target">
                ${calculateDaysUntilTarget() !== '--' ? `You have <strong>${calculateDaysUntilTarget()} days</strong> until your target date.` : 'Set your target interview date to create a personalized study plan.'}
            </p>
            <button class="btn btn-primary" id="generate-plan-btn">Generate Study Plan</button>
        </div>
    `;
    contentDiv.appendChild(targetDateCard);
    
    // Create study plan card
    const studyPlanCard = document.createElement('div');
    studyPlanCard.className = 'card';
    studyPlanCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Your Study Plan</h3>
        </div>
        <div class="card-body" id="study-plan-container">
            ${generateStudyPlanHTML()}
        </div>
    `;
    contentDiv.appendChild(studyPlanCard);
    
    // Add recommended focus areas card
    const focusAreasCard = document.createElement('div');
    focusAreasCard.className = 'card';
    focusAreasCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Recommended Focus Areas</h3>
        </div>
        <div class="card-body">
            ${generateFocusAreasHTML()}
        </div>
    `;
    contentDiv.appendChild(focusAreasCard);
    
    // Add interview preparation timeline card
    const timelineCard = document.createElement('div');
    timelineCard.className = 'card';
    timelineCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Interview Preparation Timeline</h3>
        </div>
        <div class="card-body">
            <div class="timeline">
                <div class="timeline-item">
                    <div class="timeline-marker far-out">
                        <span>2+ Months</span>
                    </div>
                    <div class="timeline-content">
                        <h4>Build Fundamentals</h4>
                        <ul>
                            <li>Master core data structures: Arrays, Linked Lists, Stacks, Queues</li>
                            <li>Learn basic algorithms: Sorting, Searching</li>
                            <li>Study Big O notation and complexity analysis</li>
                            <li>Start implementing basic data structures from scratch</li>
                            <li>Begin solving easy LeetCode problems</li>
                        </ul>
                    </div>
                </div>
                
                <div class="timeline-item">
                    <div class="timeline-marker month-out">
                        <span>1 Month</span>
                    </div>
                    <div class="timeline-content">
                        <h4>Deepen Knowledge</h4>
                        <ul>
                            <li>Master advanced data structures: Trees, Graphs, Heaps, Hash Tables</li>
                            <li>Study common algorithms: BFS/DFS, Dynamic Programming, Greedy</li>
                            <li>Begin system design basics (for senior roles)</li>
                            <li>Solve medium LeetCode problems regularly</li>
                            <li>Start creating STAR stories for behavioral questions</li>
                        </ul>
                    </div>
                </div>
                
                <div class="timeline-item">
                    <div class="timeline-marker weeks-out">
                        <span>2 Weeks</span>
                    </div>
                    <div class="timeline-content">
                        <h4>Targeted Practice</h4>
                        <ul>
                            <li>Focus on company-specific problem patterns</li>
                            <li>Practice medium/hard LeetCode problems</li>
                            <li>Do mock interviews (with peers or services)</li>
                            <li>Refine system design knowledge</li>
                            <li>Review your implementations and solutions</li>
                        </ul>
                    </div>
                </div>
                
                <div class="timeline-item">
                    <div class="timeline-marker days-out">
                        <span>3-5 Days</span>
                    </div>
                    <div class="timeline-content">
                        <h4>Final Review</h4>
                        <ul>
                            <li>Review your notes and solutions</li>
                            <li>Refresh memory on core concepts</li>
                            <li>Practice explaining your thought process out loud</li>
                            <li>Light practice only - avoid burnout</li>
                            <li>Prepare logistically: confirm interview details, prepare questions for interviewers</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    `;
    contentDiv.appendChild(timelineCard);
    
    // Add event listeners
    document.getElementById('target-date').addEventListener('change', function() {
        appState.settings.targetDate = this.value;
        saveState();
        document.getElementById('days-until-target').innerHTML = 
            `You have <strong>${calculateDaysUntilTarget()} days</strong> until your target date.`;
    });
    
    document.getElementById('generate-plan-btn').addEventListener('click', function() {
        generateNewStudyPlan();
    });
}

function generateStudyPlanHTML() {
    if (!appState.studyPlan || appState.studyPlan.length === 0) {
        return '<p>No study plan generated yet. Set your target interview date and click "Generate Study Plan".</p>';
    }
    
    // Sort plan by date
    const sortedPlan = [...appState.studyPlan].sort((a, b) => new Date(a.date) - new Date(b.date));
    
    // Group by week for better organization
    const planByWeek = {};
    sortedPlan.forEach(day => {
        const date = new Date(day.date);
        const weekStart = new Date(date);
        weekStart.setDate(date.getDate() - date.getDay()); // Start of week (Sunday)
        const weekKey = weekStart.toISOString().split('T')[0];
        
        if (!planByWeek[weekKey]) {
            planByWeek[weekKey] = [];
        }
        planByWeek[weekKey].push(day);
    });
    
    // Generate HTML for each week
    let html = '';
    Object.keys(planByWeek).forEach(weekKey => {
        const week = planByWeek[weekKey];
        const weekStart = new Date(weekKey);
        const weekEnd = new Date(weekKey);
        weekEnd.setDate(weekStart.getDate() + 6);
        
        html += `
            <div class="study-week">
                <h4>Week of ${formatDate(weekStart)} - ${formatDate(weekEnd)}</h4>
                <div class="study-days">
        `;
        
        week.forEach(day => {
            const date = new Date(day.date);
            
            html += `
                <div class="study-day">
                    <div class="day-header">
                        <span class="day-date">${formatDate(date)}</span>
                        <span class="day-name">${getDayName(date)}</span>
                    </div>
                    <div class="day-topics">
            `;
            
            if (day.topics && day.topics.length > 0) {
                html += `<ul class="topic-list">`;
                day.topics.forEach(topic => {
                    const isCompleted = appState.completedTopics.includes(topic.id);
                    html += `
                        <li class="study-topic ${isCompleted ? 'completed' : ''}">
                            <label class="checkbox-container">
                                <input type="checkbox" class="topic-checkbox" data-id="${topic.id}" ${isCompleted ? 'checked' : ''}>
                                <span class="checkmark"></span>
                                ${topic.name}
                            </label>
                            <span class="topic-category">${formatCategoryName(topic.category)}</span>
                        </li>
                    `;
                });
                html += `</ul>`;
            } else {
                html += `<p class="no-topics">No topics scheduled for this day</p>`;
            }
            
            html += `
                    </div>
                </div>
            `;
        });
        
        html += `
                </div>
            </div>
        `;
    });
    
    return html;
}

function generateFocusAreasHTML() {
    // Calculate progress per category
    const progressByCategory = {};
    for (const category in appState.progress) {
        progressByCategory[category] = appState.progress[category] || 0;
    }
    
    // Sort categories by progress (ascending)
    const sortedCategories = Object.keys(progressByCategory).sort((a, b) => {
        return progressByCategory[a] - progressByCategory[b];
    });
    
    // Get the three categories with lowest progress
    const focusCategories = sortedCategories.slice(0, 3);
    
    // Generate recommendations based on focus categories
    let html = `<div class="focus-areas">`;
    
    focusCategories.forEach(category => {
        const progress = progressByCategory[category];
        const categoryName = formatCategoryName(category);
        
        html += `
            <div class="focus-area">
                <h4>${categoryName}</h4>
                <div class="progress-bar">
                    <div class="progress" style="width: ${progress}%"></div>
                </div>
                <span>${progress}% Complete</span>
                <div class="focus-recommendations">
                    <h5>Recommended Next Steps:</h5>
                    <ul>
                        ${generateRecommendationsForCategory(category)}
                    </ul>
                </div>
            </div>
        `;
    });
    
    html += `</div>`;
    return html;
}

function generateRecommendationsForCategory(category) {
    // Get topics for the category that aren't completed
    const topics = topicData[category] && topicData[category].topics
        ? topicData[category].topics.filter(topic => !appState.completedTopics.includes(topic.id))
        : [];
    
    if (topics.length === 0) {
        return `<li>All topics in this category are completed! Great job!</li>`;
    }
    
    // Take up to 3 topics
    const recommendedTopics = topics.slice(0, 3);
    
    return recommendedTopics.map(topic => `
        <li>
            <a href="#" class="topic-link" data-id="${topic.id}" data-category="${category}">
                Study ${topic.name}
            </a>
        </li>
    `).join('');
}

function generateNewStudyPlan() {
    const targetDate = appState.settings.targetDate;
    if (!targetDate) {
        alert('Please set a target interview date first');
        return;
    }
    
    // Get completed topics
    const completedTopicIds = appState.completedTopics || [];
    
    // Generate plan using the helper function from data.js
    const result = generateStudyPlan(targetDate, completedTopicIds);
    
    if (result.error) {
        alert(result.error);
        return;
    }
    
    // Update app state
    appState.studyPlan = result.plan;
    saveState();
    
    // Update UI
    document.getElementById('study-plan-container').innerHTML = generateStudyPlanHTML();
    
    // Add event listeners for topic checkboxes
    document.querySelectorAll('.topic-checkbox').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const topicId = this.getAttribute('data-id');
            updateTopicStatus(topicId, this.checked ? 'completed' : 'not-started');
            
            // Update UI to reflect changes
            if (this.checked) {
                this.closest('.study-topic').classList.add('completed');
            } else {
                this.closest('.study-topic').classList.remove('completed');
            }
        });
    });
    
    // Add event listeners for topic links
    document.querySelectorAll('.topic-link').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const topicId = this.getAttribute('data-id');
            const category = this.getAttribute('data-category');
            
            // Navigate to the appropriate section and load topic detail
            appState.currentSection = category;
            saveState();
            
            // Update active navigation item
            document.querySelectorAll('.nav-links li').forEach(item => {
                item.classList.remove('active');
            });
            document.querySelector(`.nav-links li[data-section="${category}"]`).classList.add('active');
            
            // Load the content for the section and topic
            loadContent(category);
            
            // Call the appropriate function to load topic detail
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
            }
        });
    });
}

// Helper function to format date
function formatDate(date) {
    const options = { month: 'short', day: 'numeric' };
    return date.toLocaleDateString('en-US', options);
}

// Helper function to get day name
function getDayName(date) {
    const options = { weekday: 'short' };
    return date.toLocaleDateString('en-US', options);
}

// Helper function to format category name
function formatCategoryName(category) {
    return category
        .split('-')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}
