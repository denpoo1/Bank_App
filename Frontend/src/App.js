import React from 'react';
import styles from './GlobalFonts/Global.module.css'
import Sidebar from './Sidebar/Sidebar';
import Header from './Header/Header';

function App() {
  return (
    <section className={styles.container}>
      <Sidebar/>
      <Header/>
    </section>
  );
}

export default App;
