package com.onlinebank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;

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
    @PositiveOrZero(message = "Amount must be a positive or zero value.")
    private Integer amount;

    @Column(name = "leftover_amount")
    @PositiveOrZero(message = "Leftover amount must be a positive or zero value.")
    private Integer leftoverAmount;

    @Column(name = "to_account_id")
    @Digits(integer = 28, fraction = 0, message = "To account ID must be a valid 28-digit number.")
    private int toAccountId;

    @Column(name = "credit_card_id")
    @Digits(integer = 28, fraction = 0, message = "From account ID must be a valid 28-digit number.")
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

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
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
