package com.onlinebank.controllers;

import com.onlinebank.dto.TransactionRequest;
import com.onlinebank.dto.TransactionResponse;
import com.onlinebank.models.Account;
import com.onlinebank.models.Transaction;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping()
    public List<Transaction> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTransaction(@PathVariable("id") int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id " + id + " don't found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Transaction transaction = transactionRequest.toTransaction();
        System.out.println(transactionRequest.getToAccountId());
        transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponse(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable("id") int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id " + id + " not found");
        }

        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok("Transaction with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTransaction(@PathVariable("id") int id,
                                                    @RequestBody @Valid TransactionRequest transactionRequest,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        Transaction existingTransaction = transactionService.getTransactionById(id);
        if (existingTransaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id " + id + " not found");
        }

        Account accountTo = accountService.getAccountById(transactionRequest.getToAccountId());
        if (accountTo == null) return ResponseEntity.badRequest().body("toAccountId with id " + transactionRequest.getToAccountId() + " not found");
        Account accountFrom = accountService.getAccountById(transactionRequest.getFromAccountId());
        if (accountFrom == null) return ResponseEntity.badRequest().body("fromAccountId with id " + transactionRequest.getFromAccountId() + " not found");
        existingTransaction = transactionRequest.toTransaction();
        existingTransaction.setId(id);

        transactionService.saveTransaction(existingTransaction);
        return ResponseEntity.ok(new TransactionResponse(existingTransaction));
    }

}
