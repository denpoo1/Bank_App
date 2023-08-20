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

    public TransactionModel toTransaction() {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setDate(this.date);
        transactionModel.setAmount(this.amount);
        transactionModel.setTo_card_id(this.to_card_id);
        transactionModel.setFrom_card_Id(this.from_card_id);
        return transactionModel;
    }
}
