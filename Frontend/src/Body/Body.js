import React, { useState, useEffect } from "react";
import styles from './Body.module.css';
import BodyWrap from '../Wrap/BodyWrap'
import TotalBalance from "../TotalBalance/TotalBalance";
import MarketButtons from "../MarketButtons/MarketButtons";
import Card from '../Card/Card';
import Currency from "../Currency/Currency";
import axios from "axios"; 
import Cookies from "js-cookie"; 
import AppExpenses from "../Components/Expenses/AppExpenses";
import baseUrl from "../config";

const Body = () => {
    const [userId, setUserId] = useState(null);
    const [cards, setCards] = useState([null]);
    const [selectedCardId, setSelectedCardId] = useState(null);
    const [arrayOfExpenses, setArrayOfExpenses] = useState([null])
    useEffect(() => {
        const token = Cookies.get('token');
        const username = Cookies.get('username');
        const headers = {
            Authorization: `Bearer ${token}`
        };
        axios.get(`${baseUrl}customers`, { headers })
            .then(response => {
                const matchingUser = response.data.find(user => user.username === username);
                if (matchingUser) {
                    setUserId(matchingUser.id);
                }
            })
            .catch(error => {
                console.error('Error fetching customer data', error);
            });
        if (userId !== null) {
            axios.get(`${baseUrl}customers/${userId}/credit-cards`, { headers })
                .then(response => {
                    setCards(response.data)
                })
                .catch(error => {
                    console.error('Error fetching credit cards data', error);

                });
        }
        axios.get(`${baseUrl}transactions`, { headers }).then(
            (res) => {
                const transactions = res.data;
    
                transactions.forEach(transaction => {
                    transaction.date = new Date(transaction.date);
                });
    
                const expenses = transactions.filter(transaction => transaction.fromCardId === selectedCardId);
                const updatedExpenses = expenses.map(expense => {
                    if (
                        expense.transferType === "CARD_TO_CARD" && 
                        cards && 
                        cards.length > 0 && 
                        expense.toCardId !== null
                    ) {
                        
                        const card = cards.find(card => card.id === expense.toCardId);
                        if (card) {
                            return { ...expense, title: `Transfer to Card ${card.billingAddress}` };
                        }
                    }
                    return expense; 
                });
                
                
                
                
    
                setArrayOfExpenses(updatedExpenses);
            }
        )
    }, [selectedCardId, userId, cards]);



    const handleCardSelection = (cardId) => {
        setSelectedCardId(cardId);
    }

    useEffect(() => {
        const token = Cookies.get('token');
        const username = Cookies.get('username');
        const headers = {
            Authorization: `Bearer ${token}`
        };
        axios.get(`${baseUrl}customers`, { headers })
            .then(response => {
                const matchingUser = response.data.find(user => user.username === username);

                if (matchingUser) {

                    axios.get(`${baseUrl}customers/${matchingUser.id}/credit-cards`, { headers })
                        .then(response => {
                            const userCards = response.data;
                            if (userCards.length > 0) {
                                setSelectedCardId(userCards[0].id);
                            }
                        })
                        .catch(error => {
                            console.error('An error occurred while fetching user card data', error);
                        });
                }
            })
            .catch(error => {
                console.error('Error fetching customer data', error);
            });
    }, []);


    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleModalToggle = () => {
        setIsModalOpen(!isModalOpen);
    };
    return (

        <BodyWrap>
            <div className={styles.thirdRow}>
                <div>
                    <div className={styles.firstRow}>
                        <TotalBalance cardId={selectedCardId} />
                        <MarketButtons onModalToggle={handleModalToggle} />
                    </div>

                    <div className={styles.secondRow}>
                        <Card onCardSelect={handleCardSelection} />
                        <Currency />
                    </div>
                </div>
                <AppExpenses items={arrayOfExpenses}> </AppExpenses>
            </div>
        </BodyWrap>
    )
}

export default Body;
