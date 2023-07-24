package com.onlinebank.dto.response;
import com.onlinebank.models.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountResponse {

    private int id;

    private int piggyBankId;

    private Date date;

    private int customerId;

    private float transactionRoundingPercentage;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.piggyBankId = account.getPiggyBank().getId();
        this.date = account.getDate();
        this.customerId = account.getCustomer().getId();
        this.transactionRoundingPercentage = account.getTransactionRoundingPercentage();
    }
}
