import React, { useEffect, useState } from 'react';
import axios from 'axios';
import KeystrokeList from './KeystrokeList';
import './SessionList.css';

const SessionList = ({ user, onBack }) => {
    const [sessions, setSessions] = useState([]);
    const [selectedSession, setSelectedSession] = useState(null);

    useEffect(() => {
        const fetchSessions = async () => {
            try {
                const response = await axios.get(`/api/sessions?userId=${user.userId}`);
                setSessions(response.data);
            } catch (error) {
                console.error("Error fetching sessions:", error);
            }
        };

        fetchSessions();
    }, [user]);

    const handleSessionSelect = (session) => {
        setSelectedSession(session);
    };

    const deleteSession = async (sessionId) => {
        try {
            await axios.delete(`/api/sessions/${sessionId}`);
            setSessions(sessions.filter(session => session.sessionId !== sessionId));
        } catch (error) {
            console.error("Error deleting session:", error);
        }
    };

    return (
        <div className='SessionList'>
            <h2>Sessions for {user.username}</h2>
            {selectedSession ? (
                <KeystrokeList session={selectedSession} onBack={() => setSelectedSession(null)} />
            ) : (
                <div>
                    <button onClick={onBack}>Back to Users</button>
                    <table>
                        <thead>
                            <tr>
                                <th>Session ID</th>
                                <th>Start Time</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {Array.isArray(sessions) && sessions.length > 0 ? (
                                sessions.map(session => (
                                    <tr key={session.sessionId}>
                                        <td onClick={() => handleSessionSelect(session)}>{session.sessionId}</td>
                                        <td>{new Date(session.startTime).toLocaleString()}</td>
                                        <td>
                                            <button
                                                className="delete-button"
                                                onClick={() => deleteSession(session.sessionId)}
                                            >
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="3">No sessions found for this user.</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default SessionList;
