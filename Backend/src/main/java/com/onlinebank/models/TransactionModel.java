package com.onlinebank.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Transaction")
public class TransactionModel {

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

    @ManyToMany(mappedBy = "transactionModels")
    private List<PaymentCategoryModel> paymentCategoryModelList;

    public TransactionModel(Date date, Integer amount, Integer leftoverAmount, int toAccountId, int fromAccountId) {
        this.date = date;
        this.amount = amount;
        this.leftoverAmount = leftoverAmount;
        this.toAccountId = toAccountId;
        this.fromAccountId = fromAccountId;
    }
}