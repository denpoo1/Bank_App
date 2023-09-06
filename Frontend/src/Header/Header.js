import React, { useState, useEffect } from "react";
import styles from './Header.module.css';
import axios from "axios";
import Cookies from "js-cookie";
import { useNavigate } from 'react-router-dom';
import defaulLogo from "../images/logo/defaultPhoto.png";

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [userId, setUserId] = useState(null);
  const [username, setUsername] = useState(""); // Добавили состояние для хранения юзернейма
  const [userData, setUserData] = useState(null); // Добавили состояние для хранения данных пользователя
  const [userImg, setUserImg] = useState('')
  const [sessionExpired, setSessionExpired] = useState(false);
  const navigate = useNavigate();
  const [customerId, setCustomerId] = useState(null);
  const [accountId, setAccountId] = useState(null);
  const [avatar, setAvatar] = useState(null)
  const baseUrl = "http://localhost:8080/"

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
  useEffect(() => {
    const userName = Cookies.get('username');

    // Здесь вам нужно выполнить проверку токена и вернуть путь к соответствующей картинке
    if (userName === "liza") {
      console.log("qwe");
      setUserImg('../images/logo/liza.jpg');
    }

    // Получаем токен из куки
    const tokenFromCookie = Cookies.get("token");
    if (tokenFromCookie) {
      // Выполняем GET-запрос для получения данных пользователя
      axios
        .get(`${baseUrl}customers`, {
          headers: {
            Authorization: `Bearer ${tokenFromCookie}`,
          },
        })
        .then((response) => {

          // Получаем массив аккаунтов
          const accounts = response.data;

          // Получаем сохраненный юзернейм из куки
          const savedUsername = Cookies.get("username");

          // Ищем аккаунт с совпадающим юзернеймом
          const matchedAccount = accounts.find(
            (account) => account.username === savedUsername
          );

          if (matchedAccount) {
            // Если нашли аккаунт с совпадающим юзернеймом, сохраняем айди юзера и обновляем юзернейм
            setUserId(matchedAccount.id);
            setUsername(savedUsername); // Обновляем состояние с юзернеймом
          }
        })
        .catch((error) => {
          setSessionExpired(true);
          console.error("Error fetching user data:", error);

          // Сохраните информацию о сессии в куки
          Cookies.set("sessionExpired", "true");

          // Перенаправление на главную страницу
          navigate("/");
        });

    }
  }, []);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  useEffect(() => {
    // Получаем токен из куки
    const tokenFromCookie = Cookies.get("token");

    if (tokenFromCookie && userId) {
      // Выполняем GET-запрос для получения данных пользователя по его идентификатору
      axios
        .get(`${baseUrl}customers/${userId}`, {
          headers: {
            Authorization: `Bearer ${tokenFromCookie}`,
          },
        })
        .then((response) => {
          // Устанавливаем данные пользователя в состояние
          setUserData(response.data);
        })
        .catch((error) => {
          console.error("Error fetching user data:", error);
        });
    }
  }, [userId]); // Эффект срабатывает при изменении userId

  return (
    <div className={styles.header}>
      <h1 className={styles.headerTitle}>Home</h1>
      <div className={styles.headerWrapper}>
        <div className={styles.headerSearchWrapper}>
        </div>
        <div className={`${styles.profileButtonWrapper}`
          /* ${isMenuOpen ? styles.activeDropdown : "" */       // де добавляю выпадающее меню ,потому что все функции, которые там могут быть, реализованы на страничках
        }>

          <button className={styles.profileButton}
          // onClick={toggleMenu}   тут тоже самое
          >
            <div className={styles.leftContainer}>
              <img className={styles.userLogo} alt="we" src={avatar === 'DEFAULT' ? defaulLogo : avatar || defaulLogo} />
            </div>           
            <div className={styles.centerContainer}>
    <span className={styles.username}>{username}</span>
  </div>            {/* <span className={styles.dropdownIcon}>{isMenuOpen ? "▲" : "▼"}</span> */}
          </button>
          {isMenuOpen && (
            <div className={`${styles.dropdown}`}>
              <div className={styles.option}>Denis</div>
              <div className={styles.option}>Polnyi</div>
              <div className={styles.option}>Lox</div>
            </div>
          )}
        </div>


      </div>
    </div>
  );
};

export default Header;
