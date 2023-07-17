package com.onlinebank.models;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private PiggyBank piggyBank;

    @Column(name = "date_opened")
    private Date date;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "transaction_rounding_id", referencedColumnName = "id")
    private TransactionRounding transactionRounding;

    @OneToMany(mappedBy = "account")
    List<CreditCard> creditCards;

    @ManyToMany
    @JoinTable(
            name = "cashback_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "cashback_id")
    )
    private List<CashBackCardNumber> cashBackCardNumbers;

    public Account() {
    }

    public Account(PiggyBank piggyBank, Date date, Customer customer, TransactionRounding transactionRounding) {
        this.piggyBank = piggyBank;
        this.date = date;
        this.customer = customer;
        this.transactionRounding = transactionRounding;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PiggyBank getPiggyBank() {
        return piggyBank;
    }

    public void setPiggyBank(PiggyBank piggyBank) {
        this.piggyBank = piggyBank;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TransactionRounding getTransactionRounding() {
        return transactionRounding;
    }

    public void setTransactionRounding(TransactionRounding transactionRounding) {
        this.transactionRounding = transactionRounding;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public List<CashBackCardNumber> getCashBackCardNumbers() {
        return cashBackCardNumbers;
    }

    public void setCashBackCardNumbers(List<CashBackCardNumber> cashBackCardNumbers) {
        this.cashBackCardNumbers = cashBackCardNumbers;
    }
}