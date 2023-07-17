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

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public PiggyBank() {
    }

    public PiggyBank(Date createdAt, Integer amount, Account account) {
        this.createdAt = createdAt;
        this.amount = amount;
        this.account = account;
    }

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
