import React, { useState } from "react";

import "./AppExpenses.css";
import "./ExpenseItem.css";

import ExpensesFilter from "../NewExpense/ExpensesFilter";
import Card from "../UI/Card";
import ChartExpenses from "../NewExpense/ChartExpenses";
import ExpenseList from "./ExpenseList";

function AppExpenses(props) {

  const [savedYear, setSavedYear] = useState("2023");
  const yearChangeHAndler = (year) => {
    setSavedYear(year);
  };


  const yearFilter = props.items.filter((array) => {
    if (array && array.date) {
        return array.date.getFullYear().toString() === savedYear;
    }
    return false; // Если array или date равны null, вернуть false
});






  return (
    <Card className="expenses">
      <ExpensesFilter
        selectedYEar={savedYear}
        onSaveYear={yearChangeHAndler}
      ></ExpensesFilter>
      <ChartExpenses expenses={yearFilter} />
      <ExpenseList items={yearFilter} />
    </Card>
  );
}
export default AppExpenses;
