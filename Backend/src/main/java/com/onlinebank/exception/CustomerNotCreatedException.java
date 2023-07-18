package com.onlinebank.exception;

public class CustomerNotCreatedException extends RuntimeException{
    public CustomerNotCreatedException(String message) {
        super(message);
    }
}
