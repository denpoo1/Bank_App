import React, { useEffect, useState } from "react";
import styles from './TransactionPage.module.css';
import Wrap from "../Wrap/Wrap";
import Cookies from "js-cookie";
import axios from "axios";
import visa from '../images/card/visa.png';

const PortfolioPage = () => {
    const [cards, setCards] = useState([]);
    const [userID, setUserID] = useState(null);
    const [selectedCard, setSelectedCard] = useState(null);
    const [selectedCardTransactions, setSelectedCardTransactions] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [prevDate, setPrevDate] = useState(null); // Добавлено состояние для отслеживания предыдущей даты
    const pageSize = 6; // Number of transactions per page
    const [transactionsToShow, setTransactionsToShow] = useState(pageSize); // Add this state


    const handleCardSelection = (cardId) => {
        if (selectedCard !== cardId) {
            setSelectedCard(cardId);
            setCurrentPage(1); // Reset currentPage to 1 when selecting a new card
        }
    };
    useEffect(() => {
        const token = Cookies.get('token');
        const username = Cookies.get('username');

        const headers = {
            Authorization: `Bearer ${token}`
        };

        // First, fetch all customers to find the matching username
        axios.get("http://localhost:8080/customers", { headers })
            .then(response => {
                const matchingUser = response.data.find(user => user.username === username);
                if (matchingUser) {
                    // Set the userID if the username matches
                    setUserID(matchingUser.id);
                }
            })
            .catch(error => {
                console.error('Error fetching customer data', error);
            });

        // Then, fetch credit cards for the specific user (if userID is set)
        if (userID !== null) {
            axios.get(`http://localhost:8080/customers/${userID}/credit-cards`, { headers })
                .then(response => {
                    setCards(response.data);

                    // Set the selectedCard to the first card by default
                    if (response.data.length > 0) {
                        setSelectedCard(response.data[0].id);
                    }
                })
                .catch(error => {
                    console.error('Error fetching credit cards data', error);
                })
        }
    }, [userID]);

    useEffect(() => {
        if (selectedCard !== null) {
            const headers = {
                Authorization: `Bearer ${Cookies.get('token')}`
            };

            axios.get(`http://localhost:8080/transactions`, { headers })
                .then(response => {
                    const transactionsForSelectedCard = response.data.filter(transaction => (
                        transaction.toCardId === selectedCard || transaction.fromCardId === selectedCard
                    ));
                    transactionsForSelectedCard.sort((a, b) => new Date(b.date) - new Date(a.date));

                    setSelectedCardTransactions(transactionsForSelectedCard);
                })
                .catch(error => {
                    console.error('Error fetching transactions data', error);
                });
        }
    }, [selectedCard]);

    const transactionsToDisplay = selectedCardTransactions.slice(0, transactionsToShow);
    const loadMoreTransactions = () => {
        const newTransactionsToShow = transactionsToShow + 5; // Increase by 5 transactions
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
            <Wrap className={`${styles.transactionPageWrap} ${styles.scrollableContainer}`}> {/* Apply the scrollableContainer class */}
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
                            {index > 0 && <div className={styles.transactionSeparator}></div>} {/* Add a separator after the first group */}
                            <div className={styles.devider}>
                                <span>{transactionDate}</span>
                            </div>
                            {groupedTransactions[transactionDate].map((transaction, innerIndex) => (
                                <div className={styles.transactionContainer} key={transaction.id}>
                                    <div className={`${styles.transactionContent} ${innerIndex > 0 ? styles.separator : ''}`}>
                                        <div className={styles.twoElem}>
                                            {new Date(transaction.date).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                            <span className={styles.transactionContentElem}>
                                                {transaction.fromCardId === selectedCard ? `to card **${transaction.toCardId}` : `from card **${transaction.fromCardId}`}
                                            </span>
                                        </div>
                                        <div className={styles.twoElem}>
                                            <span className={styles.transactionContentElem}>{transaction.amount}</span>
                                            <span className={styles.transactionContentElem}>{transaction.amount}</span>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    ))}
                </div>
                <div className={styles.loadMoreButtonContainer}>
                    {transactionsToShow < selectedCardTransactions.length && ( // Show the button only when there are more transactions to load
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
