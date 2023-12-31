import React, { useState, useEffect } from "react";
import styles from "./TotalBalance.module.css";
import Wrap from "../Wrap/Wrap";
import axios from "axios";
import Cookies from "js-cookie";
import baseUrl from "../config";


const TotalBalance = ({ cardId }) => {
  const [customerId, setId] = useState(null)
  const [balance, setBalance] = useState(0);
  const [income, setIncome] = useState(0);
  const [expenses, setExpenses] = useState(0);
  const [percentage, setPercentage] = useState(0);
  const [isPositive, setIsPositive] = useState(false);


  useEffect(() => {
    const username = Cookies.get('username');
    const token = Cookies.get('token');
    const headers = {
      Authorization: `Bearer ${token}`
    };
  
    axios.get(`${baseUrl}customers`, { headers }).then((res) => {
      const matchingUser = res.data.find(arr => arr.username === username);
      if (matchingUser) {
        setId(matchingUser.id);
      }
    }).catch(error => {
      console.error('Error fetching customer data', error);
    });
  
    axios.get(`${baseUrl}accounts`, { headers }).then((res) => {
      const accounts = res.data;
  
      accounts.forEach(account => {
        if (account.customerId === customerId) {
          if (customerId != null) {
            axios.get(`${baseUrl}accounts/${account.id}/transaction`, { headers }).then((res) => {
            });
          }
        }
      });
    }).catch(error => {
      console.error('Error fetching accounts data', error);
    });


  
    




    axios.get(`${baseUrl}transactions`, { headers }) 
      .then(response => {
        const transactions = response.data;

        const incomeExpensesMap = {}; 

        transactions.forEach(transaction => {
          const { fromCardId, toCardId, amount } = transaction;

          if (!incomeExpensesMap[fromCardId]) {
            incomeExpensesMap[fromCardId] = { income: 0, expenses: 0 };
          }
          if (!incomeExpensesMap[toCardId]) {
            incomeExpensesMap[toCardId] = { income: 0, expenses: 0 };
          }

          incomeExpensesMap[fromCardId].expenses += amount; 
          incomeExpensesMap[toCardId].income += amount;    
        });
       
        setIncome(incomeExpensesMap[cardId]?.income || 0);
        setExpenses(incomeExpensesMap[cardId]?.expenses || 0);

        const calculatedIncome = incomeExpensesMap[cardId]?.income || 0;
        const calculatedExpenses = incomeExpensesMap[cardId]?.expenses || 0;
        const calculatedPercentage = calculatedExpenses === 0 ? calculatedIncome * 100 : (calculatedIncome - calculatedExpenses) / calculatedIncome * 100; 
        setPercentage(calculatedPercentage);
        setIsPositive(calculatedPercentage >= 0);
      })
      .catch(error => {
        console.error('Error fetching user data', error);
      });
  }, [cardId, isPositive, customerId]);

  useEffect(() => {
    const token = Cookies.get('token');
    const headers = {
      Authorization: `Bearer ${token}`
    };




    axios.get(`${baseUrl}credit-cards/${cardId}`, { headers })
      .then(response => {
        const cardData = response.data;

        setBalance(cardData.balance);

      })
      .catch(error => {
        console.error('Error geting the balance', error)
      });
  }, [cardId, customerId]);

  return (
    <Wrap className={styles.wrapperTotalBAlance}>
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
          {percentage !== 0 ? ( 
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
