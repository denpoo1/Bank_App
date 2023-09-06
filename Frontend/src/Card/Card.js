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
import Modal from '../Modal/Module'; // Импортируем компонент модального окна
import NewCard from "../newCard/NewCard"

const Card = ({ onCardSelect }) => {
  const [cards, setCards] = useState([]);
  const [currentCardIndex, setCurrentCardIndex] = useState(0);
  const [isAnimating, setIsAnimating] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const isOnFirstCard = currentCardIndex === 0;
  const isOnLastCard = currentCardIndex === cards.length - 1;
  const [userID, setUserID] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [generatedCardNumber, setGeneratedCardNumber] = useState(null)
  const [accountID, setAccountID] = useState(null)


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
        })
        .catch(error => {
          console.error('Error fetching credit cards data', error);
        })
        .finally(() => {
          setIsLoading(false);
        });
    }
  }, [userID]);

  useEffect(() => {
    if (isAnimating) {
      setTimeout(() => {
        setIsAnimating(false);
      }, 300); // Задержка в миллисекундах (0.3 секунды)
    }
  }, [isAnimating]);

  const handlePrevCard = () => {
    if (!isAnimating && currentCardIndex > 0) {
      setIsAnimating(true);
      setTimeout(() => {
        setCurrentCardIndex(currentCardIndex - 1);
        setIsAnimating(false);
        // Обновляем текущую карту в родительском компоненте
        onCardSelect(cards[currentCardIndex - 1].id);
      }, 300); // Задержка в миллисекундах (0.3 секунды)
    }
  };

  const handleNextCard = () => {
    if (!isAnimating && currentCardIndex < cards.length - 1) {
      setIsAnimating(true);
      setTimeout(() => {
        setCurrentCardIndex(currentCardIndex + 1);
        setIsAnimating(false);
        // Обновляем текущую карту в родительском компоненте
        onCardSelect(cards[currentCardIndex + 1].id);
      }, 300); // Задержка в миллисекундах (0.3 секунды)
    }
  };

  // Функция для обработки создания новой карты (пока заглушка)
  const handleCreateCard = async (billingAddress) => {
    try {
      const token = Cookies.get('token');
      const headers = {
        Authorization: `Bearer ${token}`
      };
  
      // Получение сгенерированного номера карты
      const cardNumberResponse = await axios.get('http://localhost:8080/credit-cards/generate/card-number/16', { headers });
      const generatedCardNumber = cardNumberResponse.data;
  
      // Получение информации об аккаунте
      const accountResponse = await axios.get('http://localhost:8080/accounts', { headers });
      const matchingAccount = accountResponse.data.find((arr) => arr.customerId === userID);
      if (!matchingAccount) {
        console.error('Не удалось найти аккаунт для пользователя');
        return;
      }
      
      const accountID = matchingAccount.id;
  
      // Функция для генерации случайного CVV
      const generateRandomCVV = () => {
        return Math.floor(100 + Math.random() * 900); // Генерируем случайное число от 100 до 999
      };
  
      // Функция для вычисления даты окончания через 8 лет
      const calculateExpirationDate = () => {
        const currentDate = new Date();
        currentDate.setFullYear(currentDate.getFullYear() + 8);
        return currentDate.toISOString();
      };
  
      // Создание объекта данных для отправки
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
  
      // Отправка POST-запроса для создания карты
      const createCardResponse = await axios.post("http://localhost:8080/credit-cards", data, { headers });
      console.log("Успешно создана новая карта:", createCardResponse.data);
      
      // Добавьте новую карту к списку карт в состоянии (если это необходимо)
    } catch (error) {
      console.error("Ошибка при создании карты:", error);
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
      const formattedNumber = cardNumber.toString().replace(/\s/g, ''); // Преобразуем в строку и удаляем пробелы, если они уже есть
      const groups = [];
      for (let i = 0; i < formattedNumber.length; i += 4) {
        groups.push(formattedNumber.substring(i, i + 4));
      }
      return groups.join(' '); // Соединяем группы цифр с пробелами между ними
    } else {
      return ''; // Если cardNumber не является числом, возвращаем пустую строку
    }
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  // Функция для закрытия модального окна
  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleConfirmAction = (props) => {
    // Здесь можно добавить логику для действия, которое нужно выполнить после подтверждения
    // Например, вызвать функцию handleCreateCard
    handleCreateCard(props);
    
    // Закрыть модальное окно
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
                  onClick={() => handleNextCard(currentCard.id)} // Передаем айдишник карты
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
