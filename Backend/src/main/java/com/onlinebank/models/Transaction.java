package com.onlinebank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "leftover_amount")
    private int leftoverAmount;

    @Column(name = "to_account_id")
    private int toAccountId;

    @Column(name = "credit_card_id")
    private int fromAccountId;

    @ManyToMany(mappedBy = "transactions")
    private List<PaymentCategory> paymentCategoryList;

    public Transaction() {
    }

    public Transaction(Date date, Integer amount, Integer leftoverAmount, int toAccountId, int fromAccountId) {
        this.date = date;
        this.amount = amount;
        this.leftoverAmount = leftoverAmount;
        this.toAccountId = toAccountId;
        this.fromAccountId = fromAccountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getLeftoverAmount() {
        return leftoverAmount;
    }

    public void setLeftoverAmount(Integer leftoverAmount) {
        this.leftoverAmount = leftoverAmount;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public List<PaymentCategory> getPaymentCategoryList() {
        return paymentCategoryList;
    }

    public void setPaymentCategoryList(List<PaymentCategory> paymentCategoryList) {
        this.paymentCategoryList = paymentCategoryList;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", leftoverAmount=" + leftoverAmount +
                ", toAccountId=" + toAccountId +
                ", fromAccountId=" + fromAccountId +
                ", paymentCategoryList=" + paymentCategoryList +
                '}';
    }
}
