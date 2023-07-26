package com.onlinebank.dto.request;

import com.onlinebank.models.AccountModel;
import com.onlinebank.models.CreditCardModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author Denis Durbalov
 */
@Data
public class CreditCardRequest {

    @DecimalMin(value = "1000000000000000", message = "card number must be 16 digits")
    @DecimalMax(value = "10000000000000000", message = "card number must be 16 digits")
    @NotNull(message = "Card number must not be null.")
    private BigDecimal cardNumber;

    @Min(value = 100, message = "CVV code must be three digits")
    @Max(value = 999, message = "CVV code must be three digits")
    private int cvv;

    @NotEmpty(message = "Billing address must not be empty.")
    @Length(max = 100, message = "Billing address length must not exceed 100 characters.")
    private String billingAddress;

    @PositiveOrZero(message = "Credit limit must be a positive or zero value.")
    private int creditLimit;

    private int balance;

    private Date createdAt;

    @Future(message = "Expiration date must be in the future.")
    private Date expirationDate;
    private int accountId;

    public CreditCardModel toCreditCard(AccountModel accountModel) {
        CreditCardModel creditCardModel = new CreditCardModel();
        creditCardModel.setCardNumber(this.cardNumber);
        creditCardModel.setCvv(this.cvv);
        creditCardModel.setBillingAddress(this.billingAddress);
        creditCardModel.setCreditLimit(this.creditLimit);
        creditCardModel.setBalance(this.balance);
        creditCardModel.setCreatedAt(this.createdAt);
        creditCardModel.setExpirationDate(this.expirationDate);
        creditCardModel.setAccountModel(accountModel);
        return creditCardModel;
    }
}
