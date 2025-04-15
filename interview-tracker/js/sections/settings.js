// Settings section handler

function loadSettings() {
    const contentDiv = document.getElementById('main-content');
    contentDiv.innerHTML = '';
    
    // Create section header
    const header = document.createElement('div');
    header.className = 'content-header';
    header.innerHTML = `
        <h2>Settings</h2>
        <p>Customize your interview preparation tracker</p>
    `;
    contentDiv.appendChild(header);
    
    // Create settings card
    const settingsCard = document.createElement('div');
    settingsCard.className = 'card';
    settingsCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Application Settings</h3>
        </div>
        <div class="card-body">
            <form id="settings-form">
                <div class="form-group">
                    <label for="target-date-setting">Target Interview Date</label>
                    <input type="date" id="target-date-setting" value="${appState.settings.targetDate || ''}">
                    <small>This will be used to generate your study plan</small>
                </div>
                
                <div class="form-group">
                    <label class="checkbox-container">
                        Enable Dark Mode
                        <input type="checkbox" id="dark-mode-setting" ${appState.settings.darkMode ? 'checked' : ''}>
                        <span class="checkmark"></span>
                    </label>
                    <small>Change the application theme</small>
                </div>
                
                <div class="form-group">
                    <label class="checkbox-container">
                        Enable Reminders
                        <input type="checkbox" id="reminders-setting" ${appState.settings.reminderEnabled ? 'checked' : ''}>
                        <span class="checkmark"></span>
                    </label>
                    <small>Receive reminders about your study plan</small>
                </div>
                
                <div class="form-group">
                    <label for="reminder-time-setting">Reminder Time</label>
                    <input type="time" id="reminder-time-setting" value="${appState.settings.reminderTime || '09:00'}">
                    <small>Time of day to receive reminders</small>
                </div>
                
                <button type="submit" class="btn btn-primary">Save Settings</button>
            </form>
        </div>
    `;
    contentDiv.appendChild(settingsCard);
    
    // Create data management card
    const dataCard = document.createElement('div');
    dataCard.className = 'card';
    dataCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">Data Management</h3>
        </div>
        <div class="card-body">
            <div class="settings-actions">
                <div class="action-item">
                    <button id="export-data-btn" class="btn btn-secondary">Export Data</button>
                    <p>Export all your progress and settings as a JSON file</p>
                </div>
                
                <div class="action-item">
                    <button id="import-data-btn" class="btn btn-secondary">Import Data</button>
                    <p>Import previously exported data</p>
                    <input type="file" id="import-file" accept=".json" style="display: none;">
                </div>
                
                <div class="action-item">
                    <button id="reset-data-btn" class="btn btn-danger">Reset All Data</button>
                    <p>Clear all your progress and settings (cannot be undone)</p>
                </div>
            </div>
        </div>
    `;
    contentDiv.appendChild(dataCard);
    
    // Create about card
    const aboutCard = document.createElement('div');
    aboutCard.className = 'card';
    aboutCard.innerHTML = `
        <div class="card-header">
            <h3 class="card-title">About</h3>
        </div>
        <div class="card-body">
            <div class="about-info">
                <h4>Interview Preparation Tracker</h4>
                <p>Version 1.0.0</p>
                <p>This application helps you prepare for technical interviews by tracking your progress through data structures, algorithms, and other key topics.</p>
                <p>It integrates with your Java DSA Technical Interview Preparation repository to provide a comprehensive study tool.</p>
            </div>
        </div>
    `;
    contentDiv.appendChild(aboutCard);
    
    // Add event listeners
    document.getElementById('settings-form').addEventListener('submit', function(e) {
        e.preventDefault();
        saveSettings();
    });
    
    document.getElementById('export-data-btn').addEventListener('click', exportData);
    document.getElementById('import-data-btn').addEventListener('click', function() {
        document.getElementById('import-file').click();
    });
    
    document.getElementById('import-file').addEventListener('change', importData);
    document.getElementById('reset-data-btn').addEventListener('click', resetData);
    
    // Dark mode toggle listener
    document.getElementById('dark-mode-setting').addEventListener('change', function() {
        toggleDarkMode(this.checked);
    });
}

function saveSettings() {
    // Get form values
    const targetDate = document.getElementById('target-date-setting').value;
    const darkMode = document.getElementById('dark-mode-setting').checked;
    const reminderEnabled = document.getElementById('reminders-setting').checked;
    const reminderTime = document.getElementById('reminder-time-setting').value;
    
    // Update app state
    appState.settings.targetDate = targetDate;
    appState.settings.darkMode = darkMode;
    appState.settings.reminderEnabled = reminderEnabled;
    appState.settings.reminderTime = reminderTime;
    
    // Save state
    saveState();
    
    // Show success message
    showNotification('Settings saved successfully');
    
    // Apply dark mode if needed
    toggleDarkMode(darkMode);
}

function toggleDarkMode(enabled) {
    if (enabled) {
        document.body.classList.add('dark-mode');
    } else {
        document.body.classList.remove('dark-mode');
    }
}

function exportData() {
    // Create a blob with the app state
    const data = JSON.stringify(appState, null, 2);
    const blob = new Blob([data], { type: 'application/json' });
    
    // Create a download link
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'interview-prep-data.json';
    
    // Trigger download
    document.body.appendChild(a);
    a.click();
    
    // Clean up
    setTimeout(function() {
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
    }, 0);
    
    showNotification('Data exported successfully');
}

function importData(event) {
    const file = event.target.files[0];
    if (!file) return;
    
    const reader = new FileReader();
    reader.onload = function(e) {
        try {
            const importedData = JSON.parse(e.target.result);
            
            // Validate imported data
            if (!importedData || typeof importedData !== 'object') {
                throw new Error('Invalid data format');
            }
            
            // Update app state
            Object.assign(appState, importedData);
            saveState();
            
            // Apply settings
            if (appState.settings && appState.settings.darkMode) {
                toggleDarkMode(appState.settings.darkMode);
            }
            
            // Reload current section
            loadContent(appState.currentSection);
            
            showNotification('Data imported successfully');
        } catch (error) {
            showNotification('Error importing data: ' + error.message, 'error');
        }
    };
    
    reader.readAsText(file);
    
    // Reset file input
    event.target.value = '';
}

function resetData() {
    // Ask for confirmation
    if (!confirm('Are you sure you want to reset all data? This cannot be undone.')) {
        return;
    }
    
    // Clear localStorage
    localStorage.removeItem('interviewPrepState');
    
    // Reset app state
    appState.currentSection = 'dashboard';
    appState.progress = {
        'data-structures': 0,
        'algorithms': 0,
        'java-concepts': 0,
        'system-design': 0,
        'behavioral': 0,
        'leetcode': 0
    };
    appState.completedTopics = [];
    appState.studyPlan = [];
    appState.settings = {
        darkMode: false,
        reminderEnabled: true,
        targetDate: null
    };
    
    // Remove dark mode if enabled
    document.body.classList.remove('dark-mode');
    
    // Reload dashboard
    loadContent('dashboard');
    
    showNotification('All data has been reset');
}

function showNotification(message, type = 'success') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    // Add to page
    document.body.appendChild(notification);
    
    // Remove after 3 seconds
    setTimeout(function() {
        notification.classList.add('fade-out');
        setTimeout(function() {
            document.body.removeChild(notification);
        }, 500);
    }, 3000);
}
