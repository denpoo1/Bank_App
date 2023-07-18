package com.onlinebank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Date;

@Entity
@Table(name = "piggy_bank")
public class PiggyBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "amount")
    @PositiveOrZero(message = "Amount must be a positive or zero value.")
    private Integer amount;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public PiggyBank() {
    }

    public PiggyBank(Account account, Date createdAt, Integer amount) {
        this.account = account;
        this.createdAt = createdAt;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "PiggyBank{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", amount=" + amount +
                ", account=" + account +
                '}';
    }
}
