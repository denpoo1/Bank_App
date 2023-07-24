package com.onlinebank.dto.response;

import com.onlinebank.models.Transaction;

public class PaymentResponse {
    private int transaction_id;
    private final String message = "Payment successful.";

    public int getTransaction_id(Transaction transaction) {
        return transaction_id = transaction.getId();
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public String getMessage() {
        return message;
    }

    public PaymentResponse(Transaction transaction) {
        this.transaction_id = transaction.getId();
    }
}
