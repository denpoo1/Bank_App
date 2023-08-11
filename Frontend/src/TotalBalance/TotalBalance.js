import React, { useState, useEffect } from "react";
import styles from "./TotalBalance.module.css";
import Wrap from "../Wrap/Wrap";

const TotalBalance = () => {
  // Sample data for income and expenses
  const [income, setIncome] = useState(400);
  const [expenses, setExpenses] = useState(5000);
  const [percentage, setPercentage] = useState(0);
  const [isPositive, setIsPositive] = useState(false);

  useEffect(() => {
    // Calculate the percentage
    const calculatedPercentage =
      expenses === 0
        ? income * 100
        : (((income - expenses) / expenses) * 100).toFixed(2);
    setPercentage(calculatedPercentage);
    setIsPositive(calculatedPercentage >= 0);
  }, [income, expenses]);

  return (
    <Wrap className={styles.wrapperTotalBAlance}>
      {/* Your other content */}
      <h1 className={styles.amountTitle}>Balance</h1>
      <div className={styles.amountWrapper}>
        <span className={styles.amount}>
          $120<span>.20</span>
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
            {percentage !== 0 ? Math.abs(percentage) + "%" : "N/A"}
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
