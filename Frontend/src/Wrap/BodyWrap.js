import React from "react";
import styles from './BodyWrap.module.css'

const BodyWrap = (props) => {
    return <div className={`${styles.bodyWrap} ${props.className} ` }>{props.children}</div>;
}

export default BodyWrap;