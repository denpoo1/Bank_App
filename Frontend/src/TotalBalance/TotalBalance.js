import React, { useState, useEffect } from "react";
import styles from "./TotalBalance.module.css";
import Wrap from "../Wrap/Wrap";
import axios from "axios";
import Cookies from "js-cookie";


const TotalBalance = ({ cardId }) => {
  const [balance, setBalance] = useState(0);
  const [income, setIncome] = useState(0);
  const [expenses, setExpenses] = useState(0);
  const [percentage, setPercentage] = useState(0);
  const [isPositive, setIsPositive] = useState(false);
  useEffect(() => {
    const token = Cookies.get('token');
    const headers = {
      Authorization: `Bearer ${token}`
    };
    console.log(cardId)

    // Здесь используем получение баланса для конкретной карты по её айди
    axios.get(`http://localhost:8080/credit-cards/${cardId}`, { headers })
      .then(response => {
        const cardData = response.data;
        console.log(cardData)
        setBalance(cardData.balance);
        /* вот сюда вот надо запихнуть эту залупу с експенсес и инкам */
        // const calculatedPercentage = (cardData.income - cardData.expenses) / cardData.expenses * 100;
        // setPercentage(calculatedPercentage);
        // setIsPositive(calculatedPercentage >= 0);
      })
      .catch(error => {
        console.error('Произошла ошибка при получении данных о балансе', error);
      });
  }, [cardId]);

  return (
    <Wrap className={styles.wrapperTotalBAlance}>
      {/* Your other content */}
      <h1 className={styles.amountTitle}>Balance</h1>
      <div className={styles.amountWrapper}>
        <span className={styles.amount}>
          ${balance.toFixed(2)}
        </span>
        <div
          className={styles.amountPercentageWrapper}
          style={{
            backgroundColor: isPositive
              ? percentage > 0
                ? "#07f8b5"
                : "#b3b3b3"
              : "#FE0738",
          }}
        >
          {percentage !== 0 ? ( // Conditionally render the triangle when percentage is not 0
            <span className={styles.triangle}>
              {isPositive ? <>&#9650;</> : <>&#9660;</>}
            </span>
          ) : null}
          <span className={styles.amountPercentage}>
            {percentage !== 0 ? Math.abs(percentage).toFixed(2) + "%" : "N/A"}
          </span>
        </div>
      </div>
      <div className={styles.spendingsWrapper}>
        <span className={styles.income}>${income}</span>
        <span className={styles.expenses}>${expenses}</span>
      </div>
    </Wrap>


  );
};


export default TotalBalance;
