package com.onlinebank.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "billing_address", nullable = false)
    private String billingAddress;

    @Column(name = "credit_limit", nullable = false)
    private Integer creditLimit;

    @Column(name = "balance", nullable = false)
    private Integer balance;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "creditCard")
    private List<Transaction> transactions;

    public CreditCard() {
    }

    public CreditCard(String cardNumber, String cvv, String billingAddress, Integer creditLimit, Integer balance, Date createdAt, Date expirationDate, Account account) {
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getBalance() {
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
