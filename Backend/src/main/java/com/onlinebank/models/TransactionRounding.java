package com.onlinebank.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transaction_rounding")
public class TransactionRounding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "percentage")
    private float percentage;

    @OneToMany(mappedBy = "transactionRounding")
    private List<Account> accounts;

    public TransactionRounding() {
    }

    public TransactionRounding(float percentage) {
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
