import styles from './Login.module.css'
import LeftSideLogin from './LeftSideLogin/LeftSideLogin';
import RightSideLogin from './RightSideLogin/RightSideLogin';


const Login = () => {
  

  return (
    <div className={styles.loginWrapper}>
      <LeftSideLogin />
      <RightSideLogin />
    </div>
    
  );
};

export default Login;
