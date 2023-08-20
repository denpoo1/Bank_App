package com.onlinebank.dto.request;

import com.onlinebank.models.CreditCardModel;
import com.onlinebank.models.TransactionModel;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
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
        transactionModel.setToAccountId(cardToPayment.getAccountModel().getId());
        transactionModel.setFromAccountId(cardFromPayment.getAccountModel().getId());
        return transactionModel;
    }
}
