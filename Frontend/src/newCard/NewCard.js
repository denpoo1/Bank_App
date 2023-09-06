import React, { useState } from "react";
import styles from './newCard.module.css'

const NewCard = (props) => {
  const [inputValue, setInputValue] = useState(""); 

  const isFormValid = () => {
    if (inputValue.trim() === "") {
      return false;
    }

    const regex = /^[a-z\s]+$/;
    return regex.test(inputValue);
  };

  return (
    <div className={styles.wrapper} >
      <h1>Add new card</h1>
      <div className={styles.addingNewCard}>
        <div className={styles.inputInf}>
          <span>Enter please card's holder</span>
          <input
            className={styles.inputForCardNumber}
            type="text"
            placeholder="Nick Johnson"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
          />
        </div>

        <button
          className={`${styles.formButton} ${isFormValid() ? styles.formButtonActive : ''}`}
          type="submit"
          onClick={() => props.onConfirm(inputValue)}
          disabled={!isFormValid()}
        >
          Submit
        </button>

      </div>
    </div>
  );
};

export default NewCard;
