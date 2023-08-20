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

    private int remaining_amount_on_the_card;

    private int amount_transferred_to_another_card;

    public PaymentResponse(TransactionModel transactionModel, int remaining_amount_on_the_card, int amount_transferred_to_another_card) {
        this.transaction_id = transactionModel.getId();
        this.remaining_amount_on_the_card = remaining_amount_on_the_card;
        this.amount_transferred_to_another_card = amount_transferred_to_another_card;
    }
}
