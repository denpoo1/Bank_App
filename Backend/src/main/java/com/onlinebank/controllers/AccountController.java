package com.onlinebank.controllers;

import com.onlinebank.dto.AccountRequest;
import com.onlinebank.dto.AccountResponse;
import com.onlinebank.dto.TransactionResponse;
import com.onlinebank.models.Account;
import com.onlinebank.models.Customer;
import com.onlinebank.models.PiggyBank;
import com.onlinebank.models.Transaction;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.CreditCardService;
import com.onlinebank.services.CustomerService;
import com.onlinebank.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService, TransactionService transactionService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<AccountResponse> getAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            accountResponses.add(new AccountResponse(account));
        }
        return accountResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable("id") int id) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(new AccountResponse(account));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + id + " not found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountRequest accountRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Customer customer = customerService.getCustomerById(accountRequest.getCustomerId());
        if (customer == null)
            return ResponseEntity.badRequest().body("Customer with id " + accountRequest.getCustomerId() + " don't found");
        Account account = accountRequest.toAccount(customer);
        account.setPiggyBank(new PiggyBank(account, accountRequest.getDate(), 0));
        accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(account));
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<Object> getAccountTransactions(@PathVariable("id") int accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + accountId + " not found");
        }

        List<Transaction> transactions = transactionService.getAccountTransactions(accountId);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionResponses.add(new TransactionResponse(transaction));
        }

        return ResponseEntity.ok(transactionResponses);
    }


    @GetMapping("/{id}/{start_day}/{end_day}")
    public ResponseEntity<Object> getAccountTransactionsByDays(@PathVariable("id") int id, @PathVariable("start_day") @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDay,
                                                               @PathVariable("end_day") @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDay) {
        Account account = accountService.getAccountById(id);
        if (account == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with id " + id + " not found");
        List<Transaction> transactions = transactionService.getAccountTransactionsByDays(id, id ,startDay , endDay);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        if (transactions.isEmpty()) return ResponseEntity.badRequest().body("Transactions between " + startDay + " - " + endDay + " don't found");
        for(Transaction transaction : transactions) {
            transactionResponses.add(new TransactionResponse(transaction));
        }
        return ResponseEntity.ok().body(transactionResponses);
    }
}
