/* import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import fetch from 'node-fetch';

// Verbindung zum WebSocket-Server herstellen
const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, async (frame) => {
    console.log('Verbunden: ' + frame);
    
    // Sitzung erstellen und auf die Session-ID warten
    const sessionId = await createSession(1);
    console.log('Sitzung erstellt. Session-ID:', sessionId);

    stompClient.subscribe('/topic/keystrokes', (message) => {
        console.log('Nachricht vom Server: ' + message.body);
    });

    const keystrokeData = {
        session: { sessionId: sessionId }, 
        text: "Das ist eine echte Testnachricht.",
        action: "Test-Action"
    };
    sendKeystroke(keystrokeData);
}, (error) => {
    console.error('Verbindungsfehler: ' + error);
});

async function createSession(userId) {
    try {
        const response = await fetch(`http://localhost:8080/createSession?userId=${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error('Fehler beim Erstellen der Sitzung: ' + response.statusText);
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Fehler beim Erstellen der Sitzung:', error);
        return null;
    }
}

function sendKeystroke(keystrokeData) {
    stompClient.send("/app/keystroke", {}, JSON.stringify(keystrokeData));
    console.log('Keystroke gesendet:', JSON.stringify(keystrokeData));
} */
