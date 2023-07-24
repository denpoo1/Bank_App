package com.onlinebank.dto.response;

import com.onlinebank.models.PaymentCategory;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class PaymentCategoryResponse {

    private int id;
    private String name;
    private int amount;
    private int maxAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public PaymentCategoryResponse(PaymentCategory paymentCategory) {
        this.id = paymentCategory.getId();
        this.name = paymentCategory.getName();
        this.amount = paymentCategory.getAmount();
        this.maxAmount = paymentCategory.getMaxAmount();
    }
}
