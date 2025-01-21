import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import AuthForm from './components/AuthForm';
import Posts from './components/Posts';
import CreateArticle from './pages/CreateArticle';


function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<AuthForm isRegistering={false} />} />
        <Route path="/register" element={<AuthForm isRegistering={true} />} />
        <Route path="/posts" element={<Posts />} />
        <Route path="/create" element={<CreateArticle />} />
        <Route path="/edit-article/:id" element={<CreateArticle />} />
      </Routes>
    </Router>
  );
}

export default App;