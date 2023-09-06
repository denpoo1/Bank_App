import React from "react";
import styles from "./SidebarFooter.module.css";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

const SidebarFooter = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    Cookies.remove("token");

    navigate("/");
  };

  const footerButtons = [
    { text: "Help", style: styles.help },
    { text: "Log out", style: styles.logOut },
  ];

  const openTelegramChat = () => {
    const telegramChatUrl = "https://t.me/a10ney";

    window.open(telegramChatUrl, "_blank");
  };

  return (
    <div>
      <div className={`${styles.sidebarFooter}`}>
        {footerButtons.map((button, index) => (
          <div key={index} className={`${styles.wrapForElement} `}>
            {button.text === "Help" ? (
              <button
                className={`${styles.button} ${button.style}`}
                onClick={openTelegramChat}
              >
                {button.text}
              </button>
            ) : button.text === "Log out" ? (
              <button
                className={`${styles.button} ${button.style}`}
                onClick={handleLogout}
              >
                {button.text}
              </button>
            ) : (
              <button className={`${styles.button} ${button.style}`}>
                {button.text}
              </button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default SidebarFooter;
