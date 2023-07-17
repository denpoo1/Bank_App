package com.onlinebank.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cashback_Card_Number")
public class CashBackCardNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "cashback_percentage", nullable = false)
    private Float cashbackPercentage;

    @ManyToMany(mappedBy = "cashBackCardNumbers")
    private List<Account> accounts;

    public CashBackCardNumber() {
    }

    public CashBackCardNumber(String name, String description, String accountNumber, Float cashbackPercentage) {
        this.name = name;
        this.description = description;
        this.accountNumber = accountNumber;
        this.cashbackPercentage = cashbackPercentage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Float getCashbackPercentage() {
        return cashbackPercentage;
    }

    public void setCashbackPercentage(Float cashbackPercentage) {
        this.cashbackPercentage = cashbackPercentage;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "CashBackCardNumber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", cashbackPercentage=" + cashbackPercentage +
                ", accounts=" + accounts +
                '}';
    }
}

