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
        <div
          className={`${styles.profileButtonWrapper} ${
            isMenuOpen ? styles.activeDropdown : ""
          }`}
        >
          <button className={styles.profileButton} onClick={toggleMenu}>
            <span>not&gt;Than10</span>
            <span>{isMenuOpen ? "▲" : "▼"}</span>
          </button>
          {isMenuOpen && (
            <div className={`${styles.dropdown}`}>
              <div className={styles.option}>Denis</div>
              <div className={styles.option}>Polnyi </div>
              <div className={styles.option}>Lox</div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Header;
  