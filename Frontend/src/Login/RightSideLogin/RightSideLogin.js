import React, { useState, useEffect } from 'react';
import styles from './RightSideLogin.module.css';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';

const RightSideLogin = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [isFormValid, setIsFormValid] = useState(false);


  useEffect(() => {
    // Check if both username and password are filled to enable the button
    setIsFormValid(username.trim() !== '' && password.trim() !== '');
  }, [username, password]);

  const handleLogin = async () => {

    try {
      const response = await axios.post('http://localhost:8080/auth/signin', {
        username,
        password,
      });

      const token = response.data.token;
      console.log('Токен:', token);

      // Save the token in cookies for 1 day
      Cookies.set('token', token, { expires: 1 });
      Cookies.set('username', username, { expires: 1 });
      

      setError(null);

      // Redirect to the main page
      navigate('/bank-page');
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setError("Username is wrong");
      }else if(error.response && error.response.status === 403){
        setError("Password is wrong");
      } else {
        setError(error);
      }
    }
  };


  const handleSignUp = () => {
    // Redirect to the signup page
    navigate('/sign-up');
  };
  return (
    <div className={styles.rightSide}>
      <div className={styles.rightSideLoginWrapper}>
        <div>
        <h1 className={styles.title}>Sign in with password</h1>
        <div>
          <input placeholder='Username' type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
          <input placeholder='Password' type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <button
          className={`${styles.formButton} ${isFormValid ? styles.formButtonActive : ''}`}
          onClick={handleLogin}
          disabled={!isFormValid}
        >
          Log in
        </button>        
        
        {error && <p className={styles.errorMessage}>{error}</p>}
      </div>
      <div className={styles.footerWrap}>
      <span>Don`t have an account?<button className={styles.btnSignUp} onClick={handleSignUp}>Sign up</button></span>
      
      </div>
      </div>
    </div>
  );
};

export default RightSideLogin;
