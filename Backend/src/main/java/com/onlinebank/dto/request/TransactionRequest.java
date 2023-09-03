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

    private int to_card_id;

    private int from_card_id;

    private String transfer_type;

    private int balanceAfterTransaction;

    public TransactionModel toTransaction() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setDate(this.date);
        transactionModel.setAmount(this.amount);
        transactionModel.setToCardId(this.to_card_id);
        transactionModel.setFromCardId(this.from_card_id);
        transactionModel.setTransferType(this.transfer_type);
        transactionModel.setBalanceAfterTransaction(this.balanceAfterTransaction);
        return transactionModel;
    }
}
