import React from "react";
import styles from './Body.module.css';
import BodyWrap from '../Wrap/BodyWrap'
import TotalBalance from "../TotalBalance/TotalBalance";
import MarketButtons from "../MarketButtons/MarketButtons";
import Card from '../Card/Card'

const Body = () => {
    return(
        <BodyWrap >
            <div className={styles.firstRow}>
                <TotalBalance/>
                <MarketButtons/>
            </div>

            <div className={styles.secondRow}>
                <Card/>
            </div>
            
        
        </BodyWrap>
        
    )
}

export default Body