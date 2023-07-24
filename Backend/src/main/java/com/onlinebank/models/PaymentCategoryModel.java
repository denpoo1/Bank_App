package com.onlinebank.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Denis Durbalov
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "payment_category")
public class PaymentCategoryModel {

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
    private List<TransactionModel> transactionModels;

    public PaymentCategoryModel(String name, int amount, int maxAmount) {
        this.name = name;
        this.amount = amount;
        this.maxAmount = maxAmount;
    }
}