package com.onlinebank.exception;

public class CreditCardNotCreatedException extends RuntimeException{
    public CreditCardNotCreatedException(String message) {
        super(message);
    }
}
