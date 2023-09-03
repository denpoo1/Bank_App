import React from "react";
import styles from './LeftSideLogin.module.css';
import logingCardLogo from '../../images/logo/logo.jpg'
import logo1 from '../../images/logo/logo1.jpg'
import logo2 from '../../images/logo/logo2.jpg'
import logo3 from '../../images/logo/logo3.jpg'
import logo4 from '../../images/logo/logo4.jpg'
import logo5 from '../../images/logo/logo5.jpg'
import logo6 from '../../images/logo/logo6.jpg'
import logo7 from '../../images/logo/logo7.jpg'
import logo8 from '../../images/logo/logo8.jpg'
import logo9 from '../../images/logo/logo9.jpg'
import logo10 from '../../images/logo/logo10.jpg'
import logo11 from '../../images/logo/logo11.jpg'
import logo12 from '../../images/logo/logo12.jpg'



const LeftSideLogin = () => {
    return(
        <div className={styles.leftSideLoginWrapper}>
            <div className={styles.wrapperForCard}>
                <img className={styles.log} src={logo12} alt="xuy"></img>
                <h1>Sold soul for nudes</h1>
                <p>Meet our talented backend web developer for the banking system! With a passion for cutting-edge technology and a keen eye for detail, they play a pivotal role in ensuring the smooth and secure functioning of our web-based banking platform. Armed with a deep understanding of programming languages and frameworks </p>
                <div className={styles.signWrap}>
                <span>Denis Durbalov &nbsp;(Backender, Springboy)</span>
                <span>Quote: "Backend - it`s my life"</span>
                </div>
            </div>
            <div className={styles.footer}>
                <h1>Trusted by</h1>
                <div className={styles.footerWrapper}>
                    <img className={styles.trustedBy} src={logo1} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo2} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo3} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo4} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo5} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo6} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo7} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo8} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo9} alt="xut"></img>
                    <img className={styles.trustedBy} src={logo11} alt="xut"></img>
                    
                </div>
            </div>
        </div>
    );
}

export default LeftSideLogin;