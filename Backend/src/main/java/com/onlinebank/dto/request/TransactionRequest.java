package com.onlinebank.dto.request;

import com.onlinebank.models.TransactionModel;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class TransactionRequest {
    private Date date;

    @PositiveOrZero(message = "Amount must be a positive or zero value.")
    private int amount;

    private int toAccountId;

    private int fromAccountId;

    public TransactionModel toTransaction() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setDate(this.date);
        transactionModel.setAmount(this.amount);
        transactionModel.setToAccountId(this.toAccountId);
        transactionModel.setFromAccountId(this.fromAccountId);
        return transactionModel;
    }
}
