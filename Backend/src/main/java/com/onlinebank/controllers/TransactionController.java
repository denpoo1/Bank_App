package com.onlinebank.controllers;

import com.onlinebank.dto.TransactionRequest;
import com.onlinebank.dto.TransactionResponse;
import com.onlinebank.models.Transaction;
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

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public List<Transaction> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTransaction(@PathVariable("id") int id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if(transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id " + id + " don't found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for(FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Transaction transaction = transactionRequest.toTransaction();
        System.out.println(transactionRequest.getToAccountId());
        transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponse(transaction));
    }
}
