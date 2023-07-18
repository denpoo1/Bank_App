package com.onlinebank.dto;

import com.onlinebank.models.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Date;

public class TransactionResponse {
    private Integer id;
    private Date date;
    private Integer amount;
    private Integer leftoverAmount;
    private int toAccountId;
    private int fromAccountId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getLeftoverAmount() {
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

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.leftoverAmount = transaction.getLeftoverAmount();
        this.toAccountId = transaction.getToAccountId();
        this.fromAccountId = transaction.getFromAccountId();
    }
}
