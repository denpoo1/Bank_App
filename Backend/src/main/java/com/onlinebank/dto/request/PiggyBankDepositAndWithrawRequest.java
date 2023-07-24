package com.onlinebank.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * @author Denis Durbalov
 */
@Data
public class PiggyBankDepositAndWithrawRequest {

    @Positive
    private int amount;
}
