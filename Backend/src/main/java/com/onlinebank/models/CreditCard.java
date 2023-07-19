package com.onlinebank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "card_number")
    private BigDecimal cardNumber;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "credit_limit")
    private int creditLimit;

    @Column(name = "balance")
    private int balance;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public CreditCard() {
    }

    public CreditCard(BigDecimal cardNumber, int cvv, String billingAddress, Integer creditLimit, Integer balance, Date createdAt, Date expirationDate, Account account) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.creditLimit = creditLimit;
        this.balance = balance;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.account = account;
    }

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

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", creditLimit=" + creditLimit +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", expirationDate=" + expirationDate +
                ", account=" + account +
                '}';
    }
}
