package com.onlinebank.exception;

public class TransactionNotCreatedException extends RuntimeException{
    public TransactionNotCreatedException(String message) {
        super(message);
    }
}
