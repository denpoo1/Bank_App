import React, { useRef, useEffect } from 'react';
import modalStyles from './Modal.module.css';
import { useSpring, animated } from 'react-spring';


const Modal = ({ children, onClose, className, clasName }) => {
    const modalRef = useRef(null);

    const handleOutsideClick = (e) => {
        if (modalRef.current && !modalRef.current.contains(e.target)) {
            onClose();
        }
    };

    useEffect(() => {
        document.addEventListener('mousedown', handleOutsideClick);
        return () => {
            document.removeEventListener('mousedown', handleOutsideClick);
        };
    }, []);

    const modalAnimation = useSpring({
        from: { opacity: 0, transform: 'scale(0.8)' },
        to: { opacity: 1, transform: 'scale(1)' },
    });

    return (
        <div className={`${modalStyles['modal-overlay']} ${className}`}>
            <div className={`${modalStyles['dark-layer']}`} />
            <animated.div className={`${clasName} ${modalStyles['modal-content']} `} style={modalAnimation} ref={modalRef}>
                <span
                    className={`${modalStyles['close-button']} ${modalStyles['close-button-right']}`}
                    onClick={onClose}
                >
                    &#10006;
                </span>
                {children}
            </animated.div>
        </div>
    );
};

export default Modal;
