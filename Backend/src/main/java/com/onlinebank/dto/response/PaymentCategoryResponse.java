package com.onlinebank.dto.response;

import com.onlinebank.models.PaymentCategoryModel;
import lombok.Data;

/**
 * @author Denis Durbalov
 */
@Data
public class PaymentCategoryResponse {

    private int id;
    private String name;
    private int amount;
    private int maxAmount;

    public PaymentCategoryResponse(PaymentCategoryModel paymentCategoryModel) {
        this.id = paymentCategoryModel.getId();
        this.name = paymentCategoryModel.getName();
        this.amount = paymentCategoryModel.getAmount();
        this.maxAmount = paymentCategoryModel.getMaxAmount();
    }
}
