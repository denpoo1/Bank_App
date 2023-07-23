import React, { useState } from "react";
import styles from './Header.module.css'

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <div className={styles.header}>
      <h1 className={styles.headerTitle}>Wallet</h1>
      <div className={styles.headerWrapper}>
        <div className={styles.headerSearchWrapper}>
          <button className={styles.search}></button>
          <button className={styles.notification}></button>
        </div>
        <button className={styles.profileButton} onClick={toggleMenu}>
          <span>denis lox</span>
          <span>{isMenuOpen ? "▲" : "▼"}</span>
        </button>
        {isMenuOpen && (
          <div className={styles.dropdown}>
            {/* Здесь размещаем список с опциями */}
            <div className={styles.option}>Опция 1</div>
            <div className={styles.option}>Опция 2</div>
            <div className={styles.option}>Опция 3</div>
            {/* ... */}
          </div>
        )}
      </div>
    </div>
  );
};

export default Header;
