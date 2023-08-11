import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login/Login';
import BankPage from './BankPages/MainPage';
import Signup from './Login/SignUp';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/bank-page" element={<BankPage />} />
        <Route path="/sign-up" element={<Signup />} />
      </Routes>
    </Router>
  );
};

export default App;
