import React, { useState, useEffect } from "react";
import styles from './Body.module.css';
import BodyWrap from '../Wrap/BodyWrap'
import TotalBalance from "../TotalBalance/TotalBalance";
import MarketButtons from "../MarketButtons/MarketButtons";
import Card from '../Card/Card';
import Currency from "../Currency/Currency";
import axios from "axios"; // Import Axios
import Cookies from "js-cookie"; // Import Cookies

const Body = () => {
    const [selectedCardId, setSelectedCardId] = useState(null);


    // Function to handle card selection
    const handleCardSelection = (cardId) => {
        setSelectedCardId(cardId);
    }

    useEffect(() => {
        const token = Cookies.get('token');
        const username = Cookies.get('username');
        const headers = {
            Authorization: `Bearer ${token}`
        };
        axios.get("http://localhost:8080/customers", { headers })
        .then(response => {
            const matchingUser = response.data.find(user => user.username === username);
            
            if (matchingUser) {
                
                axios.get(`http://localhost:8080/customers/${matchingUser.id}/credit-cards`, { headers })
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
            <div className={styles.firstRow}>
                <TotalBalance cardId={selectedCardId} />
                <MarketButtons onModalToggle={handleModalToggle} />
            </div>

            <div className={styles.secondRow}>
                <Card onCardSelect={handleCardSelection} />
                <Currency />
            </div>
        </BodyWrap>
    )
}

export default Body;
