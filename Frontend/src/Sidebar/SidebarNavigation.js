import React, { useState } from "react";
import styles from "./SidebarNavigation.module.css";

const SidebarNavigation = () => {
  const [activeButtonIndex, setActiveButtonIndex] = useState(-1);

  const handleButtonClick = (index) => {
    if (activeButtonIndex === index) {
      return; // Ничего не делаем, если нажали на уже активную кнопку
    }
    setActiveButtonIndex(index);
  };

  const mainButtons = [
    { text: "Home", style: "" },
    { text: "Transaction", style: styles.trans },
    { text: "Portfolio", style: styles.port },
    { text: "Wallet", style: styles.wallet },
  ];

  return (
    <div className={`${styles.navigation} ${styles.navigationSeparator}`}>
      {mainButtons.map((button, index) => (
        <div
          key={index}
          className={`${styles.wrapForElement} ${
            activeButtonIndex === index ? styles.clicked : ""
          }`}
        >
          <button
            className={`${styles.button} ${button.style}`}
            onClick={() => handleButtonClick(index)}
          >
            {button.text}
          </button>
        </div>
      ))}
    </div>
  );
};

export default SidebarNavigation;
