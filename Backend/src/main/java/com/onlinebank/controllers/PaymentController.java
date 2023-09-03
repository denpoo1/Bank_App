package com.onlinebank.controllers;

import com.onlinebank.dto.request.PaymentRequest;
import com.onlinebank.dto.response.PaymentResponse;
import com.onlinebank.models.CreditCardModel;
import com.onlinebank.models.TransactionModel;
import com.onlinebank.services.CreditCardService;
import com.onlinebank.services.TransactionService;
import com.onlinebank.utils.TransferTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author Denis Durbalov
 */
@RestController
@RequestMapping("/payments")
@Tag(name = "Оплаты", description = "Операции для совершания оплат")
public class PaymentController {
    private final TransactionService transactionService;
    private final CreditCardService creditCardService;


    public PaymentController(TransactionService transactionService, CreditCardService creditCardService) {
        this.transactionService = transactionService;
        this.creditCardService = creditCardService;
    }

    @PostMapping
    @Operation(summary = "Совершить платеж", description = "Осуществляет платеж между кредитными картами")
    @ApiResponse(responseCode = "201", description = "Платеж успешно выполнен", content = @Content(schema = @Schema(implementation = PaymentResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    public ResponseEntity<Object> makePayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        CreditCardModel creditCardModelFrom = creditCardService.getCreditCardById(paymentRequest.getFrom_card_id());
        CreditCardModel creditCardModelTo = creditCardService.getCreditCardById(paymentRequest.getTo_card_id());
        if (creditCardModelFrom == null) {
            return ResponseEntity.badRequest().body("Credit card with card id " + paymentRequest.getFrom_card_id() + " not found.");
        } else if (creditCardModelTo == null) {
            return ResponseEntity.badRequest().body("Credit card with card id " + paymentRequest.getTo_card_id() + " not found.");
        }
        if (paymentRequest.getAmount() > creditCardModelFrom.getBalance() + creditCardModelFrom.getCreditLimit()) {
            return ResponseEntity.badRequest().body("Insufficient funds in the 'from' account.");
        }
        int amountFromCard = creditCardModelFrom.getBalance() - paymentRequest.getAmount();
        int amountToCard = creditCardModelTo.getBalance() + paymentRequest.getAmount();
        creditCardModelFrom.setBalance(amountFromCard);
        creditCardModelTo.setBalance(amountToCard);
        creditCardService.saveCreditCard(creditCardModelFrom);
        creditCardService.saveCreditCard(creditCardModelTo);
        TransactionModel transactionModel = paymentRequest.toTransaction(creditCardModelFrom, creditCardModelTo);
        transactionModel.setBalanceAfterTransaction(amountFromCard);
        transactionModel.setTransferType(TransferTypeEnum.CARD_TO_CARD.toString());
        transactionService.saveTransaction(transactionModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentResponse(transactionModel, amountFromCard, paymentRequest.getAmount()));
    }
}
