import React from "react";
import styles from "./Sidebar.module.css";
import Wrap from "../Wrap/Wrap";
import Logo from "./Logo";
import SidebarNavigation from "./SidebarNavigation";
import SidevarFooter from "./SidebarFooter";

const Sidebar = () => {
  return (
    <Wrap className={`${styles.sidebar}`}>
      <Logo />
      <SidebarNavigation />
      <SidevarFooter />
    </Wrap>
  );
};

export default Sidebar;
