import React from "react";
import styles from "./Card.module.css";
import Wrap from "../Wrap/Wrap";
import settingsSign from "../images/other/settings.png";
import map from '../images/card/map.png'
import chip from '../images/card/chip.png'
import pattern from '../images/card/pattern.png'
import visa from '../images/card/visa.png'

const Body = () => {
  return (
    <Wrap className={styles.cardWrapper}>
      <div className={styles.titleWrapper}>
        <h1>Card Lists</h1>
        <button className={styles.settingSignButton}>
          <img
            className={styles.settingSign}
            src={settingsSign}
            alt="Settings Card"
          />
        </button>
      </div>

    <div className={styles.container}>
        <div className={styles.card}>
          <div className={styles.card_inner}>
            <div className={styles.front}>
              <img src={map} className={styles.map_img}  alt="map"/>
              <div className={styles.row}>
                <img src={chip} width="40px" alt="chip"/>
                <img src={visa} width="40px" alt="visa"/>
              </div>
              <div className={`${styles.row} ${styles.card_no}`}>
                <p>5244</p>
                <p>2150</p>
                <p>8252</p>
                <p>6420</p>
              </div>
              <div className={`${styles.row} ${styles.card_holder}`}>
                <p>CARD HPLDER</p>
                <p>VALID TILL</p>
              </div>
              <div className={`${styles.row} ${styles.name}`}>
                <p>JOE ALISON</p>
                <p>10 / 25</p>
              </div>
            </div>
            <div className={styles.back}>
              <img src={map} className={styles.map_img} alt="map"/>
              <div className={styles.bar}></div>
              <div className={`${styles.row} ${styles.card_cvv}`}>
                <div>
                  <img src={pattern} alt="pattern"/>
                </div>
                <p>824</p>
              </div>
              <div className={`${styles.row} ${styles.card_text}`}>
              </div>
              <div className={`${styles.row} ${styles.signature}`}>
                <p>CUSTOMER SIGNATURE</p>
                <img src={visa} width="60px" alt="visa"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Wrap>
  );
};

export default Body;
