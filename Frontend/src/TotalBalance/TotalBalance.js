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

    axios.get(`http://localhost:8080/transactions`, { headers }) // Запрос транзакций
      .then(response => {
        const transactions = response.data;

        const incomeExpensesMap = {}; // Объект для хранения доходов и расходов по картам

        transactions.forEach(transaction => {
          const { fromCardId, toCardId, amount } = transaction;

          if (!incomeExpensesMap[fromCardId]) {
            incomeExpensesMap[fromCardId] = { income: 0, expenses: 0 };
          }
          if (!incomeExpensesMap[toCardId]) {
            incomeExpensesMap[toCardId] = { income: 0, expenses: 0 };
          }

          incomeExpensesMap[fromCardId].expenses += amount; // Увеличение расходов
          incomeExpensesMap[toCardId].income += amount;     // Увеличение доходов
        });

        // Обновление состояний income и expenses для текущей карты
        setIncome(incomeExpensesMap[cardId]?.income || 0);
        setExpenses(incomeExpensesMap[cardId]?.expenses || 0);

        const calculatedIncome = incomeExpensesMap[cardId]?.income || 0;
        const calculatedExpenses = incomeExpensesMap[cardId]?.expenses || 0;
        const calculatedPercentage = calculatedExpenses === 0 ? calculatedIncome * 100 : (calculatedIncome - calculatedExpenses) / calculatedExpenses * 100;
        console.log(calculatedPercentage)
        console.log(isPositive)
        setPercentage(calculatedPercentage);
        setIsPositive(calculatedPercentage >= 0);
      })
      .catch(error => {
        console.error('Произошла ошибка при получении данных о транзакциях', error);
      });
  }, [cardId]);

  useEffect(() => {
    const token = Cookies.get('token');
    const headers = {
      Authorization: `Bearer ${token}`
    };




    axios.get(`http://localhost:8080/credit-cards/${cardId}`, { headers })
      .then(response => {
        const cardData = response.data;

        setBalance(cardData.balance);

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
        €{balance.toFixed(2)}
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
            {Math.abs(percentage).toFixed(2) + "%"}
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
