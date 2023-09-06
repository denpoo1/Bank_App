
import React, { useEffect, useState, useRef } from "react";
import styles from './PortfolioPage.module.css';
import Wrap from "../Wrap/Wrap";
import defaulLogo from "../images/logo/defaultPhoto.png";
import Cookies from "js-cookie";
import axios from "axios";
import editIcon from "../images/other/edit.png";
import Modal from "../Modal/Module";
import { animated } from 'react-spring';
import baseUrl from "../config";

const PortfolioPage = () => {
  const [customerId, setCustomerId] = useState(null);
  const [accountId, setAccountId] = useState(null);
  const [avatar, setAvatar] = useState(null)
  const fileInputRef = useRef(null); 

  const [userData, setUserData] = useState({
    id: null,
    username: '',
    email: '',
    password: '',
    first_name: '',
    last_name: '',
    phone: '',
    address: ''
  });

  useEffect(() => {
    const token = Cookies.get('token');
    const username = Cookies.get('username');
    const headers = {
      Authorization: `Bearer ${token}`
    };

    axios.get(`${baseUrl}customers`, { headers })
      .then(response => {
        const matchingUser = response.data.find(user => user.username === username);
        if (matchingUser) {
          setCustomerId(matchingUser.id)
          setUserData(matchingUser);
        }
      })
      .catch(error => {
        console.error('Error fetching customer data', error);
      });

    axios.get(`${baseUrl}accounts`, { headers })
      .then(response => {
        const matchingUser = response.data.find(user => user.customerId === customerId);
        console.log(matchingUser.avatar_url)
        setAvatar(matchingUser.avatar_url)
        setAccountId(matchingUser.id)
      }).catch(error => {
        console.error('Error fetching customer data', error);
      });
  }, [customerId]);
  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];

    if (selectedFile) {
      const formData = new FormData();
      formData.append('file', selectedFile);

      const token = Cookies.get('token');
      const headers = {
        Authorization: `Bearer ${token}`
      };
      console.log(accountId)
      axios.post(`${baseUrl}accounts/${accountId}/upload-avatar`, formData, { headers })
        .then(response => {
          setAvatar(response.data.avatar_url);
        })
        .catch(error => {
          console.error('Error uploading avatar', error);
        });
    }
  };

  return (
    <div className={styles.portfolioPageWrap}>
      <Wrap className={styles.qwe}>
        <input
          type="file"
          accept="image/*"
          style={{ display: 'none' }}
          onChange={handleFileChange}
          ref={fileInputRef}
        />
        <img
          className={styles.img}
          alt="avatar"
          src={avatar === 'DEFAULT' ? defaulLogo : avatar || defaulLogo}

          onClick={() => {
            fileInputRef.current.click();
          }}
        />
        <div className={styles.infWrapper}>
          <span className={styles.name}>{userData.username}</span>
          <div className={styles.emailAndPhoneWrapper}>
            <span>{userData.email}  |  </span>
            <span> +{userData.phone} </span>
          </div>
        </div>
      </Wrap>

      <Wrap className={`${styles.secElemProf} ${styles.flexContainer}`}>
        <UserInfoRow label="Username" value={userData.username} />
        <UserInfoRow label="Email" value={userData.email} />
        <UserInfoRow label="Phone" value={userData.phone} />
        <UserInfoRow label="First name" value={userData.first_name} />
        <UserInfoRow label="Last name" value={userData.last_name} />
        <UserInfoRow label="Address" value={userData.address} />
        <UserInfoRow label="Password" value="********" />

      </Wrap>
    </div>
  );


};

