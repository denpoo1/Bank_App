package com.onlinebank.dto.request;

import com.onlinebank.models.Account;
import com.onlinebank.models.PiggyBank;

import java.util.Date;

public class PiggyBankRequest {
    private Date createdAt;
    private int amount;
    private int accountID;

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

    public PiggyBank toPiggyBank (Account account) {
        PiggyBank piggyBank = new PiggyBank();
        piggyBank.setAmount(this.amount);
        piggyBank.setAccount(account);
        piggyBank.setCreatedAt(this.createdAt);
        return piggyBank;
    }
}
