package com.onlinebank.dto.response;
import com.onlinebank.models.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountUpdateResponse {

    private int id;

    private Date date;

    private int customerId;

    private float transactionRoundingPercentage;

    public AccountUpdateResponse(Account account) {
        this.id = account.getId();
        this.date = account.getDate();
        this.customerId = account.getCustomer().getId();
        this.transactionRoundingPercentage = account.getTransactionRoundingPercentage();
    }
}