const UserInfoRow = ({ label, value }) => {
  const userPassword = Cookies.get('password');
  const [putInfData, setPutInfData] = useState({
    username: "",
    email: "",
    phone: "",
    first_name: "",
    last_name: "",
    address: "",
    password: "",
  });

  const [userId, setUserID] = useState(null)
  const [isEditing, setIsEditing] = useState(false);
  const [editedValue, setEditedValue] = useState(value);
  const [editingLabel, setEditingLabel] = useState('');

  const toggleEditing = (newEditingLabel) => {
    setIsEditing(!isEditing);
    setEditingLabel(newEditingLabel);
  };

  const handleInputChange = (event) => {
    setEditedValue(event.target.value);
  };
  useEffect(() => {
    // Set up headers and other variables
    const token = Cookies.get('token');
    const username = Cookies.get('username');

    const headers = {
      Authorization: `Bearer ${token}`
    };
    axios.get(`${baseUrl}customers`, { headers })
      .then(response => {
        const matchingUser = response.data.find(user => user.username === username);
        if (matchingUser) {
          setUserID(matchingUser.id);
          if (label.toLowerCase() === "username") {
            setPutInfData({
              username: editedValue,
              email: matchingUser.email,
              phone: matchingUser.phone,
              first_name: matchingUser.first_name,
              last_name: matchingUser.last_name,
              address: matchingUser.address,
              password: userPassword,
            });
          } else if (label.toLowerCase() === "email") {
            setPutInfData({
              username: matchingUser.username,
              email: editedValue,
              phone: matchingUser.phone,
              first_name: matchingUser.first_name,
              last_name: matchingUser.last_name,
              address: matchingUser.address,
              password: userPassword,
            });
          } else if (label.toLowerCase() === "phone") {
            setPutInfData({
              username: matchingUser.username,
              email: matchingUser.email,
              phone: editedValue,
              first_name: matchingUser.first_name,
              last_name: matchingUser.last_name,
              address: matchingUser.address,
              password: userPassword,
            });
          } else if (label.toLowerCase() === "first name") {
            setPutInfData({
              username: matchingUser.username,
              email: matchingUser.email,
              phone: matchingUser.phone,
              first_name: editedValue,
              last_name: matchingUser.last_name,
              address: matchingUser.address,
              password: userPassword,
            });
          } else if (label.toLowerCase() === "last name") {
            setPutInfData({
              username: matchingUser.username,
              email: matchingUser.email,
              phone: matchingUser.phone,
              first_name: matchingUser.first_name,
              last_name: editedValue,
              address: matchingUser.address,
              password: userPassword,
            });
          } else if (label.toLowerCase() === "address") {
            setPutInfData({
              username: matchingUser.username,
              email: matchingUser.email,
              phone: matchingUser.phone,
              first_name: matchingUser.first_name,
              last_name: matchingUser.last_name,
              address: editedValue,
              password: userPassword,
            });
          } else if (label.toLowerCase() === "password") {
            setPutInfData({
              username: matchingUser.username,
              email: matchingUser.email,
              phone: matchingUser.phone,
              first_name: matchingUser.first_name,
              last_name: matchingUser.last_name,
              address: matchingUser.address,
              password: editedValue,
            });
          }
        }
      })
      .catch(error => {
        console.error('Error fetching customer data', error);
      });
  }, [editedValue, label, userPassword]);


  const handleSave = () => {
    const token = Cookies.get('token');

    console.log(userId)
    console.log(putInfData)
    axios.put(`${baseUrl}customers/${userId}`, putInfData, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        console.log('Profile updated successfully', response.data);
        setIsEditing(false);
      })
      .catch(error => {
        console.error('Error updating profile', error);
      });
  };

  const formatValue = (value) => {
    if (label === "Phone") {
      return `+${value}`;
    }
    return value;
  };

  const handleCloseDepositModal = () => {
    if (isEditing) {
      setIsEditing(false);
      setEditingLabel('');
    }
  };

  return (
    <div className={`${styles.tableWrapper} ${styles.userInfoRow}`}>
      <div className={styles.fullElemWrapper}>
        <span className={styles.nameOfElem}>{label}</span>
        {isEditing ? (
          <Modal clasName={isEditing ? `${styles.editWrapper}` : ''} onClose={handleCloseDepositModal}>
            <animated.div className={styles.zxc}>
              <input
                className={styles.editFormElem}
                type="text"
                value={editedValue}
                onChange={handleInputChange}
              />

              <button onClick={handleSave}>Save</button>
              <p>Editing: {editingLabel}</p> 
            </animated.div>
          </Modal>
        ) : (
          <div className={styles.divEdit}>
            <div className={styles.apiElemWrapper}>
              <span>{formatValue(value)}</span>
            </div>
            <img
              className={styles.editIcon}
              src={editIcon}
              alt="Edit"
              onClick={() => toggleEditing(label)}
            />
          </div>
        )}
      </div>
    </div>
  );
};


export default PortfolioPage;
