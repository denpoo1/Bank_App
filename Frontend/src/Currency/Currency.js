import React, { useEffect, useState } from "react";
import styles from "./Currency.module.css";
import Wrap from "../Wrap/Wrap";
import uaIcon from "../images/currencyFlags/ua.png";
import usdIcon from "../images/currencyFlags/usd.png";
import gbpIcon from "../images/currencyFlags/gbp.png";
import plnIcon from "../images/currencyFlags/pln.png";
import axios from "axios";
import Cookies from "js-cookie";

const Currency = () => {
  const [exchangeRates, setExchangeRates] = useState(null);

  useEffect(() => {
    const tokenFromCookie = Cookies.get("token");

    if (tokenFromCookie) {
      axios
        .get("http://localhost:8080/exchange-rates/latest/eur", {
          headers: {
            Authorization: `Bearer ${tokenFromCookie}`,
          },
        })
        .then((response) => {
          // Получаем объект с обменными курсами
          const conversionRates = response.data.conversion_rates;
          setExchangeRates(conversionRates);
        })
        .catch((error) => {
          console.error("Error fetching exchange rates:", error);
        });
    }
  }, []);

  const filteredCurrencies = ["USD", "UAH", "GBP", "PLN"];

  return (
    <Wrap className={styles.currenceWrapper}>
      <h1>Currency</h1>
      <div className={styles.currencyList}>
        {exchangeRates ? (
          filteredCurrencies.map((currency) => (
            <div key={currency} className={styles.currencyRow}>
              <div className={styles.currencyIconAndName}>
                {currency === "USD" && <img alt="USD Icon" src={usdIcon} />}
                {currency === "UAH" && <img alt="UA Icon" src={uaIcon} />}
                {currency === "GBP" && <img alt="GBP Icon" src={gbpIcon} />}
                {currency === "PLN" && <img alt="PLN Icon" src={plnIcon} />}
                <span className={styles.currencyName}>{currency}</span>
              </div>
              <div className={styles.currencyAmount}>
                <span>{exchangeRates[currency]}</span>
              </div>
            </div>
          ))
        ) : (
          <p>Loading...</p>
        )}
      </div>
    </Wrap>
  );
};

export default Currency;
