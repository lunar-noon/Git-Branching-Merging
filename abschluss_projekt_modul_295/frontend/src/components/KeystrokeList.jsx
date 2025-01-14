import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './KeystrokeList.css';

const KeystrokeList = ({ session, onBack }) => {
    const [keystrokes, setKeystrokes] = useState([]);
    const [error, setError] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [selectedKeystroke, setSelectedKeystroke] = useState(null);
    const [updatedText, setUpdatedText] = useState("");

    useEffect(() => {
        if (!session || !session.sessionId) {
            setError("Invalid session data.");
            return;
        }

        const fetchKeystrokes = async () => {
            try {
                const response = await axios.get(`/api/keystrokes/session/${session.sessionId}`);
                setKeystrokes(response.data);
                setError(null);
            } catch (error) {
                console.error("Error fetching keystrokes:", error);
                setError("Failed to load keystrokes.");
            }
        };

        fetchKeystrokes();
    }, [session]);

    const deleteKeystroke = async (keystrokeId) => {
        try {
            await axios.delete(`/api/keystrokes/${keystrokeId}`);
            setKeystrokes(keystrokes.filter(keystroke => keystroke.keystrokeId !== keystrokeId));
        } catch (error) {
            console.error("Error deleting keystroke:", error);
        }
    };

    const openEditPopup = (keystroke) => {
        setSelectedKeystroke(keystroke);
        setUpdatedText(keystroke.text);
        setIsEditing(true);
    };

    const updateKeystroke = async () => {
        try {
            const updatedKeystrokeData = {
                session: { sessionId: session.sessionId },
                text: updatedText,
                action: selectedKeystroke.action,
                sentDate: selectedKeystroke.sentDate,
                keystrokeId: selectedKeystroke.keystrokeId
            };
            
            await axios.put(`/api/keystrokes/${selectedKeystroke.keystrokeId}`, updatedKeystrokeData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            setKeystrokes(keystrokes.map(ks => 
                ks.keystrokeId === selectedKeystroke.keystrokeId ? { ...ks, text: updatedText } : ks
            ));
            setIsEditing(false);
            setSelectedKeystroke(null);
        } catch (error) {
            console.error("Error updating keystroke:", error);
        }
    };

    return (
        <div className='KeystrokeList'>
            <h3>Keystrokes for Session ID: {session.sessionId}</h3>
            <button onClick={onBack}>Back to Sessions</button>
            {error ? (
                <p>{error}</p>
            ) : (
                <ul>
                    {keystrokes.map((keystroke) => (
                        <li key={keystroke.keystrokeId}>
                            <h4>Keystroke ID: {keystroke.keystrokeId}</h4>
                            <p><strong>Text:</strong> {keystroke.text}</p>
                            <p><strong>Action:</strong> {keystroke.action}</p>
                            <p><strong>Sent Date:</strong> {new Date(keystroke.sentDate).toLocaleString()}</p>
                            <button onClick={() => openEditPopup(keystroke)}>Update</button>
                            <button onClick={() => deleteKeystroke(keystroke.keystrokeId)} className="delete-button">Delete</button>
                        </li>
                    ))}
                </ul>
            )}

            {isEditing && (
                <div className="popup">
                    <h3>Edit Keystroke</h3>
                    <textarea 
                        value={updatedText} 
                        onChange={(e) => setUpdatedText(e.target.value)} 
                    />
                    <button onClick={updateKeystroke}>Save</button>
                    <button onClick={() => setIsEditing(false)}>Cancel</button>
                </div>
            )}
        </div>
    );
};

export default KeystrokeList;
