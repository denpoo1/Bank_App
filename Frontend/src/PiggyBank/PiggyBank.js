import React, { useEffect, useState } from "react";
import styles from './PiggyBank.module.css'
import plus from '../images/other/plus.png'
import minus from '../images/other/minus.png'
import changeName from '../images/other/changeNameBtn.png'
import changeGoal from '../images/other/changeGoalBtn.png'
import Cookies from "js-cookie";
import axios from "axios";
import Modal from "../Modal/Module";
const PiggyBank = () => {
    const [collected, setCollected] = useState(1000); // Поменял значение для демонстрации переполнения
    const [goal, setGoal] = useState(1000);
    const [isPiggyOpen, setIsPiggyOpen] = useState(false);
    const [customerId, setCustomerId] = useState(null)
    const [accountID, setAccountID] = useState(null)
    const [piggyBanks, setPiggyBanks] = useState([null])
    const progress = (collected / goal) * 100;
    const isFull = progress >= 100;
    const openPiggy = () => {
        setIsPiggyOpen(true);
    };

    const closePiggy = () => {
        setIsPiggyOpen(false);
    };


    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = Cookies.get('token');
                const username = Cookies.get('username');
                const headers = {
                    Authorization: `Bearer ${token}`,
                };
    
                // Fetch customer data
                const customerResponse = await axios.get('http://localhost:8080/customers', { headers });
                const matchingUser = customerResponse.data.find((arr) => arr.username === username);
                if (matchingUser) {
                    setCustomerId(matchingUser.id);
                }
    
                // Fetch account data
                const accountResponse = await axios.get('http://localhost:8080/accounts', { headers });
                const matchingAccount = accountResponse.data.find((arr) => arr.customerId === customerId);
                if (matchingAccount) {
                    setAccountID(matchingAccount.id);
                }
    
                // Fetch piggy banks data
                const piggyBanksResponse = await axios.get('http://localhost:8080/piggy-banks', { headers });
                const piggyBanksData = piggyBanksResponse.data;
    
                // Filter piggy banks by accountID
                const filteredPiggyBanks = piggyBanksData.filter((piggyBank) => piggyBank.accountID === accountID);
    
                // Set piggy banks state
                setPiggyBanks(filteredPiggyBanks);
    
                // Вывод банков в консоль
                console.log('Банки пользователя:');
                filteredPiggyBanks.forEach((piggyBank) => {
                    console.log(`ID: ${piggyBank.id}, Имя: ${piggyBank.name}, Баланс: ${piggyBank.balance}`);
                });
            } catch (error) {
                console.error('Ошибка при получении данных', error);
            }
        };
    
        fetchData();
    }, [customerId, accountID]);
    

    const bottomOfBiggerJarStyle = {
        background: `linear-gradient(to top, #757272 ${progress}%, transparent ${progress}%)`,
    };

    const bottomOfBiggerJarStyleFull = {
        background: `linear-gradient(to top, #3adb76 ${progress}%, transparent ${progress}%)`,
        boxShadow: ' 0 0 15px rgba(58, 219, 118, 1)'
    }
    return (
        <div className={styles.piggyBank}>
            <h1>Piggy Bank</h1>
            <div className={styles.bankWrapper} onClick={openPiggy}>
                <div className={styles.jar}>
                    <div className={styles.topOfJar}></div>
                    <div className={styles.bottomOfJar}></div>
                </div>
                {piggyBanks.map((piggyBank) => (
                    <div className={styles.bankDescription}>
                        <h2 className={styles.boxName}>ZSU</h2>
                        <span className={styles.collected}>
                        {collected} euro collected from {goal} euro
                        </span>                    
                        <div className={`${styles.progressionBar} `}>
                            <div className={`${styles.progressBarFill} ${isFull ? styles.full : ''}`} style={{ width: `${progress}%` }}></div>
                        </div>
                    </div>
                ))}
            </div>
            <div className={styles.addNewPiggy}>
                <div>
                    <img src={plus} alt="plus" />
                    <span> Add new box</span>
                </div>
            </div>
            {isPiggyOpen && (
                <Modal onClose={closePiggy} clasName={styles.wholeWrapper}>
                    <h1>Name of Box</h1>
                    <div className={styles.wholeBankDescription}>
                        <div className={styles.firstRowBank}>
                            <div className={styles.biggerJar}>
                                <div className={styles.topOfBiggerJar}></div>
                                <div className={`${styles.bottomOfBiggerJar}`} style={isFull ? bottomOfBiggerJarStyleFull : bottomOfBiggerJarStyle}></div>
                            </div>
                            <div className={styles.moneyDesc}>
                                <div className={styles.innerDescWrapper}>
                                    <span>
                                        {collected} euro
                                    </span>
                                    <span>
                                        From
                                    </span>
                                    <span>
                                        {goal} euro
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div className={styles.secondRow}>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent}>
                                    <img alt="qwe" src={plus} />
                                </div>
                                <span className={styles.circleName}>Top up</span>
                            </div>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent}>
                                    <img alt="qwe" src={minus} />
                                </div>
                                <span className={styles.circleName}>Withdraw</span>
                            </div>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent}>
                                    <img alt="qwe" src={changeGoal} />
                                </div>
                                <span className={styles.circleName}>Change goal</span>
                            </div>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent}>
                                    <img alt="qwe" src={changeName} />
                                </div>
                                <span className={styles.circleName}>Change name</span>
                            </div>
                        </div>
                    </div>
                </Modal>
            )}

        </div>
    )
}

export default PiggyBank
