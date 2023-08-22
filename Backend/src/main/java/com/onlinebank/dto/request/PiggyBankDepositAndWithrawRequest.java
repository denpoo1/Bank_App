package com.onlinebank.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Denis Durbalov
 */
@Data
public class PiggyBankDepositAndWithrawRequest {

    @Positive
    private int amount;

    private int credit_card_id;
}
