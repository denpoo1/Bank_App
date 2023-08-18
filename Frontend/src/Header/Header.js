import React, { useState, useEffect } from "react";
import styles from './Header.module.css';
import axios from "axios";
import Cookies from "js-cookie";

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [userId, setUserId] = useState(null);
  const [username, setUsername] = useState(""); // Добавили состояние для хранения юзернейма
  const [userData, setUserData] = useState(null); // Добавили состояние для хранения данных пользователя

  useEffect(() => {
    // Получаем токен из куки
    const tokenFromCookie = Cookies.get("token");

    if (tokenFromCookie) {
      // Выполняем GET-запрос для получения данных пользователя
      axios
        .get("http://localhost:8080/customers", {
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
          console.error("Error fetching user data:", error);
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
        .get(`http://localhost:8080/customers/${userId}`, {
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
      <h1 className={styles.headerTitle}>Wallet</h1>
      <div className={styles.headerWrapper}>
        <div className={styles.headerSearchWrapper}>
          <button className={styles.search}></button>
          <button className={styles.notification}></button>
        </div>
        <div
          className={`${styles.profileButtonWrapper} ${
            isMenuOpen ? styles.activeDropdown : ""
          }`}
        >
          <button className={styles.profileButton} onClick={toggleMenu}>
            <span>{username}</span> {/* Используем состояние с юзернеймом */}
            <span>{isMenuOpen ? "▲" : "▼"}</span>
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
