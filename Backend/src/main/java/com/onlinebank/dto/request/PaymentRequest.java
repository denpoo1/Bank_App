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

    @DecimalMin(value = "1000000000000000", message = "card number must be 16 digits")
    @DecimalMax(value = "10000000000000000", message = "card number must be 16 digits")
    @NotNull(message = "from_card_number must not be null.")
    private BigDecimal from_card_number;

    @DecimalMin(value = "1000000000000000", message = "card number must be 16 digits")
    @DecimalMax(value = "10000000000000000", message = "card number must be 16 digits")
    @NotNull(message = "to_card_number number must not be null.")
    private BigDecimal to_card_number;
    @Positive
    private int amount;

    public TransactionModel toTransaction(CreditCardModel cardFromPayment, CreditCardModel cardToPayment) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setDate(new Date());
        transactionModel.setAmount(this.amount);
        transactionModel.setLeftoverAmount(cardFromPayment.getBalance());
        transactionModel.setToAccountId(cardToPayment.getAccountModel().getId());
        transactionModel.setFromAccountId(cardFromPayment.getAccountModel().getId());
        return transactionModel;
    }
}
