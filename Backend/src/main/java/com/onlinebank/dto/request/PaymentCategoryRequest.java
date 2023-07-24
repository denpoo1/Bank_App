package com.onlinebank.dto.request;

import com.onlinebank.models.PaymentCategory;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class PaymentCategoryRequest {

    @NotEmpty(message = "Name must not be empty.")
    private String name;

    @Positive(message = "Amount must be a positive value.")
    private int amount;

    @Positive(message = "Max amount must be a positive value.")
    private int maxAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public PaymentCategory toPaymentCategory() {
        PaymentCategory paymentCategory = new PaymentCategory();
        paymentCategory.setAmount(this.amount);
        paymentCategory.setMaxAmount(this.maxAmount);
        paymentCategory.setName(this.name);
        return paymentCategory;
    }
}
