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
        // Fetch user's cards data from the API
        const token = Cookies.get('token');
        const headers = {
            Authorization: `Bearer ${token}`
        };

        axios.get("http://localhost:8080/customers/84/credit-cards", { headers })
            .then(response => {
                const userCards = response.data; // Assuming the API returns an array of user cards
                if (userCards.length > 0) {
                    // Set the ID of the first card initially
                    setSelectedCardId(userCards[0].id);
                }
            })
            .catch(error => {
                console.error('An error occurred while fetching user card data', error);
            });
    }, []); // Empty dependency array to run the effect only once

    return (
        <BodyWrap>
            <div className={styles.firstRow}>
                <TotalBalance cardId={selectedCardId} />
                <MarketButtons />
            </div>

            <div className={styles.secondRow}>
                <Card onCardSelect={handleCardSelection} />
                <Currency />
            </div>
        </BodyWrap>
    )
}

export default Body;
