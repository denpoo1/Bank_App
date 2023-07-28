import React from "react";
import Wrap from "../Wrap/Wrap";
import styles from './MarketButtons.module.css'
import deposit from '../images/MarketButtons/deposit.png'  

const MarketButtons = () =>{
    const marketButtons = [
        { img: deposit, text: "Deposit" },
        { img: deposit, text: "Send" },
        { img: deposit, text: "kakaya-to" },
        { img: deposit, text: "xuynya" },
        { img: deposit, text: "xuy" },
        { img: deposit, text: "ne ebu" },
        { img: deposit, text: "lox" },
      ];
    return(
        <Wrap className={styles.marketButtonsWrapper}>
            Markets 
            <div className={styles.buttonsWrapper}>
               

                    {marketButtons.map((button, index) => (
                        <button key={index} className={styles.wrapForMarketElement}>
                            <img src={button.img} alt='qwe'></img>
                            <span>{button.text}</span> 
                        </button>
                    ))}
                
            </div>
        </Wrap>
    )
}

export default MarketButtons