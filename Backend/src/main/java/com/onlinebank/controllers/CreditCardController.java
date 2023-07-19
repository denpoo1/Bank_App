package com.onlinebank.controllers;

import com.onlinebank.dto.CreditCardRequest;
import com.onlinebank.dto.CreditCardResponse;
import com.onlinebank.models.Account;
import com.onlinebank.models.CreditCard;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.CreditCardService;
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
@RequestMapping("/credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final AccountService accountService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService, AccountService accountService) {
        this.creditCardService = creditCardService;
        this.accountService = accountService;
    }

    @GetMapping
    public List<CreditCardResponse> getCreditCards() {
        List<CreditCard> creditCards = creditCardService.getAllCreditCards();
        List<CreditCardResponse> creditCardResponses = new ArrayList<>();
        for(CreditCard creditCard : creditCards) {
            creditCardResponses.add(new CreditCardResponse(creditCard));
        }
        return creditCardResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCreditCard(@PathVariable("id") int id) {
        CreditCard creditCard = creditCardService.getCreditCardById(id);
        if (creditCard != null) {
            return ResponseEntity.ok(new CreditCardResponse(creditCard));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit card with id " + id + " not found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCreditCard(@RequestBody @Valid CreditCardRequest creditCardRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        Account account = accountService.getAccountById(creditCardRequest.getAccountId());
        if (account == null) return ResponseEntity.badRequest().body("Account with id " + creditCardRequest.getAccountId() + " don't found");
        CreditCard creditCard = creditCardRequest.toCreditCard(account);
        creditCardService.saveCreditCard(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreditCardResponse(creditCard));
    }
}
