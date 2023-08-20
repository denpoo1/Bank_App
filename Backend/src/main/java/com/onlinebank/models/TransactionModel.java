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

    @Column(name = "to_account_id")
    private int to_card_id;

    @Column(name = "credit_card_id")
    private int from_card_Id;

    @ManyToMany(mappedBy = "transactionModels")
    private List<PaymentCategoryModel> paymentCategoryModelList;

    public TransactionModel(Date date, Integer amount, int to_card_id, int from_card_Id) {
        this.date = date;
        this.amount = amount;
        this.to_card_id = to_card_id;
        this.from_card_Id = from_card_Id;
    }
}