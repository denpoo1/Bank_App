package com.onlinebank.dto.response;

import com.onlinebank.models.Account;
import com.onlinebank.models.CreditCard;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

public class CreditCardResponse {

    private Integer id;

    private BigDecimal cardNumber;

    private int cvv;

    private String billingAddress;

    private int creditLimit;

    private int balance;

    private Date createdAt;

    private Date expirationDate;

    private int accountId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(BigDecimal cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public CreditCardResponse(CreditCard creditCard) {
        this.id = creditCard.getId();
        this.accountId = creditCard.getId();
        this.cardNumber = creditCard.getCardNumber();
        this.cvv = creditCard.getCvv();
        this.billingAddress = creditCard.getBillingAddress();
        this.creditLimit = creditCard.getCreditLimit();
        this.balance = creditCard.getBalance();
        this.createdAt = creditCard.getCreatedAt();
        this.expirationDate = creditCard.getExpirationDate();
        this.accountId = creditCard.getAccount().getId();
    }
}
