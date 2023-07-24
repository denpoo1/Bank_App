package com.onlinebank.dto.request;

import com.onlinebank.models.Account;
import com.onlinebank.models.CreditCard;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

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

    public BigDecimal getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(BigDecimal cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public CreditCard toCreditCard(Account account) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(this.cardNumber);
        creditCard.setCvv(this.cvv);
        creditCard.setBillingAddress(this.billingAddress);
        creditCard.setCreditLimit(this.creditLimit);
        creditCard.setBalance(this.balance);
        creditCard.setCreatedAt(this.createdAt);
        creditCard.setExpirationDate(this.expirationDate);
        creditCard.setAccount(account);
        return creditCard;
    }
}
