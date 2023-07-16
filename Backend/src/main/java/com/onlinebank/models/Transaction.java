package com.onlinebank.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "leftover_amount", nullable = false)
    private Integer leftoverAmount;

    @Column(name = "to_account_id", nullable = false)
    private Integer toAccountId;

    @Column(name = "credit_card_id", nullable = false)
    private Integer creditCardId;

    public Transaction(Date date, Integer amount, Integer leftoverAmount, Integer toAccountId, Integer creditCardId) {
        this.date = date;
        this.amount = amount;
        this.leftoverAmount = leftoverAmount;
        this.toAccountId = toAccountId;
        this.creditCardId = creditCardId;
    }

    public Transaction() {
    }
}
