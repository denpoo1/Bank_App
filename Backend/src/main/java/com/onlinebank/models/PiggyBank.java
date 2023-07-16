package com.onlinebank.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "piggy_bank")
public class PiggyBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public PiggyBank(Date createdAt, Integer amount, Integer accountId) {
        this.createdAt = createdAt;
        this.amount = amount;
        this.accountId = accountId;
    }

    public PiggyBank() {
    }
}
