// src/App.jsx (tylko fragment — reszta bez zmian)
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Logowanie from './components/Logowanie';
import Rejestracja from './components/Rejestracja';
import Dashboard from './components/Dashboard';

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');
    if (token && userData) {
      setUser(JSON.parse(userData));
    }
  }, []);

  const handleLogin = (userData) => {
    // userData to AuthResponse z backendu, zawiera token i rodzicId, imie, nazwisko...
    setUser(userData);
    localStorage.setItem('token', userData.token);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route 
          path="/logowanie" 
          element={!user ? <Logowanie onLogin={handleLogin} /> : <Navigate to="/dashboard" />} 
        />
        <Route 
          path="/rejestracja" 
          element={!user ? <Rejestracja onLogin={handleLogin} /> : <Navigate to="/dashboard" />} 
        />
        <Route 
          path="/dashboard" 
          element={user ? <Dashboard user={user} onLogout={handleLogout} /> : <Navigate to="/logowanie" />} 
        />
        <Route path="/" element={<Navigate to={user ? "/dashboard" : "/logowanie"} />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
