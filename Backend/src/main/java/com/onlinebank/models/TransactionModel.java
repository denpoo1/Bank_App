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

    @Column(name = "to_card_id")
    private int toCardId;

    @Column(name = "from_card_id")
    private int fromCardId;

    @Column
    private String transferType;

    @ManyToMany(mappedBy = "transactionModels")
    private List<PaymentCategoryModel> paymentCategoryModelList;

    public TransactionModel(Date date, Integer amount, int toCardId, int fromCardId, String transferType) {
        this.date = date;
        this.amount = amount;
        this.toCardId = toCardId;
        this.fromCardId = fromCardId;
        this.transferType = transferType;
    }
}