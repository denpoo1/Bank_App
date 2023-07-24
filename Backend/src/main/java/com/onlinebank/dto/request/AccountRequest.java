package com.onlinebank.dto.request;

import com.onlinebank.models.Account;
import com.onlinebank.models.CreditCard;
import com.onlinebank.models.Customer;
import com.onlinebank.models.PiggyBank;
import jakarta.persistence.*;
import java.util.Date;

public class AccountRequest {

    private Date date;
    private int customerId;
    private float transactionRoundingPercentage;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getTransactionRoundingPercentage() {
        return transactionRoundingPercentage;
    }

    public void setTransactionRoundingPercentage(float transactionRoundingPercentage) {
        this.transactionRoundingPercentage = transactionRoundingPercentage;
    }

    public Account toAccount(Customer customer) {
        Account account = new Account();
        account.setDate(this.date);
        account.setCustomer(customer);
        account.setTransactionRoundingPercentage(this.transactionRoundingPercentage);
        return account;
    }
}
