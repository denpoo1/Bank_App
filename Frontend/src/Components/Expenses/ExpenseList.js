import React from 'react';
import ExpenseItem from './ExpenseItem';
import './ExpensesList.css';

const ExpenseList = (props) => {
  if (props.items.length === 0) {
    return <h2 className='expenses-list__fallback'>Found no expenses.</h2>;
  }

  const sortedExpenses = [...props.items].sort((a, b) => {
    return new Date(b.date) - new Date(a.date);
  });

  return (
    <ul className='expenses-list'>
      {sortedExpenses.map((expense) => (
        <ExpenseItem
          key={expense.id}
          title={expense.title}
          amount={expense.amount}
          date={expense.date}
        />
      ))}
    </ul>
  );
};

export default ExpenseList;
