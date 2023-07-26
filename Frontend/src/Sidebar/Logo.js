import React from "react";
import logoImage from "../images/logo/logo.jpg"; // Путь к изображению
import styles from "./Logo.module.css";

const Logo = () => {
  return (
    <div className={`${styles.wrapLogo} ${styles.separator}`}>
      <img className={styles.logo} src={logoImage} alt="Logo" />
      <h1 className={styles.logoName}>Iron Bank</h1>
    </div>
  );
};

export default Logo;
