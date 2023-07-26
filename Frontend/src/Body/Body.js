import React from "react";
import styles from './Body.module.css';
import BodyWrap from '../Wrap/BodyWrap'
import TotalBalance from "../TotalBalance/TotalBalance";
const Body = () => {
    return(
        <BodyWrap >
            <TotalBalance/>
        </BodyWrap>
        
    )
}

export default Body