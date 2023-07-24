package com.onlinebank.dto.response;

import com.onlinebank.models.CreditCardModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class CreditCardResponse {

    private Integer id;

    private BigDecimal cardNumber;

    private int cvv;

    private String billingAddress;

    private int creditLimit;

    private int balance;

    private Date createdAt;

    private Date expirationDate;

    private int accountId;

    public CreditCardResponse(CreditCardModel creditCardModel) {
        this.id = creditCardModel.getId();
        this.accountId = creditCardModel.getId();
        this.cardNumber = creditCardModel.getCardNumber();
        this.cvv = creditCardModel.getCvv();
        this.billingAddress = creditCardModel.getBillingAddress();
        this.creditLimit = creditCardModel.getCreditLimit();
        this.balance = creditCardModel.getBalance();
        this.createdAt = creditCardModel.getCreatedAt();
        this.expirationDate = creditCardModel.getExpirationDate();
        this.accountId = creditCardModel.getAccountModel().getId();
    }
}
