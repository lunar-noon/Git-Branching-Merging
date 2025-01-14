// src/components/Dashboard.jsx
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import SessionList from './SessionList';
import './Dashboard.css';

const Dashboard = () => {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get('/api/users');
                setUsers(response.data);
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        };

        fetchUsers();
    }, []);

    const handleUserSelect = (user) => {
        setSelectedUser(user);
    };

    return (
        <div className='Dashboard'>
            <h1>User Dashboard</h1>
            {selectedUser ? (
                <SessionList user={selectedUser} onBack={() => setSelectedUser(null)} />
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Role</th>
                        </tr>
                    </thead>
                    <tbody>
                        {Array.isArray(users) && users.length > 0 ? (
                            users.map(user => (
                                <tr key={user.userId} onClick={() => handleUserSelect(user)}>
                                    <td>{user.username}</td>
                                    <td>{user.role}</td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="2">No users found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default Dashboard;
