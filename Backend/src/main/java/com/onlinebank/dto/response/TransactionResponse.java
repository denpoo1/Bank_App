package com.onlinebank.dto.response;

import com.onlinebank.models.TransactionModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class TransactionResponse {
    private Integer id;
    private Date date;
    private int amount;
    private int toAccountId;
    private int fromAccountId;

    public TransactionResponse(TransactionModel transactionModel) {
        this.id = transactionModel.getId();
        this.date = transactionModel.getDate();
        this.amount = transactionModel.getAmount();
        this.toAccountId = transactionModel.getToCardId();
        this.fromAccountId = transactionModel.getFromCardId();
    }
}
