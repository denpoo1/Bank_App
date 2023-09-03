package com.onlinebank.dto.response;

import com.onlinebank.models.PiggyBankModel;
import lombok.Data;

import java.util.Date;

@Data
public class PiggyBankPaymentResponse {
    private int id;
    private Date createdAt;
    private int amount;
    private int accountID;
    private int limit;
    private String description;
    private int balanceAfterTransaction;

    public PiggyBankPaymentResponse(PiggyBankModel piggyBankModel, int balanceAfterTransaction) {
        this.id = piggyBankModel.getId();
        this.createdAt = piggyBankModel.getCreatedAt();
        this.amount = piggyBankModel.getAmount();
        this.accountID = piggyBankModel.getAccountModel().getId();
        this.limit = piggyBankModel.getLimitAmount();
        this.description = piggyBankModel.getDescription();
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
