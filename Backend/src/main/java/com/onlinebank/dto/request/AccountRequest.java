package com.onlinebank.dto.request;

import com.onlinebank.models.AccountModel;
import com.onlinebank.models.CustomerModel;
import lombok.Data;

import java.util.Date;
/**
 * @author Denis Durbalov
 */
@Data
public class AccountRequest {

    private Date date;
    private int customerId;
    private float transactionRoundingPercentage;

    public AccountModel toAccount(CustomerModel customerModel) {
        AccountModel accountModel = new AccountModel();
        accountModel.setDate(this.date);
        accountModel.setCustomerModel(customerModel);
        accountModel.setTransactionRoundingPercentage(this.transactionRoundingPercentage);
        return accountModel;
    }
}
