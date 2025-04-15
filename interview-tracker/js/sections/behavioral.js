// Behavioral Interview section handler

function loadBehavioral() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Behavioral Interview Preparation</h2>
        <p>Prepare for behavioral interview questions using the STAR method</p>
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
                <div class="progress" style="width: ${appState.progress['behavioral']}%"></div>
            </div>
            <span>${appState.progress['behavioral']}% Complete</span>
        </div>
    `;
    contentDiv.appendChild(progressCard);
    
    // Create STAR method card
    const starMethodCard = document.createElement('div');
    starMethodCard.className = 'card';
    starMethodCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">STAR Method</h3>
        </div>
        <div class="card-body">
            <p>The STAR method is a structured approach to answering behavioral interview questions:</p>
            <div class="star-method">
                <div class="star-component">
                    <h4>S - Situation</h4>
                    <p>Describe the context or situation you were in.</p>
                </div>
                <div class="star-component">
                    <h4>T - Task</h4>
                    <p>Explain the task or challenge you faced.</p>
                </div>
                <div class="star-component">
                    <h4>A - Action</h4>
                    <p>Detail the specific actions you took to address the challenge.</p>
                </div>
                <div class="star-component">
                    <h4>R - Result</h4>
                    <p>Share the outcomes of your actions, quantifying when possible.</p>
                </div>
            </div>
        </div>
    `;
    contentDiv.appendChild(starMethodCard);
    
    // Create common questions card
    const questionsCard = document.createElement('div');
    questionsCard.className = 'card';
    questionsCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Common Behavioral Questions</h3>
        </div>
        <div class="card-body">
            <div class="behavioral-categories">
                <div class="category-tab" data-category="leadership">Leadership</div>
                <div class="category-tab" data-category="teamwork">Teamwork</div>
                <div class="category-tab" data-category="problem-solving">Problem Solving</div>
                <div class="category-tab" data-category="failure">Handling Failure</div>
                <div class="category-tab" data-category="conflict">Conflict Resolution</div>
            </div>
            
            <div class="questions-container" id="questions-container">
                <!-- Questions will be loaded here based on category selection -->
                <p class="select-category-prompt">Select a category above to view common questions</p>
            </div>
        </div>
    `;
    contentDiv.appendChild(questionsCard);
    
    // Create your stories card
    const storiesCard = document.createElement('div');
    storiesCard.className = 'card';
    storiesCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Your STAR Stories</h3>
            <button class="btn btn-primary" id="add-story-btn">Add Story</button>
        </div>
        <div class="card-body">
            <div id="user-stories">
                ${generateUserStories()}
            </div>
        </div>
    `;
    contentDiv.appendChild(storiesCard);
    
    // Create additional resources card
    const resourcesCard = document.createElement('div');
    resourcesCard.className = 'card';
    resourcesCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Additional Resources</h3>
        </div>
        <div class="card-body">
            <ul class="implementation-list">
                ${topicData['behavioral'].topics.map(topic => 
                    topic.implementations ? topic.implementations.map(impl => `
                        <li class="implementation-item" data-id="${impl.id}">
                            <i class="fas fa-file-alt"></i> ${impl.name}
                        </li>
                    `).join('') : ''
                ).join('')}
            </ul>
        </div>
    `;
    contentDiv.appendChild(resourcesCard);
    
    // Add event listeners for category tabs
    document.querySelectorAll('.category-tab').forEach(tab => {
        tab.addEventListener('click', function() {
            // Remove active class from all tabs
            document.querySelectorAll('.category-tab').forEach(t => t.classList.remove('active'));
            
            // Add active class to clicked tab
            this.classList.add('active');
            
            // Load questions for selected category
            const category = this.getAttribute('data-category');
            loadBehavioralQuestions(category);
        });
    });
    
    // Add event listener for add story button
    document.getElementById('add-story-btn').addEventListener('click', function() {
        showAddStoryModal();
    });
    
    // Add event listeners for implementation items
    document.querySelectorAll('.implementation-item').forEach(item => {
        item.addEventListener('click', function() {
            const implId = this.getAttribute('data-id');
            loadImplementationDetail(implId);
        });
    });
}

function loadBehavioralQuestions(category) {
    const questionsContainer = document.getElementById('questions-container');
    
    // Define questions by category
    const questionsByCategory = {
        leadership: [
            "Tell me about a time when you led a team through a difficult project.",
            "Describe a situation where you had to make an unpopular decision.",
            "How have you influenced someone to adopt your way of thinking?",
            "Give an example of how you've grown as a leader.",
            "Tell me about a time when you delegated tasks effectively."
        ],
        teamwork: [
            "Give an example of how you worked effectively in a team.",
            "Tell me about a time when you had a conflict with a team member.",
            "How do you handle working with people from different backgrounds?",
            "Describe a situation where you had to step up to help your team.",
            "Tell me about a time you had to collaborate with a difficult coworker."
        ],
        "problem-solving": [
            "Describe a complex problem you solved.",
            "Tell me about a time when you faced an unexpected challenge.",
            "How do you approach problems that don't have clear solutions?",
            "Describe a situation where you had to analyze data to solve a problem.",
            "Tell me about a time you had to make a decision with incomplete information."
        ],
        failure: [
            "Describe a significant mistake you made and what you learned from it.",
            "Tell me about a time when you missed a deadline.",
            "How do you handle criticism of your work?",
            "Describe a project or idea that was implemented primarily because of your efforts.",
            "Tell me about a time you received constructive feedback and how you responded."
        ],
        conflict: [
            "Tell me about a time you had a conflict at work.",
            "How have you handled a disagreement with your manager?",
            "Describe how you've worked with a difficult client or customer.",
            "Tell me about a time you had to negotiate with someone.",
            "How do you handle situations when team members have different opinions?"
        ]
    };
    
    // Get questions for selected category
    const questions = questionsByCategory[category] || [];
    
    // Generate HTML for questions
    let questionsHTML = '';
    if (questions.length > 0) {
        questionsHTML = `
            <div class="category-questions">
                <h4>${formatCategoryName(category)} Questions</h4>
                <ul class="questions-list">
                    ${questions.map(question => `
                        <li class="question-item">
                            <div class="question-text">${question}</div>
                            <div class="question-actions">
                                <button class="btn btn-sm btn-primary prepare-answer-btn" data-question="${encodeURIComponent(question)}">
                                    Prepare Answer
                                </button>
                            </div>
                        </li>
                    `).join('')}
                </ul>
            </div>
        `;
    } else {
        questionsHTML = '<p>No questions available for this category</p>';
    }
    
    questionsContainer.innerHTML = questionsHTML;
    
    // Add event listeners for prepare answer buttons
    document.querySelectorAll('.prepare-answer-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const question = decodeURIComponent(this.getAttribute('data-question'));
            showPrepareAnswerModal(question, category);
        });
    });
}

function showAddStoryModal() {
    // Create modal element
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Add STAR Story</h3>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
                <form id="story-form">
                    <div class="form-group">
                        <label for="story-title">Title</label>
                        <input type="text" id="story-title" required placeholder="E.g., Project Leadership Success">
                    </div>
                    <div class="form-group">
                        <label for="story-category">Category</label>
                        <select id="story-category" required>
                            <option value="leadership">Leadership</option>
                            <option value="teamwork">Teamwork</option>
                            <option value="problem-solving">Problem Solving</option>
                            <option value="failure">Handling Failure</option>
                            <option value="conflict">Conflict Resolution</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="story-situation">Situation</label>
                        <textarea id="story-situation" required placeholder="Describe the context or situation you were in"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="story-task">Task</label>
                        <textarea id="story-task" required placeholder="Explain the task or challenge you faced"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="story-action">Action</label>
                        <textarea id="story-action" required placeholder="Detail the specific actions you took"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="story-result">Result</label>
                        <textarea id="story-result" required placeholder="Share the outcomes of your actions"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="story-questions">Applicable Questions</label>
                        <textarea id="story-questions" placeholder="List questions this story could answer (optional)"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Save Story</button>
                </form>
            </div>
        </div>
    `;
    
    // Add modal to page
    document.body.appendChild(modal);
    
    // Add event listener for close button
    modal.querySelector('.close-modal').addEventListener('click', function() {
        document.body.removeChild(modal);
    });
    
    // Add event listener for form submission
    modal.querySelector('#story-form').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Get form values
        const title = document.getElementById('story-title').value;
        const category = document.getElementById('story-category').value;
        const situation = document.getElementById('story-situation').value;
        const task = document.getElementById('story-task').value;
        const action = document.getElementById('story-action').value;
        const result = document.getElementById('story-result').value;
        const questions = document.getElementById('story-questions').value;
        
        // Create new story object
        const newStory = {
            id: 'story-' + Date.now(),
            title,
            category,
            situation,
            task,
            action,
            result,
            questions: questions.split('\n').filter(q => q.trim() !== '')
        };
        
        // Add to app state
        if (!appState.starStories) {
            appState.starStories = [];
        }
        appState.starStories.push(newStory);
        saveState();
        
        // Update UI
        document.getElementById('user-stories').innerHTML = generateUserStories();
        
        // Close modal
        document.body.removeChild(modal);
        
        // Update progress
        updateBehavioralProgress();
    });
}

