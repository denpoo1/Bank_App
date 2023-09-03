package com.onlinebank.dto.response;

import com.onlinebank.models.AccountModel;
import com.onlinebank.models.PiggyBankModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@Data
public class AccountResponse {

    private int id;

    private List<PiggyBankModel> piggyBankId;

    private Date date;

    private int customerId;

    private float transactionRoundingPercentage;

    public AccountResponse(AccountModel accountModel) {
        this.id = accountModel.getId();
        this.piggyBankId = accountModel.getPiggyBankModels();
        this.date = accountModel.getDate();
        this.customerId = accountModel.getCustomerModel().getId();
        this.transactionRoundingPercentage = accountModel.getTransactionRoundingPercentage();
    }
}
