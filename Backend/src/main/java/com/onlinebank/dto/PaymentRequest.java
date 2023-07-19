package com.onlinebank.dto;

import com.onlinebank.models.Account;
import com.onlinebank.models.CreditCard;
import com.onlinebank.models.Transaction;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public class PaymentRequest {

    private int from_account_id;
    private int to_account_id;
    @Positive
    private int amount;

    public int getFrom_account_id() {
        return from_account_id;
    }

    public void setFrom_account_id(int from_account_id) {
        this.from_account_id = from_account_id;
    }

    public int getTo_account_id() {
        return to_account_id;
    }

    public void setTo_account_id(int to_account_id) {
        this.to_account_id = to_account_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Transaction toTransaction(CreditCard cardFromPayment, CreditCard cardToPayment) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setAmount(this.amount);
        transaction.setLeftoverAmount(cardFromPayment.getBalance());
        transaction.setToAccountId(cardToPayment.getAccount().getId());
        transaction.setFromAccountId(cardFromPayment.getAccount().getId());
        return transaction;
    }
}
