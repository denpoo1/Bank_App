package com.onlinebank.exception;

public class PaymentCategoryNotCreatedException extends RuntimeException{
    public PaymentCategoryNotCreatedException(String message) {
        super(message);
    }
}
