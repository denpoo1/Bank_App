package com.onlinebank.controllers;

import com.onlinebank.dto.request.PaymentRequest;
import com.onlinebank.dto.response.PaymentResponse;
import com.onlinebank.models.CreditCard;
import com.onlinebank.models.Transaction;
import com.onlinebank.services.CreditCardService;
import com.onlinebank.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final TransactionService transactionService;
    private final CreditCardService creditCardService;


    public PaymentController(TransactionService transactionService, CreditCardService creditCardService) {
        this.transactionService = transactionService;
        this.creditCardService = creditCardService;
    }

    @PostMapping
    public ResponseEntity<Object> makePayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        CreditCard creditCardFrom = creditCardService.getCreditCardById(paymentRequest.getFrom_account_id());
        CreditCard creditCardTo = creditCardService.getCreditCardById(paymentRequest.getTo_account_id());
        if (creditCardFrom == null) {
            return ResponseEntity.badRequest().body("Credit card with id " + paymentRequest.getFrom_account_id() + " not found.");
        } else if (creditCardTo == null) {
            return ResponseEntity.badRequest().body("Credit card with id " + paymentRequest.getTo_account_id() + " not found.");
        }
        if (paymentRequest.getAmount() > creditCardFrom.getBalance() + creditCardFrom.getCreditLimit()) {
            return ResponseEntity.badRequest().body("Insufficient funds in the 'from' account.");
        }

        creditCardFrom.setBalance(creditCardFrom.getBalance() - paymentRequest.getAmount());
        creditCardTo.setBalance(creditCardTo.getBalance() + paymentRequest.getAmount());
        creditCardService.saveCreditCard(creditCardFrom);
        creditCardService.saveCreditCard(creditCardTo);
        Transaction transaction = paymentRequest.toTransaction(creditCardFrom, creditCardTo);
        transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentResponse(transaction));
    }


    @GetMapping("/{transaction_id}")
    public ResponseEntity<Object> gePayment(@PathVariable("transaction_id") int transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment with id " + transactionId + " not found");
        }
    }
}
