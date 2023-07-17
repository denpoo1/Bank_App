package com.onlinebank.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "payment_category")
public class PaymentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private int amount;

    @Column(name = "max_amount")
    private int maxAmount;

    @ManyToMany
    @JoinTable(
            name = "payment_category_transaction",
            joinColumns = @JoinColumn(name = "payment_category_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transactions;

    public PaymentCategory() {
    }

    public PaymentCategory(String name, int amount, int maxAmount) {
        this.name = name;
        this.amount = amount;
        this.maxAmount = maxAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
