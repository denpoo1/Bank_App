import React from "react";
import styles from "./SidebarFooter.module.css";

const SidevarFooter = () => {
  const footerButtons = [
    { text: "Help", style: styles.help },
    { text: "Log out", style: styles.logOut },
  ];
  return (
    <div>
      <div className={`${styles.sidebarFooter}`}>
        {footerButtons.map((button, index) => (
          <div key={index} className={`${styles.wrapForElement} `}>
            <button className={`${styles.button} ${button.style}`}>
              {button.text}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SidevarFooter;
