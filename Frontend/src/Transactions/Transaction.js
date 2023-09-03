import React from "react";
import Header from "../Header/Header";
import Sidebar from "../Sidebar/Sidebar";
import TransactionPage from "./TransactionPage";

const Transaction = () => {
    return (
        <div>
        <Sidebar/>
        <Header/>
        <TransactionPage/>
        </div>
    )
}

export default Transaction