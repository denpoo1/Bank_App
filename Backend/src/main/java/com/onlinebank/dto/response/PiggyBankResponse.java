package com.onlinebank.dto.response;

import com.onlinebank.models.PiggyBankModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class PiggyBankResponse {
    private int id;
    private Date createdAt;
    private int amount;
    private int accountID;

    public PiggyBankResponse(PiggyBankModel piggyBankModel) {
        this.id = piggyBankModel.getId();
        this.createdAt = piggyBankModel.getCreatedAt();
        this.amount = piggyBankModel.getAmount();
        this.accountID = piggyBankModel.getAccountModel().getId();
    }
}
