import React, { useState, useEffect } from "react";
import styles from './TotalBalance.module.css';
import Wrap from '../Wrap/Wrap'

const TotalBalance = () => {
  // Sample data for income and expenses
  const [income, setIncome] = useState(12312313);
  const [expenses, setExpenses] = useState(31231312);
  const [percentage, setPercentage] = useState(0);

  useEffect(() => {
    // Calculate the percentage
    const calculatedPercentage = expenses === 0 ? income * 100 : (((income - expenses) / expenses)*100).toFixed(2);
    setPercentage(calculatedPercentage);
  }, [income, expenses]);

  return (
    <Wrap className={styles.wrapperTotalBAlance}>
      {/* Your other content */}
      <h1 className={styles.amountTitle}>Total balance</h1>
      <div className={styles.amountWrapper}>
        <span className={styles.amount}>$1231213123<span>.20</span></span>
        <div className={styles.amountPercentageWrapper}>
          <span className={styles.amountPercentage}>{percentage}%</span>
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
