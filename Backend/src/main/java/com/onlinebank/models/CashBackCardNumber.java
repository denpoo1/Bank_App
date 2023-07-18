package com.onlinebank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "cashback_Card_Number")
public class CashBackCardNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Name cannot be empty.")
    @Size(max = 30, message = "Name cannot exceed 30 characters.")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Description cannot be empty.")
    @Column(name = "description")
    private String description;

    @Column(name = "account_number")
    @Digits(integer = 28, fraction = 0, message = "Account number must be a valid 28-digit integer.")
    private int accountNumber;

    @Column(name = "cashback_percentage")
    private Float cashbackPercentage;

    @ManyToMany(mappedBy = "cashBackCardNumbers")
    private List<Account> accounts;

    public CashBackCardNumber() {
    }

    public CashBackCardNumber(String name, String description, int accountNumber, Float cashbackPercentage) {
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

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
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

