package com.onlinebank.models;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;

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

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "rounding_transaction_as_a_percentage")
    private float transactionRoundingPercentage;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
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

    public Account(Date date, float transactionRoundingPercentage) {
        this.date = date;
        this.transactionRoundingPercentage = transactionRoundingPercentage;
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

    public float getTransactionRoundingPercentage() {
        return transactionRoundingPercentage;
    }

    public void setTransactionRoundingPercentage(float transactionRoundingPercentage) {
        this.transactionRoundingPercentage = transactionRoundingPercentage;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", piggyBank=" + piggyBank +
                ", date=" + date +
                ", customer=" + customer +
                ", transactionRoundingPercentage=" + transactionRoundingPercentage +
                ", creditCards=" + creditCards +
                ", cashBackCardNumbers=" + cashBackCardNumbers +
                '}';
    }
}