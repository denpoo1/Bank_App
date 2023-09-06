import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styles from './Signup.module.css';
import Cookies from "js-cookie";
import baseUrl from "../config";


const Signup = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phoneCode, setPhoneCode] = useState(''); 
  const [phone, setPhone] = useState('');
  const [address, setAddress] = useState('');
  const [error, setError] = useState(null);
  const [selectedCountry, setSelectedCountry] = useState(null);
  const [userId, setUserId] = useState(null);



  const countryCodes = [
    { phoneCode: '1', name: 'United States', countryCode: 'US', img: './images/x' },
    { phoneCode: '91', name: 'India', countryCode: 'IN' },
    { phoneCode: '44', name: 'United Kingdom', countryCode: 'UK' },
  ];

  const handleSignup = async (e) => {
    e.preventDefault();

    const trimmedUsername = username.trim();
    const trimmedEmail = email.trim();
    const trimmedFirstName = firstName.trim();
    const trimmedLastName = lastName.trim();
    const trimmedPhone = phone.trim();
    const trimmedAdress = address.trim();

    if (!trimmedUsername || !trimmedEmail || !trimmedFirstName || !trimmedLastName || !trimmedPhone || !trimmedAdress) {
      setError(new Error('Please fill in all required fields'));
      return;
    }
    if (password !== confirmPassword) {
      setError(new Error('Passwords do not match'));
      return;
    }

    const usernameRegex = /^[a-zA-Z0-9_]+$/;
    if (!username.match(usernameRegex)) {
      setError(new Error('Username should only contain Latin characters, numbers, and underscores'));
      return;
    }

    if (username.length > 10) {
      setError(new Error('Username should not be longer than 10 characters'));
      return;
    }

    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    if (!email.match(emailRegex)) {
      setError(new Error('Invalid email format'));
      return;
    }

    const phoneNumberRegex = /^\d+$/;
    if (!trimmedPhone.match(phoneNumberRegex)) {
      setError(new Error('Phone number should contain only numbers'));
      return;
    }

    if (!selectedCountry) {
      setError(new Error('Please select a country code'));
      return;
    }


    if (!selectedCountry) {
      setError(new Error('Please select a country code'));
      return;
    }

    try {
      const response = await axios.post(`${baseUrl}auth/signup`, {
        username: trimmedUsername,
        email: trimmedEmail,
        password: password,
        first_name: trimmedFirstName,
        last_name: trimmedLastName,
        phone: `${phoneCode}${trimmedPhone}`,
        address: trimmedAdress,
      });

      console.log('Signup successful:', response.data);
      console.log(`+${phoneCode}${trimmedPhone}`)

      Cookies.set('token', response.data.token);
      const customersResponse = await axios.get(`${baseUrl}customers`, {
        headers: {
          Authorization: `Bearer ${response.data.token}`, // Use the token from the signup response
        },
      });

      const customerWithMatchingUsername = customersResponse.data.find(
        (customer) => customer.username === trimmedUsername
      );

      if (!customerWithMatchingUsername) {
        setError(new Error('User with the entered username not found'));
        return;
      }

      const userId = customerWithMatchingUsername.id;

      const accountData = {
        date: new Date().toISOString(),
        customerId: userId,
        transactionRoundingPercentage: 0,
      };

      const accountResponse = await axios.post(`${baseUrl}accounts`, accountData, {
        headers: {
          Authorization: `Bearer ${response.data.token}`, 
        },
      });

      console.log('Account created:', accountResponse.data);

      navigate('/');
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setError(new Error(error.response.data)); 
      } else {
        setError(error);
      }
    }
  };





  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Sign Up</h2>
      {error && <p className={styles.error_message}>Error: {error.message}</p>}
      <form className={styles.form} onSubmit={handleSignup}>
        <div className={styles.form_group}>
          <label className={styles.form_label}>
            Username:
            <input
              className={styles.form_input}
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </label>
        </div>
        <div className={styles.form_group}>
          <label>
            Email:
            <input
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </label>
        </div>
        <div className={styles.form_group}>
          <label>
            Password:
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </label>
        </div>
        <div className={styles.form_group}>
          <label>
            Confirm Password:
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
          </label>
        </div>
        <div className={styles.form_group}>
          <label>
            First Name:
            <input
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
            />
          </label>
        </div>
        <div className={styles.form_group}>
          <label>
            Last Name:
            <input
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
            />
          </label>
        </div>
        <div className={`${styles.form_group} ${styles.phoneWrapper}`}>
          <label className={styles.form_label}>
            Phone:
            <div className={styles.phoneWrapper}>
              <select
                className={styles.form_select}
                value={selectedCountry ? selectedCountry.phoneCode : ''}
                onChange={(e) => {
                  const phoneCode = e.target.value;
                  setSelectedCountry(
                    countryCodes.find((country) => country.phoneCode === phoneCode)
                  );
                  setPhoneCode(phoneCode); 
                }}
              >
                <option value="">Select option</option>
                {countryCodes.map((country) => (
                  <option key={country.phoneCode} value={country.phoneCode}>
                    {selectedCountry && selectedCountry.phoneCode === country.phoneCode
                      ? ` (+${country.phoneCode})`
                      : `${country.name} (+${country.phoneCode})`}
                  </option>
                ))}
              </select>
              <input
                className={styles.form_input}
                type="text"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
              />
            </div>
          </label>
        </div>
        <div className={styles.form_group}>
          <label>
            Address:
            <input
              type="text"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
            />
          </label>
        </div>
        <button className={styles.form_button} type="submit">Sign Up</button>
      </form>
    </div>
  );
};

export default Signup;