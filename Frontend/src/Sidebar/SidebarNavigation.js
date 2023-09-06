import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styles from "./SidebarNavigation.module.css";

const SidebarNavigation = () => {
  const [activeButtonIndex, setActiveButtonIndex] = useState(-1);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const activeIndex = mainButtons.findIndex(
      (button) =>
        (button.index === 0 && location.pathname === "/") ||
        location.pathname.includes(button.text.toLowerCase())
    );

    setActiveButtonIndex(activeIndex);
  }, [location]);

  const handleButtonClick = (index) => {
    if (activeButtonIndex === index) {
      return;
    }

    const newPath = mainButtons[index].index === 10 ? "/" : `/${mainButtons[index].text.toLowerCase()}-page`;
    navigate(newPath);

    setActiveButtonIndex(index);
  };

  const mainButtons = [
    { text: "Home", style: styles.home, index: 0 },
    { text: "Transaction", style: styles.trans, index: 1 },
    { text: "Portfolio", style: styles.port, index: 2 },
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
