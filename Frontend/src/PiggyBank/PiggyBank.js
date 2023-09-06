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
    const [isPiggyOpen, setIsPiggyOpen] = useState(false);
    const [customerId, setCustomerId] = useState(null)
    const [accountID, setAccountID] = useState(null)
    const [piggyBanks, setPiggyBanks] = useState([]);
    const [selectedPiggyBank, setSelectedPiggyBank] = useState(null);
    const [style, setStyle] = useState({ background: 'white' });
    const [isEditing, setIsEditing] = useState(false);
    const [editedValue, setEditedValue] = useState("");
    const [editingLabel, setEditingLabel] = useState('');
    const baseUrl = "http://localhost:8080/"

    const handleCloseDepositModal = () => {
        if (isEditing) {
          setIsEditing(false);
          setEditingLabel('');
        }
      };
    const toggleEditing = (newEditingLabel) => {
        setIsEditing(!isEditing);
        setEditingLabel(newEditingLabel);
      };
      const handleInputChange = (event) => {
        setEditedValue(event.target.value);
      };
    const openPiggy = (piggyBank) => {
        setSelectedPiggyBank(piggyBank);
        setIsPiggyOpen(true);
        const progress = (piggyBank.amount / piggyBank.limit) * 100;
        const isFull = progress >= 100;

        let newStyle = {};

        if (isFull) {
            newStyle = {
                background: `linear-gradient(to top, #3adb76 ${progress}%, transparent ${progress}%)`,
                boxShadow: '0 0 15px rgba(58, 219, 118, 1)'
            };
        } else {
            newStyle = {
                background: `linear-gradient(to top, #757272 ${progress}%, transparent ${progress}%)`
            };
        }

        setStyle(newStyle);
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

                const customerResponse = await axios.get(`${baseUrl}customers`, { headers });
                const matchingUser = customerResponse.data.find((arr) => arr.username === username);
                if (matchingUser) {
                    setCustomerId(matchingUser.id);
                }

                const accountResponse = await axios.get(`${baseUrl}accounts`, { headers });
                const matchingAccount = accountResponse.data.find((arr) => arr.customerId === customerId);
                if (matchingAccount) {
                    setAccountID(matchingAccount.id);
                }

                const piggyBanksResponse = await axios.get(`${baseUrl}piggy-banks`, { headers });
                const piggyBanksData = piggyBanksResponse.data;

                const filteredPiggyBanks = piggyBanksData.filter((piggyBank) => piggyBank.accountID === accountID);

                setPiggyBanks(filteredPiggyBanks);

             


            } catch (error) {
                console.error('Error geting user data', error);
            }
        };

        fetchData();
    }, [customerId, accountID, isPiggyOpen]);


    const handleSave = () => {
        const token = Cookies.get('token');
    
        axios.put(`${baseUrl}customers/`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        })
          .then(response => {
            console.log('Profile updated successfully:', response.data);
            setIsEditing(false);
          })
          .catch(error => {
            console.error('Error updating profile:', error);
          });
      };

    return (
        <div className={styles.piggyBank}>
            <h1>Piggy Bank</h1>
            {!isPiggyOpen && (
                <div className={styles.banksWrapper}>

                    {piggyBanks.map((piggyBank) => (
                        <div
                            key={piggyBank.id}
                            className={styles.bankWrapper}
                            onClick={() => {
                                openPiggy(piggyBank)
                            }
                            }
                        ><div className={styles.jar}>
                                <div className={styles.topOfJar}></div>
                                <div className={styles.bottomOfJar}></div>
                            </div>
                            <div className={styles.bankDescription}>
                                <h2 className={styles.boxName}>
                                    {piggyBank && piggyBank.description !== null && piggyBank.description !== undefined
                                        ? piggyBank.description
                                        : 'Default piggy bank'}
                                </h2>

                                <span className={styles.collected}>
                                    {piggyBank && piggyBank.amount !== null && piggyBank.amount !== undefined
                                        ? `${piggyBank.amount} euro collected from ${piggyBank.limit} euro`
                                        : 'Default piggy bank'}
                                </span>
                                <div className={`${styles.progressionBar} `}>
                                    <div
                                        className={`${styles.progressBarFill} ${piggyBank.amount >= piggyBank.limit ? styles.full : ''}`}
                                        style={{ width: `${(piggyBank && piggyBank.amount / piggyBank.limit) * 100 || 0}%` }}
                                    ></div>
                                </div>
                            </div>
                        </div>
                    ))}

                </div>
            )}
            <div className={styles.addNewPiggy}>
                <div>
                    <img src={plus} alt="plus" />
                    <span> Add new box</span>
                </div>
            </div>
            {isPiggyOpen && (
                
                <Modal onClose={closePiggy} clasName={styles.editWrapper}>
                    <h1>{selectedPiggyBank && selectedPiggyBank.description ? selectedPiggyBank.description : "Default Box Name"}</h1>
                    {isEditing ? (
          <Modal clasName={isEditing ? `${styles.editWrapper}` : ''} onClose={handleCloseDepositModal}>
            <div className={styles.zxc}>
              <input
                className={styles.editFormElem}
                type="text"
                value={editedValue}
                onChange={handleInputChange}
              />

              <button onClick={handleSave}>Save</button>
              <p>Editing: {editingLabel}</p> 
            </div>
          </Modal>
        ) : (
                    <div className={styles.wholeBankDescription}>
                        <div className={styles.firstRowBank}>
                            <div className={styles.biggerJar}>
                                <div className={styles.topOfBiggerJar}></div>
                                <div
                                    className={`${styles.bottomOfBiggerJar}`}
                                    style={style}

                                ></div>
                            </div>
                            <div className={styles.moneyDesc}>
                                <div className={styles.innerDescWrapper}>
                                    <span>{selectedPiggyBank.amount} euro</span>
                                    <span>From</span>
                                    <span>{selectedPiggyBank.limit} euro</span>
                                </div>
                            </div>
                        </div>
                        <div className={styles.secondRow}>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent} onClick={() => toggleEditing("Top up")}>
                                    <img alt="qwe" src={plus} />
                                </div>
                                <span className={styles.circleName}>Top up</span>
                            </div>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent} onClick={() => toggleEditing("Withdraw")}>
                                    <img alt="qwe" src={minus} />
                                </div>
                                <span className={styles.circleName}>Withdraw</span>
                            </div>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent } onClick={() => toggleEditing("Change goal")}>
                                    <img alt="qwe" src={changeGoal} />
                                </div>
                                <span className={styles.circleName}>Change goal</span>
                            </div>
                            <div className={styles.circleWrapper}>
                                <div className={styles.circleContent} onClick={() => toggleEditing("Change name")}>
                                    <img alt="qwe" src={changeName} />
                                </div>
                                <span className={styles.circleName}>Change name</span>
                            </div>
                        </div>
                    </div>
        )}
                </Modal>
            )}

        </div>
    )
}

export default PiggyBank
