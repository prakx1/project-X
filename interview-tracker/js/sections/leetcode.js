// LeetCode section handler

function loadLeetCode() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>LeetCode Problems</h2>
        <p>Track your progress with common LeetCode problems</p>
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
                <div class="progress" style="width: ${appState.progress['leetcode']}%"></div>
            </div>
            <span>${appState.progress['leetcode']}% Complete</span>
        </div>
    `;
    contentDiv.appendChild(progressCard);
    
    // Create filter controls
    const filterControls = document.createElement('div');
    filterControls.className = 'filter-controls';
    filterControls.innerHTML = `
        <div class="filter-group">
            <label for="difficulty-filter">Difficulty:</label>
            <select id="difficulty-filter">
                <option value="all">All</option>
                <option value="Easy">Easy</option>
                <option value="Medium">Medium</option>
                <option value="Hard">Hard</option>
            </select>
        </div>
        <div class="filter-group">
            <label for="tag-filter">Tag:</label>
            <select id="tag-filter">
                <option value="all">All</option>
                <option value="Array">Array</option>
                <option value="String">String</option>
                <option value="Linked List">Linked List</option>
                <option value="Tree">Tree</option>
                <option value="Dynamic Programming">Dynamic Programming</option>
                <option value="Graph">Graph</option>
                <option value="Sorting">Sorting</option>
                <option value="Binary Search">Binary Search</option>
                <option value="Hash Table">Hash Table</option>
            </select>
        </div>
        <div class="filter-group">
            <label for="status-filter">Status:</label>
            <select id="status-filter">
                <option value="all">All</option>
                <option value="completed">Completed</option>
                <option value="not-started">Not Started</option>
            </select>
        </div>
        <button class="btn btn-primary" id="add-problem-btn">Add Problem</button>
    `;
    contentDiv.appendChild(filterControls);
    
    // Create problems table
    const problemsTable = document.createElement('div');
    problemsTable.className = 'card';
    problemsTable.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">LeetCode Problems</h3>
        </div>
        <div class="card-body">
            <table class="problems-table" id="problems-table">
                <thead>
                    <tr>
                        <th>Status</th>
                        <th>Problem</th>
                        <th>Difficulty</th>
                        <th>Tags</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="problems-tbody">
                    <!-- Problems will be loaded here -->
                </tbody>
            </table>
        </div>
    `;
    contentDiv.appendChild(problemsTable);
    
    // Load problems into table
    loadProblemsTable();
    
    // Add event listeners for filters
    document.getElementById('difficulty-filter').addEventListener('change', loadProblemsTable);
    document.getElementById('tag-filter').addEventListener('change', loadProblemsTable);
    document.getElementById('status-filter').addEventListener('change', loadProblemsTable);
    
    // Add event listener for add problem button
    document.getElementById('add-problem-btn').addEventListener('click', showAddProblemModal);
}

function loadProblemsTable() {
    const tbody = document.getElementById('problems-tbody');
    if (!tbody) return;
    
    // Get filter values
    const difficultyFilter = document.getElementById('difficulty-filter').value;
    const tagFilter = document.getElementById('tag-filter').value;
    const statusFilter = document.getElementById('status-filter').value;
    
    // Get problems from app state, or use default from topicData
    const problems = appState.leetcodeProblems || topicData.leetcode.problems;
    
    // Filter problems based on selected filters
    const filteredProblems = problems.filter(problem => {
        const difficultyMatch = difficultyFilter === 'all' || problem.difficulty === difficultyFilter;
        const tagMatch = tagFilter === 'all' || (problem.tags && problem.tags.includes(tagFilter));
        
        let statusMatch = true;
        if (statusFilter === 'completed') {
            statusMatch = appState.completedLeetcode && appState.completedLeetcode.includes(problem.id);
        } else if (statusFilter === 'not-started') {
            statusMatch = !appState.completedLeetcode || !appState.completedLeetcode.includes(problem.id);
        }
        
        return difficultyMatch && tagMatch && statusMatch;
    });
    
    // Generate table rows
    let html = '';
    if (filteredProblems.length === 0) {
        html = `
            <tr>
                <td colspan="5" class="no-problems">No problems match the selected filters.</td>
            </tr>
        `;
    } else {
        html = filteredProblems.map(problem => {
            const isCompleted = appState.completedLeetcode && appState.completedLeetcode.includes(problem.id);
            
            return `
                <tr>
                    <td>
                        <input type="checkbox" class="problem-checkbox" data-id="${problem.id}" 
                            ${isCompleted ? 'checked' : ''}>
                    </td>
                    <td>
                        <a href="${problem.link}" target="_blank" class="problem-link">${problem.name}</a>
                    </td>
                    <td class="difficulty-cell ${problem.difficulty.toLowerCase()}">
                        ${problem.difficulty}
                    </td>
                    <td class="tags-cell">
                        ${problem.tags ? problem.tags.map(tag => `<span class="tag">${tag}</span>`).join('') : ''}
                    </td>
                    <td class="actions-cell">
                        <button class="btn btn-sm btn-primary view-solution-btn" data-id="${problem.id}">
                            ${problem.solution ? 'View Solution' : 'Add Solution'}
                        </button>
                    </td>
                </tr>
            `;
        }).join('');
    }
    
    tbody.innerHTML = html;
    
    // Add event listeners for checkboxes
    document.querySelectorAll('.problem-checkbox').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const problemId = this.getAttribute('data-id');
            updateLeetCodeProblemStatus(problemId, this.checked);
        });
    });
    
    // Add event listeners for solution buttons
    document.querySelectorAll('.view-solution-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const problemId = this.getAttribute('data-id');
            const problem = problems.find(p => p.id === problemId);
            if (problem) {
                showSolutionModal(problem);
            }
        });
    });
}

