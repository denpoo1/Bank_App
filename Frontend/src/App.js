import React from 'react';
import styles from './GlobalFonts/Global.module.css'
import Sidebar from './Sidebar/Sidebar';
import Header from './Header/Header';
import Body from './Body/Body';

function App() {
  return (
    <section className={styles.container}>
      <Sidebar/>
      <Header/>
    <Body />
      </section>
  );
}

export default App;
