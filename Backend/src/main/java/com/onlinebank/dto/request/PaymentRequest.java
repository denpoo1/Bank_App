package com.onlinebank.dto.request;

import com.onlinebank.models.CreditCardModel;
import com.onlinebank.models.TransactionModel;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class PaymentRequest {
    private int from_card_id;
    private int to_card_id;
    @Positive
    private int amount;

    public TransactionModel toTransaction(CreditCardModel cardFromPayment, CreditCardModel cardToPayment) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setDate(new Date());
        transactionModel.setAmount(this.amount);
        transactionModel.setTo_card_id(cardToPayment.getAccountModel().getId());
        transactionModel.setFrom_card_Id(cardFromPayment.getAccountModel().getId());
        return transactionModel;
    }
}