function showPrepareAnswerModal(question, category) {
    // Create modal element
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Prepare STAR Answer</h3>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
                <h4>Question: ${question}</h4>
                <p><strong>Category:</strong> ${formatCategoryName(category)}</p>
                
                <form id="answer-form">
                    <div class="form-group">
                        <label for="answer-situation">Situation</label>
                        <textarea id="answer-situation" required placeholder="Describe the context or situation you were in"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="answer-task">Task</label>
                        <textarea id="answer-task" required placeholder="Explain the task or challenge you faced"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="answer-action">Action</label>
                        <textarea id="answer-action" required placeholder="Detail the specific actions you took"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="answer-result">Result</label>
                        <textarea id="answer-result" required placeholder="Share the outcomes of your actions"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Save Answer</button>
                </form>
                
                ${appState.starStories && appState.starStories.length > 0 ? `
                <div class="existing-stories">
                    <h4>Use Existing Story</h4>
                    <p>You can use one of your existing stories for this question:</p>
                    <ul class="existing-stories-list">
                        ${appState.starStories.map(story => `
                            <li class="existing-story-item" data-id="${story.id}">
                                <strong>${story.title}</strong> (${formatCategoryName(story.category)})
                            </li>
                        `).join('')}
                    </ul>
                </div>
                ` : ''}
            </div>
        </div>
    `;
    
    // Add modal to page
    document.body.appendChild(modal);
    
    // Add event listener for close button
    modal.querySelector('.close-modal').addEventListener('click', function() {
        document.body.removeChild(modal);
    });
    
    // Add event listener for form submission
    modal.querySelector('#answer-form').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Get form values
        const situation = document.getElementById('answer-situation').value;
        const task = document.getElementById('answer-task').value;
        const action = document.getElementById('answer-action').value;
        const result = document.getElementById('answer-result').value;
        
        // Create new story object
        const newStory = {
            id: 'story-' + Date.now(),
            title: question.substring(0, 40) + '...',
            category,
            situation,
            task,
            action,
            result,
            questions: [question]
        };
        
        // Add to app state
        if (!appState.starStories) {
            appState.starStories = [];
        }
        appState.starStories.push(newStory);
        saveState();
        
        // Update UI
        document.getElementById('user-stories').innerHTML = generateUserStories();
        
        // Close modal
        document.body.removeChild(modal);
        
        // Update progress
        updateBehavioralProgress();
    });
    
    // Add event listeners for existing stories
    const existingStoryItems = modal.querySelectorAll('.existing-story-item');
    existingStoryItems.forEach(item => {
        item.addEventListener('click', function() {
            const storyId = this.getAttribute('data-id');
            const story = appState.starStories.find(s => s.id === storyId);
            
            if (story) {
                // Fill form with story details
                document.getElementById('answer-situation').value = story.situation;
                document.getElementById('answer-task').value = story.task;
                document.getElementById('answer-action').value = story.action;
                document.getElementById('answer-result').value = story.result;
                
                // Add this question to the story's applicable questions
                if (!story.questions.includes(question)) {
                    story.questions.push(question);
                    saveState();
                }
            }
        });
    });
}

function generateUserStories() {
    if (!appState.starStories || appState.starStories.length === 0) {
        return '<p>You haven\'t created any STAR stories yet. Click "Add Story" to get started.</p>';
    }
    
    return `
        <ul class="user-stories-list">
            ${appState.starStories.map(story => `
                <li class="user-story-item" data-id="${story.id}">
                    <div class="story-header">
                        <h4>${story.title}</h4>
                        <span class="story-category">${formatCategoryName(story.category)}</span>
                    </div>
                    <div class="story-preview">
                        <strong>S:</strong> ${story.situation.substring(0, 60)}...
                    </div>
                    <div class="story-actions">
                        <button class="btn btn-sm btn-primary view-story-btn" data-id="${story.id}">View</button>
                        <button class="btn btn-sm btn-danger delete-story-btn" data-id="${story.id}">Delete</button>
                    </div>
                </li>
            `).join('')}
        </ul>
    `;
}

function updateBehavioralProgress() {
    // Update based on number of stories created
    const storiesCount = appState.starStories ? appState.starStories.length : 0;
    let progress = 0;
    
    if (storiesCount >= 10) {
        progress = 100;
    } else if (storiesCount > 0) {
        progress = Math.floor((storiesCount / 10) * 100);
    }
    
    appState.progress['behavioral'] = progress;
    saveState();
    
    // Update UI if on behavioral page
    if (appState.currentSection === 'behavioral') {
        const progressBar = document.querySelector('.card .progress');
        const progressText = document.querySelector('.card .progress + span');
        
        if (progressBar && progressText) {
            progressBar.style.width = `${progress}%`;
            progressText.textContent = `${progress}% Complete`;
        }
    }
    
    // Update overall progress
    updateProgressBar();
}

// Helper function to format category name
function formatCategoryName(category) {
    return category
        .split('-')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}
