import React, { useState, useEffect } from "react";
import Wrap from "../Wrap/Wrap";
import styles from './MarketButtons.module.css';
import deposit from '../images/MarketButtons/deposit.png';
import Modal from '../Modal/Module'; // Импортируем компонент модального окна
import axios from 'axios';
import Cookies from "js-cookie";
import visa from '../images/card/visa.png';
import { useSpring, animated } from 'react-spring';



const MarketButtons = () => {
    const [cards, setCards] = useState([])
    const [id, setId] = useState(null)
    const [selectedCard, setSelectedCard] = useState(null);
    const [isCardListOpen, setIsCardListOpen] = useState(false);
    const [cardNumber, setCardNumber] = useState('');
    const [isCardNumberValid, setIsCardNumberValid] = useState(true);
    const [amount, setAmount] = useState('');
    const [isAmountValid, setIsAmountValid] = useState(true);
    const [error, setError] = useState(null); // State variable to store error message
    const isFormValid = isCardNumberValid && isAmountValid && amount.trim() !== '' && cardNumber.trim() !== ''; // Check if amount is not empty


    const handleAmountChange = (e) => {
        const input = e.target.value.replace(/[^\d,]/g, ''); // Remove non-digit and non-comma characters
        setAmount(input);
        setIsAmountValid(/^\d{0,5}(,\d{0,2})?$/.test(input)); // Check if amount is valid
    };

    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevent form submission if it's not valid

        if (isFormValid) {
            const token = Cookies.get('token');
            const headers = {
                Authorization: `Bearer ${token}`
            };

            try {
                // Request to get all credit card numbers
                const creditCardsResponse = await axios.get('http://localhost:8080/credit-cards', { headers });
                const creditCardNumbers = creditCardsResponse.data.map(creditCard => creditCard.cardNumber);

                // Convert entered card number to an integer
                const enteredCardNumber = parseInt(cardNumber.replace(/\D/g, ''), 10);

                // Check if the entered card number exists in the list of all credit card numbers
                if (creditCardNumbers.includes(enteredCardNumber)) {
                    // Find the from_account_id (id of the selectedCard)
                    const from_card_id = selectedCard.id;

                    // Find the to_account_id using the entered card number
                    const matchingCard = creditCardsResponse.data.find(creditCard => creditCard.cardNumber === enteredCardNumber);
                    const to_card_id = matchingCard.id;

                    // Convert the amount to cents if it's in dollars

                    // Send the payment request
                    const paymentData = {
                        from_card_id,
                        to_card_id,
                        amount: amount
                    };
                    console.log(amount)
                    const paymentResponse = await axios.post('http://localhost:8080/payments', paymentData, { headers });
                    setError(null);
                    console.log('Payment successful:', paymentResponse.data);

                    // Reload the page after successful payment
                    window.location.reload();
                } else {
                    setError('Entered card number does not exist.');
                }
            } catch (error) {
                console.error('Error processing payment:', error);
            }
        }
    };



    const handleCardNumberChange = (e) => {
        const input = e.target.value.replace(/\D/g, ''); // Remove non-digit characters
        let formattedInput = '';

        for (let i = 0; i < input.length; i += 4) {
            formattedInput += input.slice(i, i + 4) + ' ';
        }

        setCardNumber(formattedInput.trim());
        setIsCardNumberValid(input.length === 16); // Check if card number is valid
    };



    const marketButtons = [
        { img: deposit, text: "Deposit" },
        { img: deposit, text: "Send" },
        { img: deposit, text: "Statisticks" },
        { img: deposit, text: "Transaction" },
        { img: deposit, text: "Piggy" },
        { img: deposit, text: "Piggy" },
        { img: deposit, text: "Piggy" },
    ];

    const [isDepositModalOpen, setIsDepositModalOpen] = useState(false);

    const handleDepositClick = (e) => {
        e.preventDefault();
        const firstCard = cards[0]; // Получаем первую карту из массива
        setSelectedCard(firstCard); // Устанавливаем её как выбранную карту
        setIsCardListOpen(false); // Закрываем список карт
        setIsDepositModalOpen(true); // Открываем модальное окно
    };

    const handleCloseDepositModal = () => {
        if (!isCardListOpen) {
            setIsDepositModalOpen(false);
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
        axios.get('http://localhost:8080/customers', { headers }).then((res) => {
            const matchingUser = res.data.find(arr => arr.username === username)
            if (matchingUser) {
                setId(matchingUser.id)
            }
        }).catch(error => {
            console.error('Error fetching customer data', error);
        });


        if (id != null) {
            axios.get(`http://localhost:8080/customers/${id}/credit-cards
            `, { headers }).then((res) => {
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
            transform: 'translateY(50px) scale(0.1)', // Начальное положение и размер
        },
        to: {
            opacity: 1,
            transform: 'translateY(0) scale(1)', // Конечное положение и размер
        },
    });
    return (

        <Wrap className={`${styles.marketButtonsWrapper} `}>
            Markets
            <div className={styles.buttonsWrapper}>
                {marketButtons.map((button, index) => (
                    <button
                        key={index}
                        className={styles.wrapForMarketElement}
                        onClick={button.text === "Deposit" ? handleDepositClick : undefined}
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
                                        {/* Вывести список всех карт для выбора */}
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
                                                    onClick={() => handleCardSelect(card)} // Выбор карты и закрытие списка
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
        </Wrap>
    );
};

export default MarketButtons;
