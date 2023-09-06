import React, { useEffect, useState } from "react";
import styles from './TransactionPage.module.css';
import Wrap from "../Wrap/Wrap";
import Cookies from "js-cookie";
import axios from "axios";
import visa from '../images/card/visa.png';
import arrowPlus from '../images/other/arrowPlus.png'
import arrowMinus from '../images/other/arrowMinus.png'
import baseUrl from "../config";

const PortfolioPage = () => {
    const [cards, setCards] = useState([]);
    const [userID, setUserID] = useState(null);
    const [selectedCard, setSelectedCard] = useState(null);
    const [selectedCardTransactions, setSelectedCardTransactions] = useState([]);
    const pageSize = 6; 
    const [transactionsToShow, setTransactionsToShow] = useState(pageSize); // Add this state
    const [cardHolder, setCardHolder] = useState(""); // Add this state

    const handleCardSelection = (cardId) => {
        if (selectedCard !== cardId) {
            setSelectedCard(cardId);
        }
    };
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
                    setUserID(matchingUser.id);
                }
            })
            .catch(error => {
                console.error('Error fetching customer data', error);
            });

        if (userID !== null) {
            axios.get(`${baseUrl}customers/${userID}/credit-cards`, { headers })
                .then(response => {
                    setCards(response.data);

                    if (response.data.length > 0) {
                        setSelectedCard(response.data[0].id);
                    }

                })
                .catch(error => {
                    console.error('Error fetching credit cards data', error);
                });
        }
    }, [userID]);

    useEffect(() => {
        setSelectedCardTransactions([]);

        if (selectedCard !== null) {
            const headers = {
                Authorization: `Bearer ${Cookies.get('token')}`
            };

            axios.get(`${baseUrl}transactions`, { headers })
                .then(response => {
                    const transactionsForSelectedCard = response.data.filter(transaction => (
                        transaction.toCardId === selectedCard || transaction.fromCardId === selectedCard
                    ));
                    transactionsForSelectedCard.sort((a, b) => new Date(b.date) - new Date(a.date));

                    setSelectedCardTransactions(transactionsForSelectedCard);

                    // Fetch the selected card info based on the selected card ID
                    const selectedCardInfo = cards.find(card => card.id === selectedCard);
                    if (selectedCardInfo) {
                        setCardHolder(selectedCardInfo.billingAddress);
                    }
                })
                .catch(error => {
                    console.error('Error fetching transactions data', error);
                });
        }
    }, [selectedCard, cards]);







    const transactionsToDisplay = selectedCardTransactions.slice(0, transactionsToShow);
    const loadMoreTransactions = () => {
        const newTransactionsToShow = transactionsToShow + 5; 
        setTransactionsToShow(newTransactionsToShow);
    };


    const groupedTransactions = {};

    transactionsToDisplay.forEach(transaction => {
        const transactionDate = new Date(transaction.date).toLocaleDateString();
        if (!groupedTransactions[transactionDate]) {
            groupedTransactions[transactionDate] = [];
        }
        groupedTransactions[transactionDate].push(transaction);
    });
    return (
        <div className={styles.wholePageWrapper}>
            <div className={styles.cardListElementWrapper}>
                <div className={styles.cardTitleAndCloseButton}>
                    <span>Choose your card:</span>
                </div>
                <div className={styles.cardListItem}>
                    {cards.map(card => (
                        <div
                            key={card.id}
                            className={`${styles.cardWrapper} ${selectedCard === card.id ? styles.selectedCard : ''}`}
                            onClick={() => handleCardSelection(card.id)}
                        >
                            <div className={styles.cardImage}>
                                <span>** {card.cardNumber.toString().slice(-4)}</span>
                                <img alt="visa" src={visa} />
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <Wrap className={`${styles.transactionPageWrap} ${styles.scrollableContainer}`}>
                <div className={styles.qwe}>
                    <div className={styles.tableNamesWrapper}>
                        <div className={styles.twoElem}>
                            <span className={styles.tableNames}>Date</span>
                            <span className={styles.tableNames}>Description</span>
                        </div>
                        <div className={styles.twoElem}>
                            <span className={styles.tableNames}>Amount, €</span>
                            <span className={styles.tableNames}>Balance, €</span>
                        </div>
                    </div>
                </div>
                <div className={styles.sad}>
                    {Object.keys(groupedTransactions).map((transactionDate, index) => (
                        <div key={transactionDate}>
                            {index > 0 && <div className={styles.transactionSeparator}></div>}
                            <div className={styles.devider}>
                                <span>{transactionDate}</span>
                            </div>
                            {groupedTransactions[transactionDate].map((transaction, innerIndex) => (
                                <div className={styles.transactionContainer} key={transaction.id}>
                                    <div className={`${styles.transactionContent} ${innerIndex > 0 ? styles.separator : ''}`}>
                                        <div className={`${styles.twoElem} ${styles.asd}`}>
                                            <div className={styles.imgWrapper}>
                                                <span>{new Date(transaction.date).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                                                <img className={styles.arrow} alt="qwe" src={transaction.fromCardId === selectedCard ? arrowPlus : arrowMinus} />
                                            </div>
                                            <span className={styles.transactionContentElem}>
                                                {transaction.fromCardId === selectedCard
                                                    ? `to card ${cardHolder}`
                                                    : `from ${cardHolder}'s card`}
                                            </span>
                                        </div>
                                        <div className={styles.twoElem}>
                                            <span className={`${styles.transactionContentElem} ${styles.qas} ${transaction.fromCardId === selectedCard ? styles.expense : styles.income}`}>{transaction.amount}</span>
                                            <span className={`${styles.transactionContentElem} ${styles.qaz}`}>{transaction.amount}</span>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    ))}
                </div>
                <div className={styles.loadMoreButtonContainer}>
                    {transactionsToShow < selectedCardTransactions.length && ( 
                        <button
                            className={styles.loadMoreButton}
                            onClick={loadMoreTransactions}
                        >
                            Load More
                        </button>
                    )}
                </div>
            </Wrap>
        </div>
    )

}

export default PortfolioPage;
