import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login/Login';
import BankPage from './BankPages/MainPage';
import Signup from './Login/SignUp';
import Portfolio from './Portfolio/Portfolio';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home-page" element={<BankPage />} />
        <Route path="/sign-up" element={<Signup />} />
        <Route path="/portfolio-page" element={<Portfolio />} />
      </Routes>
    </Router>
  );
};

export default App;
