package com.onlinebank.controllers;

import com.onlinebank.dto.request.PaymentRequest;
import com.onlinebank.dto.response.PaymentResponse;
import com.onlinebank.models.CreditCardModel;
import com.onlinebank.models.TransactionModel;
import com.onlinebank.services.CreditCardService;
import com.onlinebank.services.TransactionService;
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
        CreditCardModel creditCardModelFrom = creditCardService.getCreditCardByCardNumber(paymentRequest.getFrom_card_number());
        CreditCardModel creditCardModelTo = creditCardService.getCreditCardByCardNumber(paymentRequest.getTo_card_number());
        if (creditCardModelFrom == null) {
            return ResponseEntity.badRequest().body("Credit card with card number " + paymentRequest.getFrom_card_number() + " not found.");
        } else if (creditCardModelTo == null) {
            return ResponseEntity.badRequest().body("Credit card with card number " + paymentRequest.getTo_card_number() + " not found.");
        }
        if (paymentRequest.getAmount() > creditCardModelFrom.getBalance() + creditCardModelFrom.getCreditLimit()) {
            return ResponseEntity.badRequest().body("Insufficient funds in the 'from' account.");
        }
        creditCardModelFrom.setBalance(creditCardModelFrom.getBalance() - paymentRequest.getAmount());
        creditCardModelTo.setBalance(creditCardModelTo.getBalance() + paymentRequest.getAmount());
        creditCardService.saveCreditCard(creditCardModelFrom);
        creditCardService.saveCreditCard(creditCardModelTo);
        TransactionModel transactionModel = paymentRequest.toTransaction(creditCardModelFrom, creditCardModelTo);
        transactionService.saveTransaction(transactionModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentResponse(transactionModel));
    }


    @GetMapping("/{transaction_id}")
    @Operation(summary = "Получить информацию о платеже", description = "Получает информацию о платеже по его уникальному ID")
    @ApiResponse(responseCode = "200", description = "Информация о платеже успешно получена", content = @Content(schema = @Schema(implementation = TransactionModel.class)))
    @ApiResponse(responseCode = "404", description = "Платеж с указанным ID не найден")
    public ResponseEntity<Object> gePayment(@PathVariable("transaction_id") int transactionId) {
        TransactionModel transactionModel = transactionService.getTransactionById(transactionId);
        if (transactionModel != null) {
            return ResponseEntity.ok(transactionModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment with id " + transactionId + " not found");
        }
    }
}
