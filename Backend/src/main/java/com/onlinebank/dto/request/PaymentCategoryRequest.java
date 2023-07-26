package com.onlinebank.dto.request;

import com.onlinebank.models.PaymentCategoryModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * @author Denis Durbalov
 */
@Data
public class PaymentCategoryRequest {

    @NotEmpty(message = "Name must not be empty.")
    private String name;

    @Positive(message = "Amount must be a positive value.")
    private int amount;

    @Positive(message = "Max amount must be a positive value.")
    private int maxAmount;

    public PaymentCategoryModel toPaymentCategory() {
        PaymentCategoryModel paymentCategoryModel = new PaymentCategoryModel();
        paymentCategoryModel.setAmount(this.amount);
        paymentCategoryModel.setMaxAmount(this.maxAmount);
        paymentCategoryModel.setName(this.name);
        return paymentCategoryModel;
    }
}
