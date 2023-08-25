import React, { useEffect, useState } from "react";
import styles from './TransactionPage.module.css';
import Wrap from "../Wrap/Wrap";
import liza from "../images/logo/liza2.jpg";
import Cookies from "js-cookie";
import axios from "axios";
import editIcon from "../images/other/edit.png";
import Modal from "../Modal/Module";
import { useSpring, animated } from 'react-spring';
import visa from '../images/card/visa.png';


const PortfolioPage = () => {
    const [cards, setCards] = useState([]);
    const [userID, setUserID] = useState(null);
    const [selectedCard, setSelectedCard] = useState(null); // Добавленное состояние


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
                    console.log(response.data)
                })
                .catch(error => {
                    console.error('Error fetching credit cards data', error);
                })
        }
    }, [userID]);

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
                            onClick={() => setSelectedCard(card.id)}
                        >
                            <div className={styles.cardImage}>
                            <span>** {card.cardNumber.toString().slice(-4)}</span>
                                <img alt="visa" src={visa} />
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <Wrap className={styles.transactionPageWrap}>

                <div className={styles.tableNamesWrapper}>
                    <span className={styles.tableNames}>From card</span>
                    <span className={styles.tableNames}>To card</span>
                    <span className={styles.tableNames}>Date</span>
                    <span className={styles.tableNames}>Amount</span>
                </div>


            </Wrap>

            
        </div>
    )
}

export default PortfolioPage