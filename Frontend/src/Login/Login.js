import styles from './Login.module.css'
import LeftSideLogin from './LeftSideLogin/LeftSideLogin';
import RightSideLogin from './RightSideLogin/RightSideLogin';
import Cookies from 'js-cookie';


const Login = () => {
  

  return (
    <div className={styles.loginWrapper}>
      <LeftSideLogin />
      <RightSideLogin />
    </div>
    
  );
};

export default Login;
