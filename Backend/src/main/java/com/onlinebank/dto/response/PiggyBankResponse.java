package com.onlinebank.dto.response;

import com.onlinebank.models.Account;
import com.onlinebank.models.PiggyBank;
import jakarta.persistence.*;

import java.util.Date;

public class PiggyBankResponse {
    private int id;
    private Date createdAt;
    private int amount;
    private int accountID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public PiggyBankResponse(PiggyBank piggyBank) {
        this.id = piggyBank.getId();
        this.createdAt = piggyBank.getCreatedAt();
        this.amount = piggyBank.getAmount();
        this.accountID = piggyBank.getAccount().getId();
    }
}
