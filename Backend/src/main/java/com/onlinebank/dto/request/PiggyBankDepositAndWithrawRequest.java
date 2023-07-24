package com.onlinebank.dto.request;

import jakarta.validation.constraints.Positive;

public class PiggyBankDepositAndWithrawRequest {

    @Positive
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
