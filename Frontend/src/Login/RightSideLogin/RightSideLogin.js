import React, { useState, useEffect } from 'react';
import styles from './RightSideLogin.module.css';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
import Modal from '../../Modal/Module';
import baseUrl from "../../config"

const RightSideLogin = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [isFormValid, setIsFormValid] = useState(false);
  const [sessionExpired, setSessionExpired] = useState(false);

  useEffect(() => {
    const sessionExpiredCookie = Cookies.get("sessionExpired");
  
    if (sessionExpiredCookie === "true") {
      Cookies.remove("sessionExpired");
      setSessionExpired(true)
    }
  }, []);
  useEffect(() => {
    setIsFormValid(username.trim() !== '' && password.trim() !== '');
  }, [username, password]);
  useEffect(() => {
    checkTokenExpiration();
  }, []);


  const checkTokenExpiration = () => {
    const token = Cookies.get('token');
    if (!token) {
      navigate('/');
      return;
    }

    const tokenExpirationDate = new Date(Cookies.get('tokenExpiration'));
    const currentTime = new Date();
    if (currentTime > tokenExpirationDate) {
      Cookies.remove('token');
      navigate('/'); 
    }
  };





  const handleLogin = async () => {
    try {
      const response = await axios.post(`${baseUrl}auth/signin`, {
        username,
        password,
      });

      const token = response.data.token;
      console.log('Token:', token);

      const tokenExpirationDate = new Date();
      tokenExpirationDate.setDate(tokenExpirationDate.getDate() + 1);

      Cookies.set('token', token, { expires: tokenExpirationDate });
      Cookies.set('tokenExpiration', tokenExpirationDate, { expires: tokenExpirationDate });
      Cookies.set('password', password, { expires: 1 });
      Cookies.set('username', username, { expires: 1 });

      setError(null);

      navigate('/home-page');
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setError('Username is wrong');
      } else if (error.response && error.response.status === 403) {
        setError('Password is wrong');
      } else {
        setError(error);
      }
    }
  };


  const handleSignUp = () => {
    navigate('/sign-up');
  };
  const closeModalOfExpiredSession = () =>{
      setSessionExpired(false)
  }
  
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

      {sessionExpired && (
        <Modal clasName={sessionExpired ? `${styles.editWrapper}` : ''} onClose={closeModalOfExpiredSession}>
            <h2>Your session has expired</h2>
        </Modal>
      )}
    </div>


  );
};

export default RightSideLogin;
