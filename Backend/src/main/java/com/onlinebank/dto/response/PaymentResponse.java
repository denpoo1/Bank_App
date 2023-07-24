package com.onlinebank.dto.response;

import com.onlinebank.models.TransactionModel;
import lombok.Data;

/**
 * @author Denis Durbalov
 */
@Data
public class PaymentResponse {
    private int transaction_id;
    private final String message = "Payment successful.";

    public PaymentResponse(TransactionModel transactionModel) {
        this.transaction_id = transactionModel.getId();
    }
}
