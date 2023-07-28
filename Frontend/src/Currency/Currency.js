import React from "react";
import styles from "./Currency.module.css";
import Wrap from "../Wrap/Wrap";
import ua from "../images/currencyFlags/ua.png";

const Currency = () => {
  return (
    <Wrap className={styles.currenceWrapper}>
      <h1>Currency</h1>
      <div className={styles.currencyRow}>
        <div className={styles.currencyIconAndName}>
          <img alt="Currency Icon" src={ua} />
          <span className={styles.currencyName}>ua</span>
        </div>
        <div className={styles.currencyAmount}>
          <span>0.46</span>
        </div>
      </div>
    </Wrap>
  );
};

export default Currency;