function updateLeetCodeProblemStatus(problemId, isCompleted) {
    // Initialize if not exists
    if (!appState.completedLeetcode) {
        appState.completedLeetcode = [];
    }
    
    if (isCompleted) {
        // Add to completed list if not already there
        if (!appState.completedLeetcode.includes(problemId)) {
            appState.completedLeetcode.push(problemId);
        }
    } else {
        // Remove from completed list
        appState.completedLeetcode = appState.completedLeetcode.filter(id => id !== problemId);
    }
    
    // Update progress
    updateLeetCodeProgress();
    
    // Save state
    saveState();
}

function updateLeetCodeProgress() {
    const totalProblems = appState.leetcodeProblems ? appState.leetcodeProblems.length : topicData.leetcode.problems.length;
    const completedCount = appState.completedLeetcode ? appState.completedLeetcode.length : 0;
    
    const progress = totalProblems > 0 ? Math.floor((completedCount / totalProblems) * 100) : 0;
    
    appState.progress['leetcode'] = progress;
    
    // Update UI if on leetcode page
    if (appState.currentSection === 'leetcode') {
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

function showAddProblemModal() {
    // Create modal element
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3>Add LeetCode Problem</h3>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
                <form id="problem-form">
                    <div class="form-group">
                        <label for="problem-name">Problem Name</label>
                        <input type="text" id="problem-name" required>
                    </div>
                    <div class="form-group">
                        <label for="problem-link">LeetCode Link</label>
                        <input type="url" id="problem-link" required placeholder="https://leetcode.com/problems/...">
                    </div>
                    <div class="form-group">
                        <label for="problem-difficulty">Difficulty</label>
                        <select id="problem-difficulty" required>
                            <option value="Easy">Easy</option>
                            <option value="Medium">Medium</option>
                            <option value="Hard">Hard</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="problem-tags">Tags (comma separated)</label>
                        <input type="text" id="problem-tags" placeholder="Array, String, Dynamic Programming">
                    </div>
                    <button type="submit" class="btn btn-primary">Add Problem</button>
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
    modal.querySelector('#problem-form').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Get form values
        const name = document.getElementById('problem-name').value;
        const link = document.getElementById('problem-link').value;
        const difficulty = document.getElementById('problem-difficulty').value;
        const tagsInput = document.getElementById('problem-tags').value;
        
        // Parse tags
        const tags = tagsInput.split(',').map(tag => tag.trim()).filter(tag => tag !== '');
        
        // Create new problem object
        const newProblem = {
            id: 'lc-' + Date.now(),
            name,
            link,
            difficulty,
            tags,
            solution: null
        };
        
        // Add to app state
        if (!appState.leetcodeProblems) {
            appState.leetcodeProblems = [...topicData.leetcode.problems];
        }
        appState.leetcodeProblems.push(newProblem);
        saveState();
        
        // Update UI
        loadProblemsTable();
        
        // Close modal
        document.body.removeChild(modal);
    });
}

function showSolutionModal(problem) {
    // Create modal element
    const modal = document.createElement('div');
    modal.className = 'modal';
    
    // Get existing solution if available
    const solution = problem.solution || '';
    
    modal.innerHTML = `
        <div class="modal-content solution-modal">
            <div class="modal-header">
                <h3>Solution: ${problem.name}</h3>
                <span class="close-modal">&times;</span>
            </div>
            <div class="modal-body">
                <div class="problem-info">
                    <p><strong>Difficulty:</strong> <span class="difficulty ${problem.difficulty.toLowerCase()}">${problem.difficulty}</span></p>
                    <p><strong>Link:</strong> <a href="${problem.link}" target="_blank">${problem.link}</a></p>
                    ${problem.tags ? `<p><strong>Tags:</strong> ${problem.tags.join(', ')}</p>` : ''}
                </div>
                
                <div class="solution-editor">
                    <h4>Your Solution</h4>
                    <textarea id="solution-code" class="code-editor" rows="15" placeholder="// Add your solution here...">${solution}</textarea>
                </div>
                
                <div class="solution-notes">
                    <h4>Notes</h4>
                    <textarea id="solution-notes" rows="5" placeholder="Add your notes, approach, or time/space complexity analysis here...">${problem.notes || ''}</textarea>
                </div>
                
                <button id="save-solution-btn" class="btn btn-primary">Save Solution</button>
            </div>
        </div>
    `;
    
    // Add modal to page
    document.body.appendChild(modal);
    
    // Add event listener for close button
    modal.querySelector('.close-modal').addEventListener('click', function() {
        document.body.removeChild(modal);
    });
    
    // Add event listener for save button
    document.getElementById('save-solution-btn').addEventListener('click', function() {
        const solutionCode = document.getElementById('solution-code').value;
        const notes = document.getElementById('solution-notes').value;
        
        // Update problem in app state
        if (!appState.leetcodeProblems) {
            appState.leetcodeProblems = [...topicData.leetcode.problems];
        }
        
        const problemIndex = appState.leetcodeProblems.findIndex(p => p.id === problem.id);
        if (problemIndex >= 0) {
            appState.leetcodeProblems[problemIndex].solution = solutionCode;
            appState.leetcodeProblems[problemIndex].notes = notes;
        } else {
            // Add problem to app state if not found
            const newProblem = { ...problem, solution: solutionCode, notes };
            appState.leetcodeProblems.push(newProblem);
        }
        
        saveState();
        
        // Update UI
        loadProblemsTable();
        
        // Close modal
        document.body.removeChild(modal);
    });
}
