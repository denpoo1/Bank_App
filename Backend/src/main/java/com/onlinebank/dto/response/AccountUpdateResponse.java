package com.onlinebank.dto.response;

import com.onlinebank.models.AccountModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Denis Durbalov
 */
@Data
public class AccountUpdateResponse {

    private int id;

    private Date date;

    private int customerId;

    private float transactionRoundingPercentage;

    public AccountUpdateResponse(AccountModel accountModel) {
        this.id = accountModel.getId();
        this.date = accountModel.getDate();
        this.customerId = accountModel.getCustomerModel().getId();
        this.transactionRoundingPercentage = accountModel.getTransactionRoundingPercentage();
    }
}
