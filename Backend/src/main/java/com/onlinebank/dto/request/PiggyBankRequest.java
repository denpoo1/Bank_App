package com.onlinebank.dto.request;

import com.onlinebank.models.AccountModel;
import com.onlinebank.models.PiggyBankModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class PiggyBankRequest {
    private Date createdAt;
    private int amount;
    private int accountID;

    public PiggyBankModel toPiggyBank(AccountModel accountModel) {
        PiggyBankModel piggyBankModel = new PiggyBankModel();
        piggyBankModel.setAmount(this.amount);
        piggyBankModel.setAccountModel(accountModel);
        piggyBankModel.setCreatedAt(this.createdAt);
        return piggyBankModel;
    }
}
