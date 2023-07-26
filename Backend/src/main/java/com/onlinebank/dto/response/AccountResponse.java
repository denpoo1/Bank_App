package com.onlinebank.dto.response;

import com.onlinebank.models.AccountModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class AccountResponse {

    private int id;

    private int piggyBankId;

    private Date date;

    private int customerId;

    private float transactionRoundingPercentage;

    public AccountResponse(AccountModel accountModel) {
        this.id = accountModel.getId();
        this.piggyBankId = accountModel.getPiggyBankModel().getId();
        this.date = accountModel.getDate();
        this.customerId = accountModel.getCustomerModel().getId();
        this.transactionRoundingPercentage = accountModel.getTransactionRoundingPercentage();
    }
}
