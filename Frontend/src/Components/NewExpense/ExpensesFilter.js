import React from "react";

import "./ExpensesFilter.css";

const ExpensesFilter = (props) => {
const onChangeSelect = (e) => {
    props.onSaveYear(e.target.value)
   
}
  return (
    <div className="expenses-filter">
      <div className="expenses-filter__control">
        <h1>Statisticks</h1>
        <select value={props.selectedYEar} onChange={onChangeSelect}>
          <option value="2023">2023</option>
          <option value="2022">2022</option>
          <option value="2021">2021</option>
          <option value="2020">2020</option>
        </select>
      </div>
    </div>
  );
};

export default ExpensesFilter;
