package com.onlinebank.dto.request;

import com.onlinebank.models.Transaction;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionRequest {
    private Date date;

    @PositiveOrZero(message = "Amount must be a positive or zero value.")
    private int amount;

    private int leftoverAmount;

    private int toAccountId;

    private int fromAccountId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getLeftoverAmount() {
        return leftoverAmount;
    }

    public void setLeftoverAmount(Integer leftoverAmount) {
        this.leftoverAmount = leftoverAmount;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }


    public Transaction toTransaction() {
        Transaction transaction = new Transaction();
        transaction.setDate(this.date);
        transaction.setAmount(this.amount);
        transaction.setLeftoverAmount(this.leftoverAmount);
        transaction.setToAccountId(this.toAccountId);
        transaction.setFromAccountId(this.fromAccountId);
        return transaction;
    }
}
