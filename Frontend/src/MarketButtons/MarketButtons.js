import React, { useState, useEffect } from "react";
import Wrap from "../Wrap/Wrap";
import PiggyBank from "../PiggyBank/PiggyBank";
import styles from './MarketButtons.module.css';
import deposit from '../images/MarketButtons/deposit.png';
import Modal from '../Modal/Module'; 
import axios from 'axios';
import Cookies from "js-cookie";
import visa from '../images/card/visa.png';
import { useSpring, animated } from 'react-spring';
import baseUrl from "../config";



const MarketButtons = () => {
    const [cards, setCards] = useState([])
    const [id, setId] = useState(null)
    const [selectedCard, setSelectedCard] = useState(null);
    const [isCardListOpen, setIsCardListOpen] = useState(false);
    const [cardNumber, setCardNumber] = useState('');
    const [isCardNumberValid, setIsCardNumberValid] = useState(true);
    const [amount, setAmount] = useState('');
    const [isAmountValid, setIsAmountValid] = useState(true);
    const [error, setError] = useState(null);
    const isFormValid = isCardNumberValid && isAmountValid && amount.trim() !== '' && cardNumber.trim() !== '';


    const handleAmountChange = (e) => {
        const input = e.target.value.replace(/[^\d,]/g, ''); 
        setAmount(input);
        setIsAmountValid(/^\d{0,5}(,\d{0,2})?$/.test(input)); 
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (isFormValid) {
            const token = Cookies.get('token');
            const headers = {
                Authorization: `Bearer ${token}`
            };

            try {
                const creditCardsResponse = await axios.get(`${baseUrl}credit-cards`, { headers });
                const creditCardNumbers = creditCardsResponse.data.map(creditCard => creditCard.cardNumber);

                const enteredCardNumber = parseInt(cardNumber.replace(/\D/g, ''), 10);

                if (creditCardNumbers.includes(enteredCardNumber)) {
                    const from_card_id = selectedCard.id;

                    const matchingCard = creditCardsResponse.data.find(creditCard => creditCard.cardNumber === enteredCardNumber);
                    const to_card_id = matchingCard.id;

                    if (from_card_id === to_card_id) {
                        setError('Cannot transfer money from and to the same card.');
                    } else {
                        if (selectedCard.balance < parseFloat(amount.replace(',', '.'))) {
                            setError('Insufficient funds on the selected card.');
                        } else {


                            const paymentData = {
                                from_card_id,
                                to_card_id,
                                amount: amount
                            };
                            console.log(amount);
                            const paymentResponse = await axios.post(`${baseUrl}payments`, paymentData, { headers });
                            setError(null);
                            console.log('Payment successful:', paymentResponse.data);

                            window.location.reload();
                        }
                    }
                } else {
                    setError('Entered card number does not exist.');
                }
            } catch (error) {
                console.error('Error processing payment:', error);
            }
        }
    };



    const handleCardNumberChange = (e) => {
        const input = e.target.value.replace(/\D/g, ''); 
        let formattedInput = '';

        for (let i = 0; i < input.length; i += 4) {
            formattedInput += input.slice(i, i + 4) + ' ';
        }

        setCardNumber(formattedInput.trim());
        setIsCardNumberValid(input.length === 16); 
    };



    const marketButtons = [
        { img: deposit, text: "Deposit" },
        { img: deposit, text: "Piggy Bank" },
    ];

    const [isDepositModalOpen, setIsDepositModalOpen] = useState(false);
    const [isPiggyBankOpen, setIsPiggyBankOpen] = useState(false);

    const handleDepositClick = (e) => {
        e.preventDefault();
        const firstCard = cards[0]; 
        setSelectedCard(firstCard); 
        setIsCardListOpen(false); 
        setIsDepositModalOpen(true); 
    };

    const handlePiggyBank = (e) => {
        e.preventDefault();
        setIsPiggyBankOpen(true);
    };

    const handleCloseDepositModal = () => {
        if (!isCardListOpen) {
            setIsDepositModalOpen(false);
        }
    };

    const handleClosePiggyBank = () => {
        if (!isCardListOpen) {
            setIsPiggyBankOpen(false);
        }
    };

    const handleCardSelect = (card) => {
        setSelectedCard(card);
        setIsCardListOpen(false);
    };

    const handleCloseCardList = () => {
        setIsCardListOpen(false);
    };



    useEffect(() => {
        const token = Cookies.get('token')
        const username = Cookies.get('username')
        const headers = {
            Authorization: `Bearer ${token}`
        };
        axios.get(`${baseUrl}customers`, { headers }).then((res) => {
            const matchingUser = res.data.find(arr => arr.username === username)
            if (matchingUser) {
                setId(matchingUser.id)
            }
        }).catch(error => {
            console.error('Error fetching customer data', error);
        });


        if (id != null) {
            axios.get(`${baseUrl}customers/${id}/credit-cards`, { headers }).then((res) => {
                setCards(res.data)


            })
        }
    }, [id])


    const formatBalance = (balance) => {
        const formattedBalance = balance.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
        return formattedBalance;
    }


    const modalAnimation = useSpring({
        from: {
            opacity: 0,
            transform: 'translateY(50px) scale(0.1)', 
        },
        to: {
            opacity: 1,
            transform: 'translateY(0) scale(1)',
        },
    });
    return (

        <Wrap className={`${styles.marketButtonsWrapper} `}>
            <h1>Market buttons</h1>
            <div className={styles.buttonsWrapper}>
                {marketButtons.map((button, index) => (
                    <button
                        key={index}
                        className={styles.wrapForMarketElement}
                        onClick={button.text === "Deposit" ? handleDepositClick : (button.text === "Piggy Bank" ? handlePiggyBank : undefined)}
                    >
                        <img src={button.img} alt='qwe'></img>
                        <span>{button.text}</span>
                    </button>
                ))}
            </div>
            {isDepositModalOpen && (
                <Modal className={isCardListOpen ? `${styles.qwe}` : ''} onClose={handleCloseDepositModal}>

                    <animated.form className={styles.depForm} onSubmit={handleSubmit}>
                        <h1>Transfer</h1>
                        <div className={styles.withdrawWrapper}>
                            <h2 className={styles.withdrawTitle}>Withdraw money from:</h2>
                            <div
                                className={styles.cardWrapper}
                                onClick={() => setIsCardListOpen(!isCardListOpen)}
                            >
                                <div className={styles.cardImage}>
                                    {selectedCard && (
                                        <span>** {selectedCard.cardNumber.toString().slice(-4)}</span>
                                    )}
                                    <img alt="visa" src={visa} />
                                </div>
                                <div className={styles.cardNameAndWrapper}>
                                    {selectedCard && (
                                        <span>{selectedCard.billingAddress}</span>
                                    )}
                                    {selectedCard && (
                                        <span>{formatBalance(selectedCard.balance)} €</span>
                                    )}
                                </div>
                            </div>

                            {isCardListOpen && (
                                <animated.div
                                    className={`${styles.cardList} 
                                    ${styles.backdrop}`}
                                    style={modalAnimation}
                                >
                                    <animated.div style={modalAnimation} className={styles.cardList}>
                                        <div className={styles.cardListElementWrapper}>
                                            <div className={styles.cardTitleAndCloseButton}>
                                                <span>Withdraw from:</span>
                                                <span
                                                    className={`${styles.closeButton}}`}
                                                    onClick={handleCloseCardList}
                                                >
                                                    &#10006;
                                                </span>
                                            </div>
                                            {cards.map((card) => (
                                                <div
                                                    key={card.id}
                                                    className={styles.cardListItem}
                                                    onClick={() => handleCardSelect(card)} 
                                                >
                                                    <div className={styles.cardImage}>
                                                        {selectedCard && (
                                                            <span>** {card.cardNumber.toString().slice(-4)}</span>
                                                        )}
                                                        <img alt="visa" src={visa} />
                                                    </div>
                                                    <div className={styles.cardNameAndWrapper}>
                                                        {selectedCard && (
                                                            <span>{card.billingAddress}</span>
                                                        )}
                                                        {selectedCard && (
                                                            <span>{formatBalance(card.balance)} €</span>
                                                        )}
                                                    </div>
                                                </div>
                                            ))}
                                        </div>
                                    </animated.div>
                                </animated.div>
                            )}
                        </div>

                        <div className={`${styles.withdrawWrapper} ${isCardNumberValid ? '' : styles.error}`}>
                            <h2 className={styles.withdrawTitle}>Send money to:</h2>
                            <input
                                className={styles.inputForCardNumber}
                                type="text"
                                value={cardNumber}
                                onChange={handleCardNumberChange}
                                placeholder="0000 0000 0000 0000"
                            />
                            {!isCardNumberValid && <p className={styles.errorText}>Card number must have exactly 16 digits</p>}
                        </div>
                        <div className={`${styles.amountWrapper} ${isAmountValid ? '' : styles.error}`}>
                            <h2 className={styles.amountTitle}>Amount</h2>
                            <input
                                className={styles.inputForAmount}
                                type="text"
                                value={amount}
                                onChange={handleAmountChange}
                                placeholder="00,00"
                            />
                            {!isAmountValid && <p className={styles.errorText}>Invalid amount format</p>}
                        </div>

                        <button className={`${styles.formButton} ${isFormValid ? styles.formButtonActive : ''}`} type="submit">
                            Submit
                        </button>
                        {error && (
                            <p className={`${styles.errorText} ${styles.errorMessage}`}>
                                {error}
                            </p>
                        )}
                    </animated.form>

                </Modal>
            )}
            {isPiggyBankOpen && (
                <Modal className={isCardListOpen ? `${styles.qwe}` : ''} onClose={handleClosePiggyBank}>

                    <PiggyBank />

                </Modal>
            )}
        </Wrap>
    );
};

export default MarketButtons;
