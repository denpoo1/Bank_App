import React, { useState, useEffect } from "react";
import styles from "./Card.module.css";
import Wrap from "../Wrap/Wrap";
import map from '../images/card/map.png';
import chip from '../images/card/chip.png';
import visa from '../images/card/visa.png';
import right from '../images/cardArrows/rightActive.png';
import left from '../images/cardArrows/leftActive.png';
import addCard from '../images/cardArrows/addCard.png';
import axios from "axios";
import Cookies from "js-cookie";
import Modal from '../Modal/Module';                    
import NewCard from "../newCard/NewCard"
import baseUrl from "../config"; 


const Card = ({ onCardSelect }) => {
  const [cards, setCards] = useState([]);
  const [currentCardIndex, setCurrentCardIndex] = useState(0);
  const [isAnimating, setIsAnimating] = useState(false);
  const isOnFirstCard = currentCardIndex === 0;
  const [userID, setUserID] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);


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
        })
        .catch(error => {
          console.error('Error fetching credit cards data', error);
        })
    }
  }, [userID]);

  useEffect(() => {
    if (isAnimating) {
      setTimeout(() => {
        setIsAnimating(false);
      }, 300); 
    }
  }, [isAnimating]);

  const handlePrevCard = () => {
    if (!isAnimating && currentCardIndex > 0) {
      setIsAnimating(true);
      setTimeout(() => {
        setCurrentCardIndex(currentCardIndex - 1);
        setIsAnimating(false);
        onCardSelect(cards[currentCardIndex - 1].id);
      }, 300); 
    }
  };

  const handleNextCard = () => {
    if (!isAnimating && currentCardIndex < cards.length - 1) {
      setIsAnimating(true);
      setTimeout(() => {
        setCurrentCardIndex(currentCardIndex + 1);
        setIsAnimating(false);
        onCardSelect(cards[currentCardIndex + 1].id);
      }, 300); 
    }
  };

  const handleCreateCard = async (billingAddress) => {
    try {
      const token = Cookies.get('token');
      const headers = {
        Authorization: `Bearer ${token}`
      };
  
      const cardNumberResponse = await axios.get(`${baseUrl}credit-cards/generate/card-number/16`, { headers });
      const generatedCardNumber = cardNumberResponse.data;
  
      const accountResponse = await axios.get(`${baseUrl}accounts`, { headers });
      const matchingAccount = accountResponse.data.find((arr) => arr.customerId === userID);
      if (!matchingAccount) {
        console.error('Couldnt find a user ');
        return;
      }
      
      const accountID = matchingAccount.id;
  
      const generateRandomCVV = () => {
        return Math.floor(100 + Math.random() * 900); 
      };
  
      const calculateExpirationDate = () => {
        const currentDate = new Date();
        currentDate.setFullYear(currentDate.getFullYear() + 8);
        return currentDate.toISOString();
      };
  
      const data = {
        cardNumber: generatedCardNumber,
        cvv: generateRandomCVV(),
        billingAddress: billingAddress,
        creditLimit: 1000,
        balance: 0,
        createdAt: new Date().toISOString(),
        expirationDate: calculateExpirationDate(),
        accountId: accountID,
      };
  
      console.log(data);
  
      const createCardResponse = await axios.post(`${baseUrl}credit-cards`, data, { headers });
      console.log("card created succesflly", createCardResponse.data);
      
    } catch (error) {
      console.error("Error creating card", error);
    }
  };
  
  const formatDate = (dateStr) => {
    const dateObject = new Date(dateStr);
    const year = dateObject.getFullYear();
    const month = (dateObject.getMonth() + 1).toString().padStart(2, "0");
    const day = dateObject.getDate().toString().padStart(2, "0");
    return `${year}-${month}-${day}`;
  };

  const currentCard = cards[currentCardIndex];
  const formattedExpirationDate = currentCard ? formatDate(currentCard.expirationDate) : '';


  const formatCardNumber = (cardNumber) => {
    if (typeof cardNumber === 'number') {
      const formattedNumber = cardNumber.toString().replace(/\s/g, '');
      const groups = [];
      for (let i = 0; i < formattedNumber.length; i += 4) {
        groups.push(formattedNumber.substring(i, i + 4));
      }
      return groups.join(' ');
    } else {
      return ''; 
    }
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleConfirmAction = (props) => {
    handleCreateCard(props);
    closeModal();
  };
  return (
    <Wrap className={styles.cardWrapper}>
      <div className={styles.titleWrapper}>
        <h1>Card Lists</h1>
        <button onClick={openModal} className={styles.settingSignButton}>
          <img
            className={styles.settingSign}
            src={addCard}
            alt="Adding card"
          />
        </button>
      </div>
      <div className={styles.container}>
        {(
          currentCard && (
            <div className={styles.cardButtonsContainer}>
              <button
                className={`${styles.cardButtons} ${styles.leftButton} ${isOnFirstCard ? styles.transparentButton : ""
                  }`}
                onClick={handlePrevCard}
                disabled={isOnFirstCard}
              >
                <img src={left} alt="Previous" />
              </button>
              <div className={styles.card} style={{ opacity: isAnimating ? 0 : 1 }}>
                <div className={`${styles.front} ${styles.card_inner}`} style={{ transform: `rotateY(${isAnimating ? 180 : 0}deg)`, opacity: isAnimating ? 0 : 1 }}>
                  <img src={map} className={styles.map_img} alt="map" />
                  <div className={styles.row}>
                    <img src={chip} width="40px" alt="chip" />
                    <img src={visa} width="40px" alt="visa" />
                  </div>
                  <div className={`${styles.row} ${styles.card_no}`}>
                    <p>{formatCardNumber(currentCard.cardNumber)}</p>
                  </div>
                  <div className={`${styles.row} ${styles.card_holder}`}>
                    <div>
                      <p className={styles.cardHolderText}>Card Holder:</p>
                      <p className={styles.cardHolderValue}>{currentCard.billingAddress}</p>
                    </div>
                    <div>
                      <p className={styles.expiryText}>Valid Till:</p>
                      <p className={styles.expiryValue}>{formattedExpirationDate}</p>
                    </div>
                  </div>
                  <div className={`${styles.row} ${styles.name}`}>
                    <p>{currentCard.name}</p>
                  </div>
                </div>
              </div>
              {currentCardIndex === cards.length - 1 ? (
                <span></span>
              ) : (
                <button
                  className={`${styles.cardButtons} ${styles.createButton}`}
                  onClick={() => handleNextCard(currentCard.id)} 
                >
                  <img src={right} alt="Next" />
                </button>
              )}
            </div>
          ))}
      </div>
      {isModalOpen && (
        <Modal clasName={isModalOpen ? `${styles.editWrapper}` : ''} onClose={closeModal}>

          <NewCard onConfirm={handleConfirmAction} />

        </Modal>
      )}

    </Wrap>
  );
};

export default Card;
